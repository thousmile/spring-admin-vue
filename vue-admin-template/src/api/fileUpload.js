import request from '@/utils/request'

// 删除图片
export function deleteImage(params) {
  return request({ url: '/upload/delete', method: 'delete', data: { url: params }})
}
