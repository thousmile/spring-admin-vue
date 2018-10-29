import request from '@/utils/request'

// 登录
export function login(username, password) {
  return request({ url: '/auth/login', method: 'post', data: { username, password }})
}

// 获取用户信息
export function getInfo(token) {
  return request({ url: 'user/info', method: 'get' })
}

// 登出
export function logout() {
  return request({ url: 'user/logout', method: 'post' })
}
