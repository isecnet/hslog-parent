<template>
    <div class="content-bg">
        <div class="top-title">逻辑资产管理</div>
        <div class="equipemnt-tools-form">
            <v-search-form :formItem="formConditionsArr" :busName="busName"></v-search-form>
        </div>
        <div class="equipment-tools">
            <div class="equipemnt-tools-btns">
                <el-button type="primary" size="mini" plain @click="goToAddLogicAssets">添加资产</el-button>
                <el-button type="danger" size="mini" plain  @click="removeLogicAssets">删除资产</el-button>
            </div>
        </div>
        <div class="equipment-table-wapper" v-loading="loading"  element-loading-background="rgba(48, 62, 78, 0.5)">
            <v-basetable :selection="true" :tableHead="tableHead" :tableData="tableData" :busName="busNames"></v-basetable>
        </div>
        <div class="equipment-table-page">
           <span>共检索到资产数量为 <b>{{allCounts}}</b> 台</span>
            <el-pagination background layout="prev, pager, next" @current-change="handleCurrentChange" :current-page.sync="c_page" :page-size="size" :total="allCounts"></el-pagination>
        </div>
    </div>
</template>

<script>
    import vSearchForm from '../common/BaseSearchForm';
    import vBasetable from '../common/Basetable';
    import {jumpHtml} from "../../../static/js/common";
    import bus from '../common/bus';
    export default {
        name: "equipment",
        data(){
            return{
                loading:false,
                busName:'logicAssetsList',
                busNames:{
                    selectionName:'logicAssetsDelect'
                },
                page:1,//页码
                c_page:1,//当前页码
                size:15,//每页的数量
                logBaseJson:[],
                logType:[],//日志类型
                typeArr:[],//资产类型数组
                formConditionsArr:[],//查询数组
                searchConditions:{//查询条件
                    name:'',
                    hostName:'',
                    ip:'',
                    type:''
                },
                saveCondition:'',//保存查询条件 用于分页
                tableHead : [//表头
                    {
                        prop:'name',
                        label:'资产名称',
                        width:'',
                    },
                    {
                        prop:'hostName',
                        label:'主机名',
                        width:''
                    },
                    {
                        prop:'type',
                        label:'资产类型',
                        width:'',
                        formatData:(val)=>{
                            if(val == '01'){
                                return '实体机'
                            }else if(val =='02'){
                                return '虚拟机'
                            }else if(val == '03'){
                                return '微服务(类Docker)'
                            }
                            return type;
                        }

                    },
                    {
                        prop:'ip',
                        label:'IP地址',
                        width:'125'
                    },
                    {
                        prop:'startUp',
                        label:'是否启用',
                        width:'',
                        formatData:(val)=>{return val == '1' ? '是' : '否'}
                    },
                    {
                        prop:'createTime',
                        label:'创建时间',
                        width:'',
                        formatData:(val)=>{return val.split('.')[0]}
                    },
                    {
                        prop:'tools',
                        label:'操作',
                        width:'',
                        btns:[
                            {
                                icon:'el-icon-edit',
                                text:'修改资产',
                                clickFun:(row,index)=>{
                                    this.reviseLogicAssets(row,index)
                                }
                            }/*,
                            {
                                icon:'el-icon-tickets',
                                text:'查看资产日志',
                                clickFun:(row,index)=>{
                                    this.equipmentLogs(row,index)
                                }
                            },
                            {
                                icon:'el-icon-share',
                                text:'查看资产图表',
                                btnType: 'equipmentEcharts',
                                clickFun:(row,index)=>{
                                    this.equipmentEcharts(row,index)
                                }
                            },
                            {
                                icon:'el-icon-date',
                                text:'查看资产事件',
                                btnType: 'equipmentEvents',
                                clickFun:(row,index)=>{
                                    this.equipmentEvents(row,index)
                                }
                            },
                            {
                                icon:'el-icon-bell',
                                text:'设置安全策略',
                                btnType: 'setSafe',
                                clickFun:(row,index)=>{
                                    this.setSafe(row,index)
                                }
                            },
                            {
                                icon:'el-icon-view',
                                text:'潜在威胁分析',
                                btnType: 'theartAnalyse',
                                clickFun:(row,index)=>{
                                    this.theartAnalyse(row,index)
                                }
                            }*/

                        ]
                    },
                    {
                        prop:'threats',
                        label:'',
                        width:'70',
                        formatData:(val,obj)=>{
                            let tipsHtml = '';
                            if(obj.high_risk !== 0 ){
                                tipsHtml += `<span title="高危事件数${obj.high_risk}" style="display:inline-block;width: 20px;height:20px;border-radius: 100%;background: #f55446;text-align: center">${obj.high_risk}</span>`
                            }
                            if(obj.moderate_risk !== 0 ){
                                tipsHtml += `<span title="中危事件数${obj.moderate_risk}" style="display:inline-block;width: 20px;height:20px;border-radius: 100%;background: #f1ae09;text-align: center">${obj.moderate_risk}</span>`
                            }
                            return tipsHtml;
                        }
                    }
                ],//表头
                tableData:[],//表格数据
                firstGetData:true,
                allCounts:0, //总条数
                selectEquipmentId:'',//日志页面跳转过来的资产id
                delectEquipmentIds:''//删除的资产集合
            }
        },
        created(){
            //获取日志类型数据
            this.getLogType();
            //this.getEquipmentType();
            this.formConditionsArr=[
                {
                    label:'资产名称',
                    paramName:'name',
                    model:{
                        model:''
                    },
                    itemType:'',
                    type:'input'
                },
                {
                    label:'IP地址',
                    paramName:'ip',
                    itemType:'',
                    model:{
                        model:''
                    },
                    type:'input'
                },
                {
                    label:'主机名',
                    paramName:'hostName',
                    model:{
                        model:''
                    },
                    itemType:'',
                    type:'input'
                },
                {
                    label:'资产类型',
                    paramName:'type',
                    type:'select',
                    itemType:'',
                    model:{
                        model:''
                    },
                    options:[
                        {
                            label:'全部',
                            value:''
                        },
                        {
                            label:'实体机',
                            value:'01'
                        },
                        {
                            label:'虚拟机',
                            value:'02'
                        },
                        {
                            label:'微服务(类Docker)',
                            value:'03'
                        }
                    ]
                }
            ]
            //监听查询条件组件传过来的条件
            bus.$on(this.busName,(params)=>{
                this.searchConditions = params;
                this.getEquipmentList(this.searchConditions,1)
                this.c_page = 1;
            })
            //监听选中的资产
            bus.$on(this.busNames.selectionName,(params)=>{
                this.delectEquipmentIds = '';
                for(let i in params){
                    this.delectEquipmentIds += params[i].id +','
                }
                //console.log(this.delectEquipmentIds)
            })
        },
        beforeDestroy(){
            //销毁
            bus.$off(this.busName)
            bus.$off(this.busNames.selectionName)
        },
        mounted(){
            //获取资产列表数据
            //this.getEquipmentList(this.searchConditions,1)
        },
        methods:{
            /*获得日志类型*/
            getLogType(){
                this.$nextTick(()=>{
                    this.$axios.get('static/filejson/logTypeLevel.json',{})
                        .then((res)=>{
                            this.logBaseJson = res.data;
                            this.logType.push({value:'',label:'全部'});
                            for(let i=0;i<res.data.length; i++){
                                let obj = {
                                    value:res.data[i].type,
                                    label:res.data[i].type
                                };
                                this.logType.push(obj);
                            }
                        })
                        .catch((err)=>{
                            console.log(err)
                        })
                })
            },
            /*获取资产列表*/
            getEquipmentList(searchObj,page){
                this.loading = true;
                let obj = searchObj;
                obj.pageIndex = page;//当前页
                obj.pageSize = this.size;//页的条数
                //请求
                this.$nextTick( ()=> {
                    this.$axios.post(this.$baseUrl+'/asset/selectPage.do',this.$qs.stringify(obj))
                        .then((res) => {
                            this.loading = false;
                            //填充表格数据
                            //对数据进行预处理
                            this.tableData = res.data[0].asset;
                            //this.tableData = arr;
                            //第一次请求
                           /* if(this.firstGetData){
                                this.allCounts = JSON.parse(res.data[0].count.count);
                                ///this.c_page = 1;
                            }*/
                            this.allCounts = JSON.parse(res.data[0].count.count);
                            //将查询条件保存，用于分页
                            this.saveCondition = this.searchConditions;

                        })
                        .catch((err) => {
                            this.loading = false;
                            console.log(err);
                        })
                })
            },
            /*查询资产*/
            selectEquipment(){
                this.$nextTick(()=>{
                    this.loading = true;
                    this.$axios.post(this.$baseUrl+'/asset/selectAsset.do',this.$qs.stringify({id:this.selectEquipmentId}))
                        .then(res=>{
                            this.loading = false;
                            if(res.data.success === 'false'){
                                layer.msg(res.data.message,{icon:5});
                            }else{
                                let arr = res.data.equipment;
                                for(let i=0;i<arr.length;i++){
                                    //时间类型处理
                                    if(arr[i].createTime){
                                        arr[i].createTime = arr[i].createTime.split(".")[0];
                                    }
                                    if(arr[i].upDateTime){
                                        arr[i].upDateTime = arr[i].upDateTime.split(".")[0];
                                    }
                                    //console.log(arr[i].endTime)
                                    if(arr[i].endTime){
                                        arr[i].endTime = arr[i].endTime.split(".")[0];
                                    }

                                    //是否启用
                                    if(arr[i].startUp == '1'){
                                        arr[i].startUp = '是'
                                    }else{
                                        arr[i].startUp = '否'
                                    }
                                    //资产类型
                                    //分割出前两位
                                    let type = '';
                                    const str = arr[i].type.substring(0,2);
                                    for (let n in this.typeArr){
                                        let obj = this.typeArr[n];
                                        if(obj.value == str){
                                            type += obj.label +'-';

                                            for(let j in obj.children){
                                                if(obj.children[j].value == arr[i].type){
                                                    type += obj.children[j].label;
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    arr[i].type = type;
                                    //console.log(arr)
                                }
                                this.tableData = arr;

                            }
                        })
                        .catch(err=>{
                            this.loading = false;
                            console.log(err)
                        })
                })
            },
            /*分页页码改变*/
            handleCurrentChange(page){
                //获取数据
                this.getEquipmentList(this.saveCondition,page);
                //改变标识
                this.firstGetData = false;
            },
            /*跳转到添加资产页面*/
            goToAddLogicAssets(){
                this.$router.push({path:'addLogicAssets'});
            },
            /*删除资产*/
            removeLogicAssets(){
                if(this.delectEquipmentIds === ''){
                    layer.msg('未选中任何资产',{icon: 5});
                }else{
                    //询问框
                    layer.confirm('您确定删除么？', {
                        btn: ['确定','取消'] //按钮
                    }, (index)=>{
                        layer.close(index);
                        this.$nextTick(()=>{
                            this.$axios.post(this.$baseUrl+'/asset/delete.do',this.$qs.stringify({id:this.delectEquipmentIds}))
                                .then((res)=>{
                                   if(res.data.success =='true'){
                                       layer.msg("删除成功",{icon:1});
                                       this.getEquipmentList(this.searchConditions,1)
                                       this.c_page = 1;
                                   }else{
                                       layer.msg("删除失败",{icon:5});
                                   }

                                })
                                .catch((err)=>{
                                    layer.msg("删除失败",{icon:5});
                                })
                        })
                    }, function(){
                        layer.close();
                    })
                }
            },
            /*修改资产按钮*/
            reviseLogicAssets(rowData,index){
                //跳转页面
                jumpHtml('reviseLogicAssets'+rowData.id,'equipment/reviseLogicAssets.vue',{ name:rowData.name,id: rowData.id },'逻辑资产修改')
            },
            /*查看资产日志*/
            equipmentLogs(rowData,index){
                //跳转页面
                jumpHtml('equipmentLogs'+rowData.id,'logsManage/equipmentLogs.vue',{ name:rowData.name,id: rowData.id ,logType:rowData.logType},'日志')
            },
            /*查看资产图表*/
            equipmentEcharts(rowData,index){
                //跳转页面
                jumpHtml('equipmentEcharts'+rowData.id,'equipment/equipmentEcharts.vue',{ name:rowData.name,id: rowData.id ,logType:rowData.logType},'统计')
            },
            /*查看资产事件*/
            equipmentEvents(rowData,index){
                //跳转页面
                jumpHtml('equipmentEvents'+rowData.id,'eventManage/equipmentEvents.vue',{ name:rowData.name,id: rowData.id },'事件')
            },
            /*设置安全策略*/
            setSafe(rowData,index){
                //跳转页面
                jumpHtml('equipmentSafe'+rowData.id,'equipment/equipmentSafe.vue',{ name:rowData.name,id: rowData.id ,logType:rowData.logType},'安全策略')
            },
            /*潜在威胁分析*/
            theartAnalyse(rowData,index){
                //跳转页面
                jumpHtml('equipmentThreat'+rowData.id,'equipment/equipmentThreat.vue',{ name:rowData.name,id: rowData.id ,logType:rowData.logType},'威胁分析')
            }
        },
        watch: {
     /*       '$route' (to, from) {
                if(from.name === 'addLogicAssets'|| from.name.indexOf('reviseLogicAssets') !== -1){
                    this.getEquipmentList(this.searchConditions,1);
                    this.c_page = 1;
                }
            }*/
        },
        beforeRouteEnter(to, from, next) {
            next (vm => {
                // 这里通过 vm 来访问组件实例解决了没有 this 的问题
                //console.log(to.params.equipmentid)
                if(to.params.equipmentid !== undefined){
                    vm.selectEquipmentId = to.params.equipmentid;
                    vm.selectEquipment();
                }else {
                    vm.getEquipmentList(vm.searchConditions,1)
                }

            })
        },
        components:{
            vSearchForm,
            vBasetable
        }
    }
</script>

<style scoped>
    .equipment-tools{
        height: 30px;
        padding: 0 20px;
        margin-bottom: 30px;
    }
    .equipemnt-tools-btns{
        float: left;
        margin: 10px 0;
    }
    .equipemnt-tools-btns button{
        margin: 0;
        background: 0;
    }
    .equipemnt-tools-form{
        float: right;
    }
    .equipment-table-wapper{
        min-height: 600px;
        padding:0 10px;
    }
    .equipment-table-page{
        border-top: 1px solid #303e4e;
        height: 40px;
        display: flex;
        justify-content: flex-end;
        align-items:center;
    }
    .equipment-table-page b{
        color: #e4956d;
    }
    .inNum {
        position: relative;
        display: inline-block;
        /* background: #e4956d; */
        /* background: #FF8547; */
        border: 1px solid #e4956d;
        color: #e4956d;
        padding: 0 5px;
        /* border-radius: 20%; */
        font-size: 10px;
        left: 7px;
        /* top: -5px; */
    }
    .higeThreat , middleThreat{
        display: inline-block;
        width: 21px;
        height: 21px;
        color: #fff;
        border-radius: 100%;
        font-size: 12px;
        text-align: center;
        margin-top: 8px;
        cursor: pointer;
        padding: 3px;
        -webkit-transform-origin-x: 0;
        -webkit-transform: scale(0.80);
    }
</style>
