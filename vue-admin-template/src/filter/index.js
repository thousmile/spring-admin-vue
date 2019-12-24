import Vue from 'vue'

/**
 * 格式化时间
 * @param {*} date
 * @param {*} fmt
 */
function dateTimeFormat(date, fmt) {
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(
      RegExp.$1,
      (date.getFullYear() + '').substr(4 - RegExp.$1.length)
    )
  }
  const o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  }
  for (const k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      const str = o[k] + ''
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length === 1 ? str : ('00' + str).substr(str.length)
      )
    }
  }
  return fmt
}

// 时间搓处理的过滤器
Vue.filter('formatDate', function(time) {
  // 返回处理后的值
  var date = new Date(time)
  return dateTimeFormat(date, 'yyyy-MM-dd')
})

// 时间搓处理的过滤器
Vue.filter('formatDateTime', function(time) {
  // 返回处理后的值
  var date = new Date(time)
  return dateTimeFormat(date, 'yyyy-MM-dd hh:mm:ss')
})

// 状态显示过滤器
Vue.filter('showStatus', function(state) {
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
