<template>
    <div class="content-bg" v-loading="loading"  element-loading-background="rgba(26,36,47, 0.2)">
        <div class="top-title">仪表盘列表</div>
        <div class="tools-wapper">
            <el-button  type="primary" plain @click="goToHtml" >新建</el-button>
            <el-button  type="primary" plain @click="getDashboardsList" >刷新</el-button>
        </div>
        <div class="table-wapper">
            <v-basetable :tableHead="tableHead" :tableData="tableData"></v-basetable>
        </div>
    </div>
    
</template>

<script>
    import vBasetable from '../common/Basetable';
    import {jumpHtml} from "../../../static/js/common";
    import bus from '../common/bus';
    export default {
        name: "dashboardList",
        data() {
            return {
                loading:false,
                tableHead:[
                    {
                        prop:'title',
                        label:'标题',
                        width:'',
                    },
                    /*{
                        prop:'createTime',
                        label:'创建时间',
                        width:'',
                        formatData:(val)=>{return val.split('.')[0]}
                    },*/
                    {
                        prop:'description',
                        label:'描述',
                        width:'',
                    },
                    {
                        prop: 'tools',
                        label: '操作',
                        width: '100',
                        btns: [
                            {
                                icon: 'el-icon-view',
                                text: '查看',
                                clickFun: (row, index) => {
                                    jumpHtml('seeDashboard'+row.id,'dashboard/dashboard.vue',{name:row.title,id:row.id,type:'see'},' 查看');
                                }
                            },
                            {
                                icon: 'el-icon-edit',
                                text: '编辑',
                                formatData:(obj)=>{
                                    return obj.editable
                                },
                                clickFun: (row, index) => {
                                    //this.reviseChart(row,index)
                                    jumpHtml('resiveDashboard'+row.id,'dashboard/dashboard.vue',{name:row.title,id:row.id,type:'edit'},' 编辑');
                                }
                            },
                            {
                                icon: 'el-icon-error',
                                text: '删除',
                                formatData:(obj)=>{
                                    return obj.deletable
                                },
                                clickFun: (row, index) => {
                                    this.removeChart(row,index)
                                }
                            },
                        ]
                    },
                    {
                        prop:'',
                        label:'状态',
                        width:'60',
                        formatData:(val,row)=>{
                            if(!row.deletable && !row.editable){
                                return '<i class="el-icon-lock" style="color:#e4956d"></i>'
                            }
                        }
                    }
                ],
                tableData:[
                    // {id:'aaa',name:'日志级别数量统计',type:'bar',createTime:'2020-02-13 12:10:10'},
                    // {id:'bbb',name:'事件级别数量统计',type:'line',createTime:'2020-02-13 12:10:10'}
                ]
            }
        },
        created(){
            this.getDashboardsList();
        },
        methods:{
            /*跳转页面*/
            goToHtml(){
                this.$router.push({path:'dashboard'});
            },
            /*获取图表列表*/
            getDashboardsList(){
                this.$nextTick(()=>{
                    this.loading = true;
                    this.$axios.post(this.$baseUrl+'/BI/getDashboards.do','')
                        .then(res=>{
                            this.loading = false;
                            let obj =res.data;
                            if (obj.success == 'true'){
                                this.tableData = obj.data;
                            } else {
                                layer.msg(res.data.message,{icon:5})
                            }
                        })
                        .catch(err=>{
                            this.loading = false;
                            layer.msg('获取数据失败',{icon:5})
                        })
                })
            },
            /*修改按钮*/
            reviseChart(rowData,index){
                switch (rowData.type) {
                    case 'bar':
                        jumpHtml('barChart'+rowData.id,'dashboard/barChart.vue',{name:rowData.title,id:rowData.id},' 修改');
                        break;
                    case 'pie':
                        jumpHtml('pieChart'+rowData.id,'dashboard/pieChart.vue',{name:rowData.title,id:rowData.id},' 修改');
                        break;
                    case 'line':
                        jumpHtml('lineChart'+rowData.id,'dashboard/lineChart.vue',{name:rowData.title,id:rowData.id},' 修改');
                        break;
                }
            },
            /*删除*/
            removeChart(row,index){
                layer.confirm('您确定删除么？', {
                    btn: ['确定','取消'] //按钮
                }, (index)=> {
                    this.loading = true;
                    this.$nextTick(()=>{
                        this.$axios.post(this.$baseUrl+'/BI/deleteDashboardById.do',this.$qs.stringify({
                            id:row.id
                        }))
                            .then(res=>{
                                this.loading = false;
                                let obj =res.data
                                if (obj.success == 'true'){
                                    layer.msg(res.data.message,{icon:1})
                                    setTimeout(()=>{
                                        this.getDashboardsList();
                                    },1000)

                                } else {
                                    layer.msg(res.data.message,{icon:5})
                                }
                            })
                            .catch(err=>{
                                this.loading = false;

                            })
                    })
                })

            }
        },
        components:{
            vBasetable
        }

    }
</script>

<style scoped>
    .tools-wapper{
        padding: 0 10px;
        margin: 10px 0;
    }
    .table-wapper{
        padding: 0 10px;
    }
</style>
