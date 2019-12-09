webpackJsonp([23],{JPXN:function(e,t){},w6WV:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=a("ID1p"),n=a("qTy0"),i=a("8+FI"),o={name:"auditLog",data:function(){return{selection:!0,busName:{formBusName:"auditLog",selectionName:"selectedAuditLogs"},formConditionsArr:[{label:"事件范围",type:"datetimerange",itemType:"",paramName:"time",model:{model:[]},val:""},{label:"部门",paramName:"departmentName",model:{model:""},itemType:"",type:"input"},{label:"用户",paramName:"userName",model:{model:""},itemType:"",type:"input"},{label:"账号",paramName:"account",model:{model:""},itemType:"",type:"input"},{label:"IP",paramName:"ip",model:{model:""},itemType:"",type:"input"}],tableHead:[{prop:"time",label:"操作时间",width:"150"},{prop:"userName",label:"用户",width:"100"},{prop:"account",label:"账号",width:"100"},{prop:"departmentName",label:"部门",width:"80"},{prop:"ip",label:"IP",width:"125"},{prop:"describe",label:"内容",width:""}],tableData:[],searchConditions:{userName:"",departmentName:"",startTime:"",endTime:"",ip:"",account:""},page:1,pageSize:15,c_page:1,allCounts:0,selectedIds:""}},created:function(){var e=this;i.default.$on(this.busName.formBusName,function(t){var a={userName:t.userName,departmentName:t.departmentName,startTime:t.startTime,endTime:t.endTime,ip:t.ip,account:t.account};e.searchConditions=a,e.c_page=1,e.getAuditLogData(1,e.searchConditions)}),i.default.$on(this.busName.selectionName,function(t){t.forEach(function(t){e.selectedIds+=t.id+","})}),this.getAuditLogData(1,this.searchConditions)},beforeDestroy:function(){i.default.$off(this.busName.formBusName),i.default.$off(this.busName.selectionName)},methods:{getAuditLogData:function(e,t){var a=this;layer.load(1),t.pageIndex=e,t.pageSize=this.pageSize,this.$nextTick(function(){a.$axios.post(a.$baseUrl+"/note/selectByPage.do",a.$qs.stringify(t)).then(function(e){layer.closeAll(),a.tableData=e.data[0].note[0],a.allCounts=Number(e.data[0].count.count)}).catch(function(e){layer.closeAll(),layer.msg("获取信息失败",{icon:5})})})},handleCurrentChange:function(e){this.getAuditLogData(e,this.searchConditions)},backupLogs:function(){var e=this;layer.confirm("您确定备份数据么？",{btn:["确定","取消"]},function(t){layer.close(t),layer.load(1),e.$nextTick(function(){e.$axios.post(e.$baseUrl+"/note/backup.do","").then(function(e){"true"===e.data.success?layer.msg(e.data.message,{icon:1}):"false"===e.data.success&&layer.msg(e.data.message,{icon:5})}).catch(function(e){layer.closeAll(),layer.msg("备份失败",{icon:5})})})},function(){layer.close()})},recoverLogs:function(){var e=this;layer.confirm("您确定还原数据么？",{btn:["确定","取消"]},function(t){layer.close(t),layer.load(1),e.$nextTick(function(){e.$axios.post(e.$baseUrl+"/note/restore.do","").then(function(e){"true"===e.data.success?layer.msg(e.data.message,{icon:1}):"false"===e.data.success&&layer.msg(e.data.message,{icon:5})}).catch(function(e){layer.closeAll(),layer.msg("备份失败",{icon:5})})})},function(){layer.close()})},removeLogs:function(){var e=this;""!==this.selectedIds?layer.confirm("您确定删除选中的日志么？",{btn:["确定","取消"]},function(t){layer.close(t),layer.load(1),e.$nextTick(function(){e.$axios.post(e.$baseUrl+"/note/deletes.do",e.$qs.stringify({ids:e.selectedIds})).then(function(t){"true"===t.data.success?(layer.msg(t.data.message,{icon:1}),e.getAuditLogData(1,e.searchConditions)):"false"===t.data.success&&layer.msg(t.data.message,{icon:5})}).catch(function(e){layer.closeAll(),layer.msg("删除失败",{icon:5})})})},function(){layer.close()}):layer.msg("未选中任何日志",{icon:5})},emptyLogs:function(){var e=this;layer.confirm("您确定清空数据么？",{btn:["确定","取消"]},function(t){layer.close(t),layer.load(1),e.$nextTick(function(){e.$axios.post(e.$baseUrl+"/note/deleteAll.do","").then(function(t){"true"===t.data.success?(layer.msg(t.data.message,{icon:1}),e.getAuditLogData(1,e.searchConditions)):"false"===t.data.success&&layer.msg(t.data.message,{icon:5})}).catch(function(e){layer.closeAll(),layer.msg("清空数据",{icon:5})})})},function(){layer.close()})},repeatLogs:function(){this.searchConditions={userName:"",departmentName:"",startTime:"",endTime:"",ip:"",account:""},this.getAuditLogData(1,this.searchConditions),this.c_page=1}},components:{vBasetable:n.default,vSearchForm:s.default}},c={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"content-bg"},[a("div",{staticClass:"top-title"},[e._v("审计日志")]),e._v(" "),a("div",{staticClass:"audit-search-wapper"},[a("v-search-form",{attrs:{formItem:e.formConditionsArr,busName:e.busName.formBusName}})],1),e._v(" "),a("div",{staticClass:"audit-btn-wapper"},[a("el-button",{attrs:{type:"primary",plain:"",size:"mini",title:"备份数据"},on:{click:e.backupLogs}},[a("i",{staticClass:"el-icon-printer"}),e._v("备份")]),e._v(" "),a("el-button",{attrs:{type:"success",plain:"",size:"mini",title:"还原备份数据"},on:{click:e.recoverLogs}},[a("i",{staticClass:"el-icon-sort"}),e._v("还原")]),e._v(" "),a("el-button",{attrs:{type:"info",plain:"",size:"mini",title:"清空"},on:{click:e.emptyLogs}},[a("i",{staticClass:"el-icon-delete"}),e._v("清空")]),e._v(" "),a("el-button",{attrs:{type:"danger",plain:"",size:"mini",title:"删除"},on:{click:e.removeLogs}},[a("i",{staticClass:"el-icon-close"}),e._v("删除")]),e._v(" "),a("el-button",{attrs:{type:"warning",plain:"",size:"mini",title:"刷新"},on:{click:e.repeatLogs}},[a("i",{staticClass:"el-icon-refresh"}),e._v("刷新")])],1),e._v(" "),a("div",{staticClass:"audit-table-wapper"},[a("v-basetable",{attrs:{selection:e.selection,tableHead:e.tableHead,tableData:e.tableData,busName:e.busName}})],1),e._v(" "),a("div",{staticClass:"user-page-wapper"},[e._v("\n        共检索到日志"),a("b",[e._v(e._s(e.allCounts))]),e._v("条\n        "),a("el-pagination",{attrs:{background:"",layout:"prev, pager, next","current-page":e.c_page,"page-size":e.pageSize,total:e.allCounts},on:{"current-change":e.handleCurrentChange,"update:currentPage":function(t){e.c_page=t},"update:current-page":function(t){e.c_page=t}}})],1)])},staticRenderFns:[]};var l=a("C7Lr")(o,c,!1,function(e){a("JPXN")},"data-v-67ac84e6",null);t.default=l.exports}});