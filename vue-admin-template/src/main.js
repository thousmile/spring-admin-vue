import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css
import '@/icons' // icon
import '@/permission' // permission control
import '@/directive'
import { elementSize } from './settings'

Vue.use(ElementUI, { size: store.getters.size || elementSize })

import * as filters from './filters' // global filters

// 全局拦截器 filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

import ShowUserAvatar from '@/components/ShowUserAvatar'
Vue.component('ShowUserAvatar', ShowUserAvatar)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
