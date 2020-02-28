
/* 校验正常的 http url */
export function testHtppUrl(value) {
  const reg = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\*\+,;=.]+$/
  return reg.test(value)
}

/* 校验正常的 邮箱 */
export function testEmail(value) {
  const reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
  return reg.test(value)
}

/* 校验 车牌 */
export function testLicensePlate(value) {
  const reg = /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][a-zA-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][a-zA-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/
  return reg.test(value)
}

/* 校验 手机号码*/
export function testPhone(value) {
  const reg = /^[1][3,4,5,7,8,9][0-9]{9}$/
  return reg.test(value)
}

/**
 * 电话号码
*/
export function testTelphone(value) {
  const reg = /^(\d3,4|\d{3,4}-)?\d{7,8}$/
  return reg.test(value)
}

/**
 * 身份证号码
*/
export function testIdCard(value) {
  const reg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
  return reg.test(value)
}

/**
 * 用户密码
*/
export function testPassword(value) {
  const reg = /^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$/
  return reg.test(value)
}

/**
 * 用户名称
*/
export function testUsername(value) {
  const reg = /^[a-zA-Z0-9_-]{4,18}$/
  return reg.test(value)
}

/**
 * 压缩包格式
*/
export function testCompress(value) {
  const reg = /^[^\\\/:\*\?"<>\|]+\.(zip|gz|rar|7z|tar|xz|bz2|tar.gz|tar.xz|tar.bz2|tar.7z)$/
  return reg.test(value)
}

/**
 * 日期
*/
export function testDate(value) {
  const reg = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/
  return reg.test(value)
}

/**
 * 图片后缀名
*/
export function testImage(value) {
  const reg = /\w(\.gif|\.jpeg|\.png|\.jpg|\.bmp)/
  return reg.test(value)
}
