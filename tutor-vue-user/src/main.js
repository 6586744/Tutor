import Vue from 'vue'
import App from './App'
import router from "./router";

import store from './store/modules/user'

//导入elementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Axios.defaults.baseURL = "https://192.168.0.110:8083"

//mavonEditor 编辑器
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use
Vue.use(mavonEditor)

//百度地图

import BaiduMap from 'vue-baidu-map'
Vue.use(BaiduMap, {
  ak: "AT7qqA2EssnxNrpaIGQvXI0mzUz4PoE0"
})

Vue.config.productionTip = false

//显示的使用路由
Vue.use(ElementUI)
//给所有子组件显示的使用上axios
import Axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, Axios)


//实现前端路由拦截功能
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    console.log("requireAuth : true")
    if (store.state.user.u_name) {
      next()
    } else {
      next({
        path: '/Login',
        query: { redirect: to.fullPath }
      })
    }
  } else {
    next()
  }
}
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
