import request from '@/utils/request'

// 角色管理

// 获取分页信息
export function getRolePage(params) {
  return request({ url: '/role/page', method: 'get', data: params })
}

// 获取列表信息
export function getRoleAll() {
  return request({ url: '/role/all', method: 'get' })
}

// 根据ID 查询单个角色信息
export function getRoleById(params) {
  return request({ url: '/role/info/' + params, method: 'get' })
}

// 新增角色
export function saveRole(params) {
  return request({ url: '/role', method: 'post', data: params })
}

// 修改角色
export function updateRole(params) {
  return request({ url: '/role', method: 'put', data: params })
}

// 删除角色
export function removeRoleById(params) {
  return request({ url: '/role/' + params, method: 'delete' })
}

// 修改角色权限
export function updateRolePermission(params) {
  return request({ url: '/role/update/permission', method: 'post', data: params })
}

