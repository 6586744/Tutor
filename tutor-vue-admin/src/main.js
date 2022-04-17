import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

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

//聊天组件
import Chat from 'jwchat';
// import 'jwchat/lib/JwChat.css';
Vue.use(Chat)

//表格批量导入导出相关
// import FileSave from 'file-saver'
// import xlsx from "xlsx"
// Vue.use(FileSave)
// Vue.use(xlsx)

import '@/styles/index.scss' // global css

import App from './App'
// import axios from 'axios'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
import { mockXHR } from '../mock'
if (process.env.NODE_ENV === 'production') {
  mockXHR()
}

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })

Vue.config.productionTip = false
// Vue.prototype.$axios = axios

function getServerConfig() {
  // return new Promise((resolve, reject) => {
  //   axios.get('./serverConfig.json').then((result) => {
  //     console.log(result) // 看打印出来的结果
  //     const config = result.data
  //     for (const key in config) {
  //       Vue.prototype[key] = config[key]
  //     }
  //     // 验证是否已经把属性挂在了Vue上
  //     console.log(Vue.prototype.BASE_ADDR)
  //     resolve()
  //   }).catch((error) => {
  //     console.log(error)
  //     reject()
  //   })
  // })
}

//给所有子组件显示的使用上axios
import Axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, Axios)
Axios.defaults.baseURL = "https://192.168.0.110:8083"

//实现前端路由拦截功能
router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) {
      if (store.state.user.userInfo.user) {
        next()
      } else {
        next({
          path: '/login',
          query: {redirect: to.fullPath}
        })
      }
    } else {
      next()
    }
  }
)



async function init() {
  await getServerConfig()
  // axios.defaults.baseURL = Vue.prototype.BASE_API
  new Vue({
    router,
    store,
    render: h => h(App)
  }).$mount('#app')
}

init()
