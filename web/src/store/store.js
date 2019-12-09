import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


const state = { //一般通过外部文件引入的，东西很多的。
                //定义数据 --- 类似data
    logshowcolumn:{},
    closeState:false
}
const mutations = {
    /*//定义方法 --- 类似methods
    add(state){//state 是把上面的数据引入进来的
        state.num++;
    },
    minus(state){
        state.num--;
    }*/
    updateLogShowColumn(state,data){
        state.logshowcolumn = data;
    },
    updateCloseState(state,data){
        state.closeState = data;
    }
}
export default new Vuex.Store({
    state,
    mutations
});