webpackJsonp([14],{ZB4s:function(a,t){},fK2e:function(a,t,e){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=e("jKHB"),s=e("8+FI"),l=e("/sA3"),i={name:"allEcharts",data:function(){return{logType:"",dateVal:[],pickerOptions:{shortcuts:[{text:"最近一周",onClick:function(a){var t=new Date,e=new Date;e.setTime(e.getTime()-6048e5),a.$emit("pick",[e,t])}},{text:"最近一个月",onClick:function(a){var t=new Date,e=new Date;e.setTime(e.getTime()-2592e6),a.$emit("pick",[e,t])}},{text:"最近三个月",onClick:function(a){var t=new Date,e=new Date;e.setTime(e.getTime()-7776e6),a.$emit("pick",[e,t])}},{text:"全部",onClick:function(a){a.$emit("pick",["",""])}}]},searchParams:{type:"",starttime:"",endtime:"",index:"estest",param:"operation_level"},logTypeArr:[{value:"",label:"全部"},{value:"syslog",label:"syslog"},{value:"winlog",label:"winlog"},{value:"log4j",label:"log4j"},{value:"mysql",label:"mysql"}],echartData:{barData:{baseConfig:{title:"",xAxisName:"级别",yAxisName:"数量/条",hoverText:"数量"},xAxisArr:[],yAxisArr:[]},pieData:{baseConfig:{title:"",hoverText:"百分比"},xAxisArr:[],yAxisArr:[]}},exportEchartsArr:[]}},created:function(){var a=this,t=new Date,e=new Date;e.setTime(e.getTime()-6048e5),this.dateVal=[e,t];var r=t.getMonth()+1,l=t.getDate();r<10&&(r="0"+(t.getMonth()+1)),l<10&&(l="0"+t.getDate()),this.searchParams.endtime=t.getFullYear()+"-"+r+"-"+l;var i=new Date(t);i.setDate(t.getDate()-7),this.searchParams.starttime=i.getFullYear()+"-"+(i.getMonth()+1)+"-"+i.getDate(),this.getAllEchartsData(this.searchParams),s.default.$on("allEchartbar",function(t){a.exportEchartsArr[0]=t}),s.default.$on("allEchartpie",function(t){a.exportEchartsArr[1]=t}),s.default.$on("allEchartsBarClick",function(t){a.$router.push({name:"searchLogs",params:{words:t.name}})})},methods:{clickss:function(a){this.$router.push({name:"searchLogs",params:{words:a.name}})},getAllEchartsData:function(a){var t=this;layer.load(1),this.$nextTick(function(){t.$axios.post(t.$baseUrl+"/log/getCountGroupByParam.do",t.$qs.stringify(a)).then(function(a){var e=a.data[0],r=[],s=[],l=[];for(var i in e){var n={};r.push(i),s.push(e[i]),n.value=e[i],n.name=i,l.push(n),layer.closeAll()}t.echartData.barData.xAxisArr=r,t.echartData.barData.yAxisArr=s,t.echartData.pieData.yAxisArr=l}).catch(function(a){layer.closeAll()})})},logTypeChange:function(){this.searchParams.type=this.logType,this.getAllEchartsData(this.searchParams)},exportEcharts:function(){var a=[];for(var t in this.exportEchartsArr)a.push(this.exportEchartsArr[t].getDataURL());var e=$("canvas"),r=e.width(),s=e.height();Object(l.c)(a,40,r,s)},changeDate:function(){this.searchParams.starttime=this.dateVal[0],this.searchParams.endtime=this.dateVal[1],this.getAllEchartsData(this.searchParams)}},components:{vEcharts:r.default}},n={render:function(){var a=this,t=a.$createElement,e=a._self._c||t;return e("div",{staticClass:"content-bg"},[e("div",{staticClass:"top-title"},[a._v("全部资产报表\n        "),e("el-dropdown",{staticClass:"exportEchartsMenu"},[e("span",{staticClass:"el-dropdown-link"},[a._v("\n                导出"),e("i",{staticClass:"el-icon-arrow-down el-icon--right"})]),a._v(" "),e("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[e("el-dropdown-item",{nativeOn:{click:function(t){a.exportEcharts()}}},[a._v("PDF格式")])],1)],1)],1),a._v(" "),e("div",{staticClass:"all-echarts-wapper"},[e("h2",{staticClass:"all-echarts-title"},[a._v("日志级别数量统计")]),a._v(" "),e("div",{staticClass:"all-echarts-condition"},[e("span",[a._v("日志类型：")]),a._v(" "),e("el-select",{attrs:{placeholder:"请选择"},on:{change:a.logTypeChange},model:{value:a.logType,callback:function(t){a.logType=t},expression:"logType"}},a._l(a.logTypeArr,function(a){return e("el-option",{key:a.value,attrs:{label:a.label,value:a.value}})})),a._v(" "),e("span",[a._v("日期范围：")]),a._v(" "),e("el-date-picker",{attrs:{type:"daterange",align:"right",size:"mini","value-format":"yyyy-MM-dd","range-separator":"-","start-placeholder":"开始日期","end-placeholder":"结束日期","picker-options":a.pickerOptions},on:{change:a.changeDate},model:{value:a.dateVal,callback:function(t){a.dateVal=t},expression:"dateVal"}})],1),a._v(" "),e("div",{staticClass:"all-echarts-content"},[e("el-row",[e("el-col",{attrs:{span:12}},[e("div",{staticClass:"grid-content bg-purple "},[e("v-echarts",{attrs:{echartType:"bar",echartData:this.echartData.barData,busName:{exportName:"allEchartbar",clickName:"allEchartsBarClick"}}})],1)]),a._v(" "),e("el-col",{attrs:{span:12}},[e("div",{staticClass:"grid-content bg-purple-light"},[e("v-echarts",{attrs:{echartType:"pie",echartData:this.echartData.pieData,busName:{exportName:"allEchartpie",clickName:"allEchartsBarClick"}}})],1)])],1)],1)])])},staticRenderFns:[]};var c=e("VU/8")(i,n,!1,function(a){e("ZB4s")},"data-v-b3013b4a",null);t.default=c.exports}});