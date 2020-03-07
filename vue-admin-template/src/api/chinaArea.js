import request from '@/utils/request'

// 获取分页信息
export function getChinaAreaTree() {
  return request({ url: '/china/area/tree', method: 'get' })
}

// 获取列表信息
export function getChinaAreaAll(params) {
  return request({ url: '/china/area/all/' + params, method: 'get' })
}

// 根据ID 查询单个区域信息
export function getChinaAreaById(params) {
  return request({ url: '/china/area/' + params, method: 'get' })
}
