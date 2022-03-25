import request from '@/utils/request'

// 系统日志

// 获取操作日志
export function getOperLogPage(params) {
  return request({ url: '/oper/log/query', method: 'get', params: params })
}

// 删除操作日志
export function removeOperLogById(params) {
  return request({ url: '/oper/log/batch', method: 'delete', data: params })
}

// 获取登录日志
export function getLoginLogPage(params) {
  return request({ url: '/login/log/query', method: 'get', params: params })
}

// 删除登录日志
export function removeLoginLogById(params) {
  return request({ url: '/login/log/batch', method: 'delete', data: params })
}
