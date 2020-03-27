import axios from 'axios'
import Qs from 'qs'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import router from '@/router'
import { getToken } from '@/utils/auth'

const service = axios.create({
  /**
   * 如果要打包发布，切记，要修改
   * baseURL: '/api' 只适合开发的时候，解决前后端跨域的问题，
   * baseURL:process.env.VUE_APP_BASE_API 线上环境，不存在跨域的问题，所以不需要代理
   *
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
    const method = config.method.toLocaleLowerCase()
    if (method === 'get') {
      // GET请求就序列化参数
      if (config.data !== undefined && config.data !== null) {
        config.url += ('?' + Qs.stringify(config.data))
      }
    }
    return config
  },
  error => {
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * status 为非 200 是抛错 可结合自己业务进行修改
     */
    const result = response.data
    if (result.status !== 200) {
      // 304 表示 参数检验失败
      if (result.status === 304) {
        Message({
          message: result.message,
          type: 'warning',
          duration: 5 * 1000
        })
      } else if (result.status === 401) {
        // 401 表示未经过授权
        MessageBox.confirm(
          '你已被登出，可以取消继续留在该页面，或者重新登录',
          '确定登出',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('FedLogOut').then(() => {
            // 为了重新实例化 vue-router 对象，清空之前的路由列表
            window.location.reload()
          })
          // 跳转到 首页
          router.push({ name: 'Dashboard' })
        })
      } else {
        resultErrorMessage(result.message)
      }
      return Promise.reject('error')
    } else {
      return result.data
    }
  },
  error => {
    resultErrorMessage(error.message)
    return Promise.reject(error)
  }
)

function resultErrorMessage(msg) {
  Message({ message: msg, type: 'error', duration: 5000 })
}

export default service
