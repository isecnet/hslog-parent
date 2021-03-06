package com.jz.bigdata.roleauthority.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jz.bigdata.common.Constant;
import com.jz.bigdata.roleauthority.system.entity.System;
import com.jz.bigdata.roleauthority.system.service.ISystemService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jz.bigdata.util.DescribeLog;

import java.util.List;

/**
 * @author shichengyu
 * @date 2018年5月10日 下午5:54:09
 * @description
 */
@Slf4j
@Controller
@RequestMapping("/system")
public class SystemController {
	@Resource(name="SystemService")
	private ISystemService systemService;
	

	@ResponseBody
	@RequestMapping(value="/selectAllSystem",produces = "application/json; charset=utf-8")
	@DescribeLog(describe="获取所有系统")
	public List<System> selectAllSystem(HttpServletRequest request){
		try{
			return systemService.selectAllSystem();
		}catch(Exception e){
			log.error("获取所有系统"+e.getMessage());
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value="/upsert",produces = "application/json; charset=utf-8")
	@DescribeLog(describe="增改系统信息")
	public String upsert(System sys){
		try{
			if( sys.getId()==null||sys.getId().isEmpty()){//新增
				return systemService.insert(sys)? Constant.successMessage():Constant.failureMessage();
			}else{//修改
				return systemService.update(sys)? Constant.successMessage():Constant.failureMessage();
			}
		}catch(Exception e){
			log.error("增改系统信息"+e.getMessage());
			return Constant.failureMessage("增改系统信息失败！");
		}

	}

	@ResponseBody
	@RequestMapping(value="/delete",produces = "application/json; charset=utf-8")
	@DescribeLog(describe="删除系统信息")
	public String delete(HttpServletRequest request){
		try{
			String id = request.getParameter("id");
			if(id==null||id.isEmpty()){
				return Constant.failureMessage("删除失败！");
			}else{
				return  systemService.delete(id)?Constant.successMessage():Constant.failureMessage();
			}
		}catch(Exception e){
			log.error("删除系统信息"+e.getMessage());
			return Constant.failureMessage("删除系统信息失败");
		}

	}
	@ResponseBody
	@RequestMapping(value="/getEntity",produces = "application/json; charset=utf-8")
	@DescribeLog(describe="获取系统信息")
	public System getEntity(HttpServletRequest request){
		try{
			String id = request.getParameter("id");
			if(id==null||id.isEmpty()){
				return new System();
			}else{
				return  systemService.getEntity(id);
			}
		}catch(Exception e){
			log.error("获取系统信息"+e.getMessage());
			return null;
		}

	}
}
