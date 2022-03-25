import request from '@/utils/request'

// 权限管理api

// 获取分页信息
export function getPermissionPage(params) {
  return request({ url: '/permission/query', method: 'get', params: params })
}

// 获取树节点
export function getPermissionTree(params) {
  return request({ url: '/permission/tree', method: 'get', params: params })
}

// 获取列表信息
export function getPermissionAll() {
  return request({ url: '/permission/all', method: 'get' })
}

// 根据ID 查询单个权限信息
export function getPermissionById(id) {
  return request({ url: '/permission/' + id, method: 'get' })
}

// 新增权限
export function savePermission(params) {
  return request({ url: '/permission', method: 'post', data: params })
}

// 修改权限
export function updatePermission(params) {
  return request({ url: '/permission', method: 'put', data: params })
}

// 删除权限
export function removePermissionById(id) {
  return request({ url: '/permission/' + id, method: 'delete' })
}

