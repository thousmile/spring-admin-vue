import request from '@/utils/request'

// 权限管理api

// 获取分页信息
export function getPermissionTree(params) {
  return request({ url: '/sys/permission/tree', method: 'get', data: params })
}

// 获取分页信息
export function getPermissionPage(params) {
  return request({ url: '/sys/permission/page', method: 'get', data: params })
}

// 获取列表信息
export function getPermissionAll() {
  return request({ url: '/sys/permission/all', method: 'get' })
}

// 根据ID 查询单个权限信息
export function getPermissionById(params) {
  return request({ url: '/sys/permission/info/' + params, method: 'get' })
}

// 新增权限
export function savePermission(params) {
  return request({ url: '/sys/permission', method: 'post', data: params })
}

// 修改权限
export function updatePermission(params) {
  return request({ url: '/sys/permission', method: 'put', data: params })
}

// 删除权限
export function removePermissionById(params) {
  return request({ url: '/sys/permission/' + params, method: 'delete' })
}

