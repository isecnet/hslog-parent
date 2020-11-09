package com.jz.bigdata.util;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.jz.bigdata.business.logAnalysis.log.entity.Syslog;

public class Bean2Mapping {

	public String toMapping() {
		String template = "{\n" + "\t\t\"properties\":{\n" + "\t\t{#}\n" + "\t\t\t\t}" + "}";
		String fieldString = getClassMapping(new Syslog());
		template = template.replace("{#}", fieldString);
		return template;
	}

	public static <T> String getClassMapping(T classes) {

		StringBuilder fieldstring = new StringBuilder();

		// 设置需要聚合的字段
		String [] fielddata = {
				// 基本字段
				"equipmentid", "equipmentname","logtime", "hostname", "operation_facility","operation_level", "process",  "eventid", "event_type","hslog_type",
				// dhcp字段
				"dhcp_type","client_mac" ,"client_hostname", "error_log","network_error",
				// dns字段
				"dns_view","dns_domain_name","dns_ana_type","dns_server",
				// 流量字段
				"protocol","protocol_name","application_layer_protocol","encryption_based_protection_protocol","packet_source",
				"l4_src_port","l4_dst_port","request_type","domain_url","complete_url","url_param","request_url","response_state",
				"user_agent_os","user_agent_browser","session_status","dst_addr_country","dst_addr_province","dst_addr_city",
				"src_addr_country","src_addr_province","src_addr_city","flag",
				// 防火墙字段
				"from","devid","dname","logtype","mod","act","sa","da","pa",
				// filebeat

				"host"};
		// 设置需要分词的字段
		String [] analyzer = {
				// 基本字段
				"operation_des","equipmentname","hostname","process","event_des",
				// dhcp分词字段
				"dhcp_type","client_mac", "client_hostname", "error_log","network_error",
				// dns分词字段
				"dns_view","dns_domain_name","dns_ana_type","dns_server",
				// 流量包字段，包含http
				"l4_src_port","l4_dst_port","protocol_name","application_layer_protocol","encryption_based_protection_protocol",
				"packet_source","request_url","complete_url","url_param","domain_url","user_agent_os","user_agent_browser","session_status",
				// 防火墙
				"act",
				// filebeat
				"host","name"};
		
		// 设置raw，保证聚合时不会聚合分词内容，主要针对ip地址和url
		String [] rawkeywords = {
				// ip
				"client_ip","dns_clientip","ip","relay_ip","ipv4_src_addr","ipv4_dst_addr","from","sa","da","pa",
				// url
				"equipmentname","request_url","domain_url","url_param","complete_url","protocol_name","application_layer_protocol",
				"encryption_based_protection_protocol","flag",
				//hsdata
				"group_name",
				// user-agent
				"user_agent_os","user_agent_browser","session_status",
				"dst_addr_country","dst_addr_province","dst_addr_city",
				"src_addr_country","src_addr_province","src_addr_city","title",
				//alert
				"alert_id","state","alert_name"
		};

		// 设置join field,通过父子关系使字段关联，一个索引只能建立一个关联字段,nextacknum是parent，acknum是child
		String [] joinfields = {"nextacknum","acknum"};
		
		// 获取class的所有属性进行遍历
		Field[] fields = classes.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fieldstring.append("\t\t\t\t\"" + fields[i].getName().toLowerCase() + "\": {\n");
			fieldstring.append("\t\t\t\t\t\t\"type\": \""
					+ getElasticSearchMappingType(fields[i].getType().getSimpleName(), fields[i].getName()) + "\n");
			if (fields[i].getName().equals("id")) {
				fieldstring.append("\t\t\t\t\t\t,\"index\": \"" + "false\"" + "\n");
			}
			if (Arrays.asList(fielddata).contains(fields[i].getName())) {
				fieldstring.append("\t\t\t\t\t\t,\"fielddata\": " + "true" + "\n");
			}
			if (Arrays.asList(analyzer).contains(fields[i].getName())) {
				//index_ansj   query_ansj
				fieldstring.append("\t\t\t\t\t\t,\"analyzer\": \"" + "standard\"" + "\n");
				fieldstring.append("\t\t\t\t\t\t,\"search_analyzer\": \"" + "standard\"" + "\n");
			}
			if (Arrays.asList(rawkeywords).contains(fields[i].getName())) {
				fieldstring.append("\t\t\t\t\t\t,\"fields\": " + "{\"raw\": {\"type\": \"keyword\"}}" + "\n");
			}

			if (fields[i].getName().equals("joinfield")) {
				fieldstring.append("\t\t\t\t\t\t,\"relations\": {\""+joinfields[0]+"\": \""+joinfields[1]+"\"}" + "\n");
			}
			if (i == fields.length - 1) {
				fieldstring.append("\t\t\t\t\t}\n");
			} else {
				fieldstring.append("\t\t\t\t\t},\n");
			}
		}
		return fieldstring.toString();
	}

	private static String getElasticSearchMappingType(String varType, String name) {

		// 设置keyword类型的字段
		String [] keywords = {
				"userid", "deptid", "id", "process_id",
				"type"//图表字段
		};
		String es = "text";
		switch (varType) {
		case "Date":
			es = "date\"\n" + "\t\t\t\t\t\t,\"format\":\"yyyy-MM-dd HH:mm:ss\"\n" + "\t\t\t\t\t\t";
			break;
		case "Double":
			es = "double\"\n" + "\t\t\t\t\t\t,\"null_value\":\"NaN\"";
			break;
		case "Long":
			es = "long\"";
			break;
		case "Integer":
			es = "integer\"";
			break;
		case "Boolean":
			es = "boolean\"";
			break;
		case "Object":
			es = "object\"";
			break;
		default:
			// 针对id类型的字段设置为keyword
			if (Arrays.asList(keywords).contains(name)) {
				es = "keyword\"";
			// 针对坐标字段设置为geo_point
			} else if (name.contains("_locations")){
				es = "geo_point\"";
				// 针对ip类型的字段设置为ip类型，新版本的elasticsearch数据类型增加了IP类型
			} else if (name.contains("ip")&&!name.equals("equipmentname")&&!name.equals("equipmentid")&&!name.equals("description")) {
				es = "ip\"";
			} else if (name.equals("joinfield")){
				es = "join\"";
			} else {
				es = "text\"";
			}

			break;
		}
		return es;
	}

}
