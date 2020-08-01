import request from '@/utils/request'

// 删除 oss 上的图片
export function deleteImage(params) {
  return request({ url: '/upload/delete', method: 'delete', data: { url: params }})
}
