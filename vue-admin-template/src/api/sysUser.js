import request from '@/utils/request'

/**
 * 用户登录
 * @param username  用户名长度不能少于五位
 * @param password  密码长度不能少于五位
 * @param codeText  验证码长度是四位
 * @param codeKey  验证码 KEY 不能为空
 * @returns
 */
export function login(params) {
  return request({ url: '/auth/login', method: 'post', data: params })
}

/**
 * 获取当前登录的用户信息
 */
export function getUserInfo() {
  return request({ url: '/user/info', method: 'get' })
}

/**
 * 用户退出登录
 */
export function logout() {
  return request({ url: '/auth/logout', method: 'post' })
}

/**
 * 刷新token
 */
export function refresh() {
  return request({ url: '/auth/refresh', method: 'get' })
}

// 获取分页信息
export function getUserPage(params) {
  return request({ url: '/user/query', method: 'get', params: params })
}

// 获取列表信息
export function getUserAll() {
  return request({ url: '/user/all', method: 'get' })
}

// 根据ID 查询单个用户信息
export function getUserByUserId(params) {
  return request({ url: '/user/' + params, method: 'get' })
}

// 获取所有用户
export function getAllSimpleUsers(params) {
  return request({ url: '/user/allUsers', method: 'get' })
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
  return request({ url: '/user/update/roles', method: 'post', data: params })
}

// 修改用户密码
export function updatePassword(params) {
  return request({ url: '/user/update/password', method: 'post', data: params })
}

// 重置用户密码
export function resetPassword(params) {
  return request({ url: '/user/reset/password', method: 'post', data: params })
}
