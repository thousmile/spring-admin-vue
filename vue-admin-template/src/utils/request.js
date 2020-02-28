import axios from 'axios'
import Qs from 'qs'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  // baseURL: '/api',
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
      // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      if (result.status === 401) {
        MessageBox.confirm(
          '你已被登出，可以取消继续留在该页面，或者重新登录',
          '确定登出',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          // 为了重新实例化vue-router对象 避免bug
          store.dispatch('FedLogOut').then(() => {
            location.reload()
          })
        })
      }

      // 系统内部错误
      Message({
        message: result.message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject('error')
    } else {
      return result.data
    }
  },
  error => {
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
