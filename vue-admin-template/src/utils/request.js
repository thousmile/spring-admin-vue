import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import router from '@/router'
import { getToken } from '@/utils/auth'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

const service = axios.create({
  /**
   * 如果要打包发布，切记，要修改
   * baseURL: '/api' 只适合开发的时候，解决前后端跨域的问题，
   * baseURL:process.env.VUE_APP_BASE_API 线上环境，不存在跨域的问题，所以不需要代理
   */
  baseURL: process.env.NODE_ENV === 'development' ? '/api' : process.env.VUE_APP_BASE_API,
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// request拦截器
service.interceptors.request.use(
  config => {
    if (getToken() !== '') {
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * status 为非 200 是抛错 可结合自己业务进行修改
     */
    const result = response.data
    if (result.status === 200) {
      return result.data
    } else if (result.status > 400000) {
      logout(result.message)
      return Promise.reject('error')
    } else {
      console.log('result', result)
      errorMessage(result.message)
      return Promise.reject(result.message || 'Error')
    }
  },
  error => {
    console.log('err', error) // for debug
    errorMessage(error.message)
    return Promise.reject(error)
  }
)

function errorMessage(msg) {
  Message({ message: msg, type: 'error', duration: 5000 })
}

function logout(msg) {
  MessageBox.confirm(msg || 'Error', '确定退出', {
    confirmButtonText: '重新登录',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    store.dispatch('FedLogOut').then(() => {
      // 为了重新实例化 vue-router 对象，清空之前的路由列表
      window.location.reload()
    })
    // 跳转到 首页
    router.push({ name: 'Dashboard' })
  })
}

export default service
