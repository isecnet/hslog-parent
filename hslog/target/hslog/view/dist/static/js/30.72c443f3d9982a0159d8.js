webpackJsonp([30],{JoeA:function(e,t){},ui9M:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=a("ID1p"),i=a("AIWI"),o=a("8+FI"),n={name:"dhcpLogs",data:function(){var e=this;return{layerObj:{detailData:{},layerState:!1},busName:"dhcpLogs",searchUrl:"log/getDNSLogListByBlend.do",searchConditions:{type:"dhcp",starttime:"",endtime:"",dhcp_type:"",client_mac:"",relay_ip:"",client_ip:""},formConditionsArr:[{label:"事件范围",type:"datetimerange",itemType:"",paramName:"time",model:{model:[]},val:""},{label:"dhcp类型",paramName:"dhcp_type",model:{model:""},itemType:"",type:"input"},{label:"mac地址",paramName:"client_mac",model:{model:""},itemType:"",type:"input"},{label:"中继设备",paramName:"relay_ip",model:{model:""},itemType:"",type:"input"},{label:"客户端ip",paramName:"client_ip",model:{model:""},itemType:"",type:"input"}],tableHead:[{prop:"logtime",label:"时间",width:""},{prop:"equipmentname",label:"资产名称",width:""},{prop:"dhcp_type",label:"dhcp类型",width:""},{prop:"client_mac",label:"mac地址",width:""},{prop:"relay_ip",label:"中继设备地址",width:""},{prop:"client_ip",label:"客户端IP",width:""},{prop:"tools",label:"操作",width:"60",btns:[{icon:"el-icon-tickets",text:"查看详情",btnType:"readDetails",clickFun:function(t,a){e.layerObj.layerState=!0,e.layerObj.detailData=t}},{icon:"el-icon-circle-close",text:"删除",btnType:"removeItem",clickFun:function(t,a){e.$refs.logContent.removeParams([t])}}]}]}},created:function(){var e=this;o.default.$on("dhcpLogs",function(t){e.searchConditions=t,e.searchConditions.type="dhcp"})},beforeDestroy:function(){o.default.$off("dhcpLogs")},components:{vSearchForm:l.default,vLogscontent:i.default}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"content-bg"},[a("div",{staticClass:"top-title"},[e._v("DHCP 日志")]),e._v(" "),a("div",{staticClass:"search-wapper"},[a("v-search-form",{attrs:{formItem:e.formConditionsArr,busName:e.busName}})],1),e._v(" "),a("div",{staticClass:"content-wapper"},[a("v-logscontent",{ref:"logContent",attrs:{searchConditions:e.searchConditions,tableHead:e.tableHead,searchUrl:e.searchUrl,layerObj:e.layerObj,moreDeleteBtn:!0}})],1)])},staticRenderFns:[]};var c=a("C7Lr")(n,r,!1,function(e){a("JoeA")},"data-v-5a986d4c",null);t.default=c.exports}});