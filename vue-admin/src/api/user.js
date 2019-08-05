import request from '@/utils/request'

// 用户管理

// 获取分页信息
export function getUserPage(params) {
  return request({ url: '/user/page/info', method: 'get', data: params })
}

// 获取列表信息
export function getUserAll() {
  return request({ url: '/user/all', method: 'get' })
}

// 根据ID 查询单个用户信息
export function getUserById(params) {
  return request({ url: '/user/info/' + params, method: 'get' })
}

// 新增用户
export function saveUser(params) {
  return request({ url: '/user', method: 'post', data: params })
}

// 修改用户
export function updateUser(params) {
  return request({ url: '/user', method: 'put', data: params })
}

// 删除用户
export function removeUserById(params) {
  return request({ url: '/user/' + params, method: 'delete' })
}

// 修改用户权限
export function updateUserRoles(params) {
  return request({ url: '/role/update/user', method: 'post', data: params })
}

