<template>
    <div class="content-bg">
        <div id="dvCompute" style="position:absolute;visibility: hidden"></div>
        <div class="top-title">资产管理
            <div class="equipment-tools">
                <div class="equipemnt-tools-btns">
                    <!--<el-button type="primary" size="mini" plain @click="goToAddEquipment">添加资产</el-button>
                    <el-button type="danger" size="mini" plain  @click="removeEquipment">删除资产</el-button>-->
                    <el-button type="info" size="mini" plain ><a id="eqDownload" @click='downLoadEq'>模板下载</a></el-button>
                    <el-button type="warning" size="mini" plain @click="importEquipment()">资产导入</el-button>
                    <el-button type="primary" size="mini" plain @click="goToAddEquipment">添加资产</el-button>
                    <el-button type="danger" size="mini" plain  @click="removeEquipment">删除资产</el-button>
                    <el-button type="success" size="mini" plain  @click="refreshEq">刷新</el-button>
                </div>
            </div>
        </div>
        <div class="equipemnt-tools-form">
            <v-search-form :formItem="formConditionsArr" :busName="busName"></v-search-form>
        </div>
        <div class="equipment-table-wapper" v-loading="loading"  element-loading-background="rgba(48, 62, 78, 0.5)">
            <v-basetable :selection="true" :tableHead="tableHead" :tableData="tableData" :busName="busNames"></v-basetable>
        </div>
        <div class="equipment-table-page">
           <span>共检索到资产数量为 <b>{{allCounts}}</b> 台</span>
            <el-pagination background layout="prev, pager, next" @current-change="handleCurrentChange" :current-page.sync="c_page" :page-size="size" :total="allCounts"></el-pagination>
        </div>
        <el-dialog title="资产导入" :visible.sync="importState" width="680px" height="550px" class="dialog-wapper">
            <div class="back-state" v-if="backState">
                <p class="i-box"><i class="el-icon-success" v-if="this.backStateObj.state == 'true'" style="color:#279e72;"></i><i class="el-icon-error" v-else style="color:#e27145;"></i></p>
                <h3 class="back-h3" v-if="this.backStateObj.state == 'true'"  style="color:#279e72;">导入成功</h3>
                <h3 class="back-h3" v-else style="color:#e27145;">导入失败</h3>
                <p class="back-text">{{this.backStateObj.text}}</p>
                <p class="back-btn"><el-button type="primary" size="mini" @click="backState = false">返回导入界面</el-button></p>
            </div>
            <p>通过浏览本地文件或将文件拖到下面指定区域，上传资产文件。</p>
            <p>文件支持的类型：<span class="txtColor">.xlsx</span> <span class="txtColor">.xls</span> <span class="txtColor">.xlsm</span></p>
            <p>文件的名称必须为：<span class="txtColor">资产清单</span>  (例:资产清单.xlsx)</p>
            <div style="padding-right: 30px;">
                <input type="file" multiple id="ssi-upload"/>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="importState = false">取 消</el-button>
            </div>
        </el-dialog>
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
                busName:'searchEquipment',
                busNames:{
                    selectionName:'equipmentDelect'
                },
                importState:false,//导入框状态
                backState:false,//导入结果状态框显示与否
                page:1,//页码
                c_page:1,//当前页码
                size:15,//每页的数量
                logBaseJson:[],
                logType:[],//日志类型
                typeArr:[],//资产类型数组
                formConditionsArr:[],//查询数组
                searchConditions:{//查询条件
                    name:'',
                    hostname:'',
                    ip:'',
                    logType:''
                },
                saveCondition:'',//保存查询条件 用于分页
                tableHead : [],//表头
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
            this.getEquipmentType();
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
                    paramName:'hostname',
                    model:{
                        model:''
                    },
                    itemType:'',
                    type:'input'
                },
                {
                    label:'日志类型',
                    paramName:'logType',
                    type:'select',
                    itemType:'',
                    model:{
                        model:''
                    },
                    options:this.logType
                },
                {
                    label:'资产类型',
                    paramName:'type',
                    type:'cascader',
                    itemType:'',
                    model:{
                        model:[]
                    },
                    options:[]
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
        },
        mounted(){
            //获取资产列表数据
            //this.getEquipmentList(this.searchConditions,1)
            this.tableHead = [
                {
                    prop:'name',
                    label:'资产名称',
                    width:'200',
                    formatData:(val,obj)=>{
                        let d = document.getElementById('dvCompute');
                        d.innerHTML = obj.log_count + 15;
                        let w = d.offsetWidth;
                        return `<span title="${val}" style="display: inline-block;float:left;margin-right: 5px;max-width: calc(100% - ${w}px);overflow: hidden;text-overflow: ellipsis; ">${val}</span>
                                    <b title="今日日志数 " class="inNum" style="float:left;border: 1px solid #e4956d;color: #e4956d;padding: 0 5px;height: 18px;line-height: 18px;">${obj.log_count}</b>`;
                    }
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
                        let type = '';
                        const str = val.substring(0,2);
                        for (let n in this.typeArr){
                            let obj = this.typeArr[n];
                            if(obj.value == str){
                                type += obj.label +'-';
                                for(let j in obj.children){
                                    if(obj.children[j].value == val){
                                        type += obj.children[j].label;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        return type;
                    }

                },
                {
                    prop:'logType',
                    label:'日志类型',
                    width:''
                },
                {
                    prop:'ip',
                    label:'IP地址',
                    width:'125'
                },
                {
                    prop:'domain',
                    label:'根域名',
                    width:''
                },
                {
                    prop:'端口',
                    label:'port',
                    width:''
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
                                this.reviseEquipment(row,index)
                            }
                        },
                        {
                            icon:'el-icon-tickets',
                            text:'查看资产日志',
                            clickFun:(row,index)=>{
                                this.equipmentLogs(row,index)
                            }
                        },
                        {
                            icon:'el-icon-s-data',
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
                        }

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
            ]
        },
        methods:{
            /*刷新*/
            refreshEq(){
                this.c_page = 1;
                this.getEquipmentList(this.searchConditions,1);
            },
            /*资产导入*/
            importEquipment(){
                layer.confirm('此功能适用于实施人员，请谨慎操作。', {
                    btn: ['确定导入','取消'] //按钮
                }, (index)=> {
                    layer.close(index);
                    this.importState = true
                }, function(){
                    layer.close();
                })
            },
            /*下载资产模板*/
            downLoadEq(){
                console.log('ddd')
                let ahtml = document.getElementById('eqDownload');
                ahtml.href = this.$baseUrl+'/equipment/equipmentDownload.do';
                ahtml.click();
            },
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
                                    label:res.data[i].label
                                };
                                this.logType.push(obj);
                            }
                        })
                        .catch((err)=>{
                            console.log(err)
                        })
                })
            },
            /*获得资产类型数据*/
            getEquipmentType(){
                this.$nextTick(()=>{
                    this.$axios.get('static/filejson/equiptype.json',{})
                        .then((res)=>{
                            this.typeArr = res.data
                            this.formConditionsArr[this.formConditionsArr.length - 1].options = this.typeArr
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
                    this.$axios.post(this.$baseUrl+'/equipment/selectPage.do',this.$qs.stringify(obj))
                        .then((res) => {
                            this.loading = false;
                            //填充表格数据
                            //对数据进行预处理
                            this.tableData = res.data[0].equipment;
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
                    this.$axios.post(this.$baseUrl+'/equipment/selectEquipmentByLog.do',this.$qs.stringify({id:this.selectEquipmentId}))
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
            goToAddEquipment(){
                this.$router.push({path:'addEquipment2'});
            },
            /*跳转到所有资产报表*/
            goToAllEcharts(){
                this.$router.push({path:'allEcharts'})
            },
            /*删除资产*/
            removeEquipment(){
                if(this.checkList.length === 0){
                    layer.msg('未选中任何资产',{icon: 5});
                }else{
                    //询问框
                    layer.confirm('您确定删除么？', {
                        btn: ['确定','取消'] //按钮
                    }, (index)=>{
                        layer.close(index);
                        let delectEquipmentIds = ''
                        for(let i in this.checkList){
                            delectEquipmentIds += this.checkList[i]+','
                        }
                        this.$nextTick(()=>{
                            this.$axios.post(this.$baseUrl+'/equipment/delete.do',this.$qs.stringify({id:delectEquipmentIds}))
                                .then((res)=>{
                                    if(res.data.success == "true"){
                                        layer.msg("删除成功",{icon:1});
                                        this.getData(this.searchConditions,1)
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
            reviseEquipment(rowData,index){
                //跳转页面
                jumpHtml('reviseEquipment2'+rowData.id,'equipment/reviseEquipment2.vue',{ name:rowData.name,id: rowData.id },'资产修改')
            },
            /*查看资产日志*/
            equipmentLogs(rowData,index){
                //跳转页面
                jumpHtml('equipmentLogs2'+rowData.id,'logsManage/equipmentLogs2.vue',{ name:rowData.name,id: rowData.id ,logType:rowData.logType},'日志')
            },
            /*查看资产图表*/
            equipmentEcharts(rowData,index){
                //判断资产日志类型
                let logType = rowData.logType;
                if(logType === 'syslog'){
                    //跳转页面
                    jumpHtml('syslogEquipmentEcharts'+rowData.id,'equipment/syslogEquipmentEcharts.vue',{ name:rowData.name,id: rowData.id },'统计')
                }else if(logType === 'winlog' || logType === 'winlogbeat'){
                    //跳转页面
                    jumpHtml('winEquipmentEcharts'+rowData.id,'equipment/winEquipmentEcharts.vue',{ name:rowData.name,id: rowData.id },'统计')
                }else if(logType === 'metric'){
                    jumpHtml('equipmentDashboard'+rowData.id,'dashboard/dashboard.vue',{ name:rowData.name+'指标数据统计',eid: rowData.id,id:'y_qMB3IBmkPMjFRE7O-_',type:'EQedit' },'查看')
                }else{
                    layer.msg(`${logType} 类型资产暂无报表`,{icon:5})
                }
            },
            /*资产仪表盘*/
            equipmentDashboard(rowData,index){
                jumpHtml('equipmentDashboard'+rowData.id,'dashboard/dashboard.vue',{ name:rowData.name+'指标数据统计',eid: rowData.id,id:'y_qMB3IBmkPMjFRE7O-_',type:'EQedit' },'修改')
            },
            /*查看资产事件*/
            equipmentEvents(rowData,index){
                //跳转页面
                jumpHtml('equipmentEvents2'+rowData.id,'eventManage/equipmentEvents2.vue',{ name:rowData.name,id: rowData.id },'事件')
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
            },
        },
        watch: {
            'importState'(){
                if(this.importState === true){
                    setTimeout(()=>{
                        let $that = this;
                        $('#ssi-upload').ssi_uploader({
                            url:this.$baseUrl+'/equipment/insertEquipmentUpload.do',//地址
                            maxNumberOfFiles:1,
                            allowed:['xlsx','xls','xlsm'],//允许上传文件的类型
                            ajaxOptions: {
                                success: function(res) {
                                    let obj = JSON.parse(res)
                                    $that.backState = true;
                                    $that.backStateObj ={
                                        state:obj.success,
                                        text:obj.message
                                    }
                                    /*
                                            if(res.success === 'true'){
                                                layer.msg(res.message,{icon:1})
                                            }else if(res.success === 'false'){
                                                layer.msg(res.message,{icon:5})
                                            }*/
                                },
                                error:function(data){
                                    layer.msg("导入失败",{icon: 5});
                                    $(".ssi-abortUpload").click()
                                }
                            }
                        })
                    },100)

                }
            }
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
        float: right;
        margin-right: 10px;
    }
    .equipemnt-tools-btns{
        float: left;
    }
    .equipemnt-tools-btns button{
        margin: 0;
        background: 0;
    }
    .equipemnt-tools-form{
        /*float: right;*/
        margin-bottom: 20px;
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
    .dialog-wapper p{
        color: #fff;
        line-height: 30px;
    }
    .dialog-wapper p span{
        color: #4da5ff;
    }
</style>
