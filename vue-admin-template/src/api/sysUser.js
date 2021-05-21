import request from '@/utils/request'

/**
 * 用户登录
 *
 * @export
 * @param {*} params
 * @returns
 */
export function login(params) {
  return request({ url: '/auth/login', method: 'post', data: params })
}

/**
 * 获取当前登录的用户信息
 *
 * @export
 * @returns
 */
export function getUserInfo() {
  return request({ url: '/sys/user/info', method: 'get' })
}

/**
 * 用户退出登录
 *
 * @export
 * @param {*} params
 * @returns
 */
export function logout() {
  return request({ url: '/auth/logout', method: 'get' })
}

// 获取分页信息
export function getUserPage(params) {
  return request({ url: '/sys/user/info/page', method: 'get', data: params })
}

// 获取列表信息
export function getUserAll() {
  return request({ url: '/user/all', method: 'get' })
}

// 根据ID 查询单个用户信息
export function getUserByUid(params) {
  return request({ url: '/sys/user/' + params, method: 'get' })
}

// 新增用户
export function saveUser(params) {
  return request({ url: '/sys/user', method: 'post', data: params })
}

// 修改用户
export function updateUser(params) {
  return request({ url: '/sys/user', method: 'put', data: params })
}

// 删除用户
export function removeUserById(params) {
  return request({ url: '/sys/user/' + params, method: 'delete' })
}

// 修改用户权限
export function updateUserRoles(params) {
  return request({ url: '/sys/user/update/roles', method: 'post', data: params })
}


// 修改用户密码
export function updatePassword(params) {
  return request({ url: '/sys/user/update/password', method: 'post', data: params })
}

// 重置用户密码
export function resetPassword(params) {
  return request({ url: '/sys/user/reset/password', method: 'post', data: params })
}
