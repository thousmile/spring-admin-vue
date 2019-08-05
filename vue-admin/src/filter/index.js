import Vue from 'vue'
// 时间格式化
import { formatDate } from '@/utils/index'

// 时间搓处理的过滤器
Vue.filter('formatDate', function(time) {
  // 返回处理后的值
  var date = new Date(time)
  return formatDate(date, 'yyyy-MM-dd')
})

// 时间搓处理的过滤器
Vue.filter('formatTime', function(time) {
  // 返回处理后的值
  var date = new Date(time)
  return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
})

// 状态显示过滤器
Vue.filter('showState', function(state) {
  // 判断状态是什么类型的
  let result = ''
  if (state === 0) {
    result = '被删除'
  } else if (state === 1) {
    result = '正常'
  } else {
    result = '被隐藏'
  }
  return result
})

// 状态显示过滤器
Vue.filter('showGender', function(gender) {
  // 判断状态是什么类型的
  let result = ''
  if (gender === 0) {
    result = '女'
  } else if (gender === 1) {
    result = '男'
  } else {
    result = '未知'
  }
  return result
})

