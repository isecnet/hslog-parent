webpackJsonp([46],{Pt1D:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a("jKHB"),i=a("POd4"),n=a("8+FI"),r=["01","02","03","04","05","06","07","08","09","10","11","12"],o=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],l=["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"],c={name:"index",data:function(){return{date:"",hour:"",min:"",sec:"",starttime:"",todayDate:"",pickerOptions:{shortcuts:[{text:"最近一周",onClick:function(t){var e=new Date,a=new Date;a.setTime(a.getTime()-6048e5),t.$emit("pick",[a,e])}},{text:"最近一个月",onClick:function(t){var e=new Date,a=new Date;a.setTime(a.getTime()-2592e6),t.$emit("pick",[a,e])}},{text:"最近三个月",onClick:function(t){var e=new Date,a=new Date;a.setTime(a.getTime()-7776e6),t.$emit("pick",[a,e])}},{text:"全部",onClick:function(t){t.$emit("pick",["",""])}}]},dateVal1:[],dateVal2:[],allLogsTotle:0,errorLogsTotle:0,flowTotle:0,echartData:{barData:{baseConfig:{title:"日志级别数量统计",xAxisName:"级别",yAxisName:"数量/条",hoverText:"数量"},xAxisArr:[],yAxisArr:[]},pieData:{baseConfig:{title:"日志级别数量统计",hoverText:"百分比"},xAxisArr:[],yAxisArr:[]},lineData:{baseConfig:{title:"",xAxisName:"时间/时",yAxisName:"数量/条",hoverText:"日志数量"},xAxisArr:[],yAxisArr:[]}},errorLogsData:{},interval:"",activeIndex:0,baseConfig:{type:"2",title:"日志详情",areaWidth:"620px",areaHeight:"520px"},detailData:{},layerState:!1}},created:function(){var t=new Date,e=new Date;e.setTime(e.getTime()-6048e5),this.dateVal1=[e,t],this.dateVal2=[e,t];var a=t.getMonth()+1,s=t.getDate();a<10&&(a="0"+(t.getMonth()+1)),s<10&&(s="0"+t.getDate()),this.todayDate=t.getFullYear()+"-"+a+"-"+s;var i=new Date(t);i.setDate(t.getDate()-7),this.starttime=i.getFullYear()+"-"+(i.getMonth()+1)+"-"+i.getDate(),setInterval(this.setDate,1e3),this.getLogsTotle(),this.getBarPieEchartData(this.starttime,this.todayDate,!0,!0),this.getLineEchartData(),this.getErrorLogsData(),this.getFlowTotle()},mounted:function(){},watch:{layerState:function(t){t?this.stopLogsInterval():this.startLogsInterval()}},methods:{clickss:function(t){this.$router.push({name:"searchLogs",params:{words:t.name}})},goToSearchLogs:function(){this.$router.push({name:"searchLogs",params:{words:"error"}})},setDate:function(){var t=new Date;t.setDate(t.getDate()),this.date=t.getFullYear()+"/"+r[t.getMonth()]+"/"+t.getDate()+" "+o[t.getDay()];var e=(new Date).getHours();this.hour=(e<10?"0":"")+e;var a=(new Date).getMinutes();this.min=(a<10?"0":"")+a;var s=(new Date).getSeconds();this.sec=(s<10?"0":"")+s},changeDate1:function(){this.getBarPieEchartData(this.dateVal1[0],this.dateVal1[1],!0,!1)},changeDate2:function(){this.getBarPieEchartData(this.dateVal2[0],this.dateVal2[1],!1,!0)},getLogsTotle:function(){var t=this;this.$nextTick(function(){t.$axios.get(t.$baseUrl+"/log/getIndicesCount.do",{}).then(function(e){t.allLogsTotle=parseInt(e.data[0].indices).toLocaleString(),t.errorLogsTotle=parseInt(e.data[0].indiceserror).toLocaleString()}).catch(function(t){console.log(t)})})},getFlowTotle:function(){var t=this;this.$nextTick(function(){t.$axios.get(t.$baseUrl+"/log/getIndicesCountByType.do",{}).then(function(e){t.flowTotle=parseInt(e.data[0].indices_defaultpacket).toLocaleString()}).catch(function(t){console.log(t)})})},getBarPieEchartData:function(t,e,a,s){var i=this;this.$nextTick(function(){i.$axios.get(i.$baseUrl+"/log/getCountGroupByParam.do",{params:{index:"estest",type:"",param:"operation_level",starttime:t,endtime:e}}).then(function(t){var e=t.data[0],n=[],r=[],o=[];for(var l in e){var c={};n.push(l),r.push(e[l]),c.value=e[l],c.name=l,o.push(c)}a&&(i.echartData.barData.xAxisArr=n,i.echartData.barData.yAxisArr=r),s&&(i.echartData.pieData.yAxisArr=o)}).catch(function(t){console.log(t)})})},getLineEchartData:function(){var t=this;this.$nextTick(function(){t.$axios.get(t.$baseUrl+"/log/getCountGroupByTime.do",{params:{equipmentid:"",index:"estest",type:"",param:t.todayDate}}).then(function(e){var a=[];for(var s in e.data)a.push(e.data[s].count);t.echartData.lineData.xAxisArr=l,t.echartData.lineData.yAxisArr=a}).catch(function(t){console.log(t)})})},getErrorLogsData:function(){var t=this;this.$nextTick(function(){t.$axios.get(t.$baseUrl+"/log/getLogListByLevel.do",{params:{words:"error"}}).then(function(e){t.errorLogsData=e.data.slice(0,30),t.startLogsInterval()}).catch(function(t){console.log(t)})})},errorLogsClick:function(t){var e=this;this.detailData=t,this.layerState=!0,n.default.$on("closeLayer",function(t){e.layerState=!1})},errorLogsListScroll:function(){this.activeIndex<this.errorLogsData.length-3?this.activeIndex+=1:(this.stopLogsInterval(),this.activeIndex=0,this.getErrorLogsData())},stopLogsInterval:function(){clearInterval(this.interval)},startLogsInterval:function(){var t=this;this.layerState||(this.interval=setInterval(function(){t.errorLogsListScroll()},4e3))}},computed:{top:function(){return 112.66*-this.activeIndex+"px"}},components:{vEcharts:s.default,vListdetails:i.default}},p={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"wapper"},[a("el-row",{staticClass:"wapper-top",attrs:{gutter:20}},[a("el-col",{attrs:{span:7}},[a("div",{staticClass:"grid-content bg-purple wapper-content count-wapper"},[a("p",{staticClass:"content-title"}),t._v(" "),a("div",{staticClass:"content-infom"},[a("div",{staticClass:"current-date"},[a("div",{attrs:{id:"date"}},[t._v(t._s(t.date))]),t._v(" "),a("ul",[a("li",{attrs:{id:"hours"}},[t._v(" "+t._s(t.hour))]),t._v(" "),a("li",{staticClass:"point"},[t._v(":")]),t._v(" "),a("li",{attrs:{id:"min"}},[t._v(t._s(t.min)+" ")]),t._v(" "),a("li",{staticClass:"point"},[t._v(":")]),t._v(" "),a("li",{attrs:{id:"sec"}},[t._v(" "+t._s(t.sec))])])]),t._v(" "),a("div",{staticClass:"current-number"},[a("div",{staticClass:"num-left"},[a("p",{staticClass:"all-totle-wapper"},[a("span",[t._v("日志数：")]),t._v(" "),a("b",[t._v(" "+t._s(t.allLogsTotle)+" ")])]),t._v(" "),a("p",{staticClass:"error-totle-wapper"},[a("span",[t._v("ERROR日志数：")]),t._v(" "),a("b",{staticClass:"errorLogsCounts",attrs:{title:"点击查看错误日志"},on:{click:function(e){return t.goToSearchLogs()}}},[t._v(" "+t._s(t.errorLogsTotle)+" ")])])]),t._v(" "),a("div",{staticClass:"num-right"},[a("p",{staticClass:"flow-totle-wapper"},[a("span",[t._v("流量数：")]),t._v(" "),a("b",[t._v(" "+t._s(t.flowTotle)+" ")])])])])])])]),t._v(" "),a("el-col",{attrs:{span:10}},[a("div",{staticClass:"grid-content bg-purple wapper-content"},[a("p",{staticClass:"content-title"},[a("el-date-picker",{attrs:{type:"daterange",align:"right",size:"mini","value-format":"yyyy-MM-dd","range-separator":"-","start-placeholder":"开始日期","end-placeholder":"结束日期","picker-options":t.pickerOptions},on:{change:t.changeDate1},model:{value:t.dateVal1,callback:function(e){t.dateVal1=e},expression:"dateVal1"}})],1),t._v(" "),a("div",{staticClass:"content-infom"},[a("v-echarts",{attrs:{echartType:"bar",echartData:this.echartData.barData},on:{callbackFucnc:t.clickss}})],1)])]),t._v(" "),a("el-col",{attrs:{span:7}},[a("div",{staticClass:"grid-content bg-purple wapper-content"},[a("p",{staticClass:"content-title"},[a("el-date-picker",{attrs:{type:"daterange",align:"right",size:"mini","value-format":"yyyy-MM-dd","range-separator":"-","start-placeholder":"开始日期","end-placeholder":"结束日期","picker-options":t.pickerOptions},on:{change:t.changeDate2},model:{value:t.dateVal2,callback:function(e){t.dateVal2=e},expression:"dateVal2"}})],1),t._v(" "),a("div",{staticClass:"content-infom"},[a("v-echarts",{attrs:{echartType:"pie",echartData:this.echartData.pieData}})],1)])])],1),t._v(" "),a("el-row",{staticClass:"wapper-bottom",attrs:{gutter:20}},[a("el-col",{attrs:{span:10}},[a("div",{staticClass:"grid-content bg-purple wapper-content",staticStyle:{height:"400px"}},[a("p",{staticClass:"content-title"},[t._v("日志检索")]),t._v(" "),a("div",{staticClass:"content-infom",staticStyle:{height:"350px"}},[a("ul",{ref:"errorLogsList",staticClass:"errorLogsList",style:{top:t.top},on:{mouseenter:function(e){return t.stopLogsInterval()},mouseleave:function(e){return t.startLogsInterval()}}},t._l(t.errorLogsData,function(e,s){return a("li",{key:s,staticClass:"error-logs-item",attrs:{title:"点击查看详情"},on:{click:function(a){return t.errorLogsClick(e)}}},[a("p",{staticClass:"index-list-top"},[a("span",{staticClass:"index-log-level"},[t._v("#"+t._s(e.operation_level)+"#")]),t._v(" "),a("span",{staticClass:"index-log-ip"},[t._v("#"+t._s(e.ip)+"#")]),t._v(" "),a("span",{staticClass:"index-log-eqname"},[t._v("#"+t._s(e.equipmentname)+"#")])]),t._v(" "),a("p",{staticClass:"index-list-content"},[t._v(t._s(e.operation_des))]),t._v(" "),a("p",{staticClass:"index-list-time"},[a("small",[a("i",{staticClass:"el-icon-time"}),t._v(" "+t._s(e.logtime))])])])}),0)])])]),t._v(" "),a("el-col",{staticStyle:{height:"400px"},attrs:{span:14}},[a("div",{staticClass:"grid-content bg-purple wapper-content",staticStyle:{height:"400px"}},[a("p",{staticClass:"content-title"},[t._v(t._s(t.todayDate)+" 数据量统计")]),t._v(" "),a("div",{staticClass:"content-infom",staticStyle:{height:"350px"}},[a("div",{staticClass:"content-infom",staticStyle:{height:"350px"}},[a("v-echarts",{attrs:{echartType:"line",echartData:this.echartData.lineData}})],1)])])])],1),t._v(" "),t.layerState?a("div",[a("vListdetails",{attrs:{baseConfig:this.baseConfig,detailsData:this.detailData}})],1):t._e()],1)},staticRenderFns:[]};var h=a("C7Lr")(c,p,!1,function(t){a("ZEOD")},"data-v-17c10e9a",null);e.default=h.exports},ZEOD:function(t,e){}});