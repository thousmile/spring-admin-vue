// import parseTime, formatTime and set to filter
export { parseTime, formatTime } from '@/utils'

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

/**
 * Show plural label if time is plural number
 * @param {number} time
 * @param {string} label
 * @return {string}
 */
function pluralize(time, label) {
  if (time === 1) {
    return time + label
  }
  return time + label + 's'
}

/**
 * @param {number} time
 */
export function timeAgo(time) {
  const between = Date.now() / 1000 - Number(time)
  if (between < 3600) {
    return pluralize(~~(between / 60), ' minute')
  } else if (between < 86400) {
    return pluralize(~~(between / 3600), ' hour')
  } else {
    return pluralize(~~(between / 86400), ' day')
  }
}

/**
 * Number formatting
 * like 10000 => 10k
 * @param {number} num
 * @param {number} digits
 */
export function numberFormatter(num, digits) {
  const si = [
    { value: 1E18, symbol: 'E' },
    { value: 1E15, symbol: 'P' },
    { value: 1E12, symbol: 'T' },
    { value: 1E9, symbol: 'G' },
    { value: 1E6, symbol: 'M' },
    { value: 1E3, symbol: 'k' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (num >= si[i].value) {
      return (num / si[i].value).toFixed(digits).replace(/\.0+$|(\.[0-9]*[1-9])0+$/, '$1') + si[i].symbol
    }
  }
  return num.toString()
}

/**
 * 10000 => "10,000"
 * @param {number} num
 */
export function toThousandFilter(num) {
  return (+num || 0).toString().replace(/^-?\d+/g, m => m.replace(/(?=(?!\b)(\d{3})+$)/g, ','))
}

/**
 * Upper case first char
 * @param {String} string
 */
export function uppercaseFirst(string) {
  return string.charAt(0).toUpperCase() + string.slice(1)
}

// 性别 显示过滤器
export function showGender(gender) {
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
}

// 状态 显示过滤器
export function showStatus(state) {
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
}

// 状态 显示过滤器
export function formatDateTime(time) {
  var date = new Date(time)
  return dateTimeFormat(date, 'yyyy-MM-dd hh:mm:ss')
}

// 时间搓处理的过滤器
export function formatDate(time) {
  // 返回处理后的值
  var date = new Date(time)
  return dateTimeFormat(date, 'yyyy-MM-dd')
}
