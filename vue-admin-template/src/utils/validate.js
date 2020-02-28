import {
  testHtppUrl,
  testEmail,
  testLicensePlate,
  testPhone,
  testTelphone,
  testIdCard,
  testPassword,
  testUsername,
  testCompress,
  testDate,
  testImage } from './regular'

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(http?:|https?:|mailto:|tel:)/.test(path)
}

/* 校验正常的 http url */
export function validateHtppUrl(rule, value, callback) {
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testHtppUrl(value)) {
      callback(new Error('请输入正确的链接地址~'))
    } else {
      callback()
    }
  }
}

/* 校验正常的 邮箱 */
export function validateEmail(rule, value, callback) {
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testEmail(value)) {
      callback(new Error('请输入正确的邮箱账号~'))
    } else {
      callback()
    }
  }
}

/* 校验 车牌 */
export function validateLicensePlate(rule, value, callback) {
  value = value.replace(/\s+/g, '')
  value = value.toUpperCase()
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testLicensePlate(value)) {
      callback(new Error('请输入正确的车牌号码~'))
    } else {
      callback()
    }
  }
}

/* 校验 手机号码*/
export function validatePhone(rule, value, callback) {
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testPhone(value)) {
      callback(new Error('请输入正确的电话号码~'))
    } else {
      callback()
    }
  }
}

/* 校验 电话号码*/
export function validateTelphone(rule, value, callback) {
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testTelphone(value)) {
      callback(new Error('请输入正确的固话(格式：区号+号码,如010-1234567)'))
    } else {
      callback()
    }
  }
}

/* 校验 身份证号码*/
export function validateIdCard(rule, value, callback) {
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testIdCard(value)) {
      callback(new Error('请输入正确的身份证号码~'))
    } else {
      callback()
    }
  }
}

/* 校验 用户密码*/
export function validatePassword(rule, value, callback) {
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testPassword(value)) {
      callback(new Error('密码必须由字母、数字组成，区分大小写，长度为6-20位~'))
    } else {
      callback()
    }
  }
}

/* 校验 用户名称*/
export function validateUsername(rule, value, callback) {
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testUsername(value)) {
      callback(new Error('用户名，只能包含字母、数字下、划线、减号、长度为4-18位~'))
    } else {
      callback()
    }
  }
}

/* 校验 用户昵称*/
export function validateNickname(rule, value, callback) {
  value = value.trim()
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!value.trim()) {
      callback(new Error('用户名必须填写~'))
    } else {
      callback()
    }
  }
}

/* 校验 压缩包格式 */
export function validateCompress(rule, value, callback) {
  // 将文件转小写
  value = value.toLowerCase()
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testCompress(value)) {
      callback(new Error('压缩包后缀名只能是(zip|gz|rar|7z|tar|tar.gz)~'))
    } else {
      callback()
    }
  }
}

/* 校验 日期 */
export function validateDate(rule, value, callback) {
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testDate(value)) {
      callback(new Error('请输入正确的日期格式~'))
    } else {
      callback()
    }
  }
}

/* 校验 图片后缀名 */
export function validateImage(rule, value, callback) {
  // 将文件转小写
  value = value.toLowerCase()
  if (value === undefined || value === null || value === '') {
    callback()
  } else {
    if (!testImage(value)) {
      callback(new Error('图片后缀名只能是(.gif|.jpeg|.png|.jpg|.bmp)~'))
    } else {
      callback()
    }
  }
}
