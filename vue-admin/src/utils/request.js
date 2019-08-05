import axios from 'axios'
import {
  Message,
  MessageBox
} from 'element-ui'
import store from '@/store'
import Qs from 'qs'
import {
  getToken
} from '@/utils/auth'

// 创建axios实例
const service = axios.create({
  baseURL: '/api', // 开发环境使用代理
  // baseURL: process.env.BASE_API,  //生产环境使用这个，不需要代理
  timeout: 50000, // 请求超时时间
  responseType: 'json',
  withCredentials: false, // 是否允许带cookie这些
  headers: {
    'Content-Type': 'application/json; charset=UTF-8'
  }
})

// request拦截器
service.interceptors.request.use(
  config => {
    if (getToken() !== '') {
      config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    const method = config.method.toLocaleLowerCase()
    if (method === 'get') {
      // GET请求就序列化参数
      config.url += ('?' + Qs.stringify(config.data))
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.status !== 200) {
      // 400:非法的token; 401:权限不足;  403:Token 过期了;
      if (res.status === 400 || res.status === 401 || res.status === 403) {
        MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出',
          { confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('FedLogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      } else {
        Message({
          message: res.message,
          type: 'error',
          duration: 5 * 1000
        })
      }
      return Promise.reject('error')
    } else {
      return res
    }
  },
  error => {
    const errorData = error.response.data
    console.log('request error :', error)
    // 400:非法的token; 401:权限不足;  403:Token 过期了;
    if (errorData.status === 400 || errorData.status === 401 || errorData.status === 403) {
      MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出',
        { confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(() => {
        store.dispatch('FedLogOut').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        })
      })
    } else {
      Message({
        message: errorData.message,
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(errorData)
  }
)

export default service
