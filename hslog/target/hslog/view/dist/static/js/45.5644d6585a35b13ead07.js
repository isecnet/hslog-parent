webpackJsonp([45],{"9RRZ":function(t,e){},yxFu:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s={name:"searchLogs",data:function(){return{layerObj:{detailData:{},layerState:!1},words:"",searchConditions:{words:""},searchUrl:"log/getLogListByContent.do",tableHead:[],firstSearch:!0}},created:function(){var t=this;this.tableHead=[{prop:"logtime",label:"时间",width:"150"},{prop:"operation_level",label:"级别",width:"100"},{prop:"type",label:"日志类型",width:"100"},{prop:"equipmentname",label:"资产名称",width:"125",clickFun:function(e){"unknown"===e.equipmentid?layer.msg("资产未知",{icon:5}):t.$router.push({name:"equipment",params:{equipmentid:e.equipmentid}})}},{prop:"ip",label:"IP",width:"125"},{prop:"operation_des",label:"日志内容",width:""},{prop:"tools",label:"操作",width:"50",btns:[{icon:"el-icon-tickets",text:"查看详情",btnType:"readDetails",clickFun:function(e,a){t.layerObj.layerState=!0,t.layerObj.detailData=e}}]}],void 0!==this.$route.params.words&&(this.words=this.$route.params.words,this.searchConditions=this.$route.params)},watch:{$route:function(t,e){this.$route.params.words&&(this.words=this.$route.params.words,this.searchConditions=this.$route.params)}},methods:{searchLogs:function(){this.searchConditions.words=this.words},goToEquipment:function(){console.log("ddd")}},components:{vLogscontent:a("AIWI").default}},o={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"content-bg"},[a("div",{staticClass:"top-title"},[t._v("日志检索")]),t._v(" "),a("div",{staticClass:"search-wapper"},[a("el-form",{staticClass:"demo-form-inline",attrs:{inline:!0,onsubmit:"return false"}},[a("el-form-item",[a("el-input",{staticClass:"input-searchword",attrs:{placeholder:"输入条件检索日志"},model:{value:t.words,callback:function(e){t.words=e},expression:"words"}})],1),t._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.searchLogs()}}},[a("i",{staticClass:"el-icon-search"}),t._v("查询")])],1)],1)],1),t._v(" "),a("div",{staticClass:"content-wapper"},[a("v-logscontent",{attrs:{searchConditions:t.searchConditions,tableHead:t.tableHead,searchUrl:t.searchUrl,layerObj:t.layerObj}})],1)])},staticRenderFns:[]};var r=a("C7Lr")(s,o,!1,function(t){a("9RRZ")},"data-v-1ed1ef0e",null);e.default=r.exports}});