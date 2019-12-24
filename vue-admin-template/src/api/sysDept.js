import request from '@/utils/request'

// 部门管理

// 获取列表信息
export function getDeptTree() {
  return request({ url: '/sys/department/tree', method: 'get' })
}

// 获取列表信息
export function getDeptPage(params) {
  return request({ url: '/sys/department/page', method: 'get', data: params })
}

// 获取列表信息
export function getDeptAll() {
  return request({ url: '/sys/department/all', method: 'get' })
}

// 根据ID 查询单个部门信息
export function getDeptById(params) {
  return request({ url: '/sys/department/' + params, method: 'get' })
}

// 新增部门
export function saveDept(params) {
  return request({ url: '/sys/department', method: 'post', data: params })
}

// 修改部门
export function updateDept(params) {
  return request({ url: '/sys/department', method: 'put', data: params })
}

// 删除部门
export function removeDeptById(params) {
  return request({ url: '/sys/department/' + params, method: 'delete' })
}
