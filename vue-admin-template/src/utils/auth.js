import Cookies from 'js-cookie'

// token 值 在 Cookie 中key名称
const TokenKey = 'Token-Value'

// 请求头 在 Cookie 中key名称
const HeaderKey = 'Token-Header'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function getHeader() {
  return Cookies.get(HeaderKey)
}

export function setToken(header, token) {
  Cookies.set(HeaderKey, header)
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  Cookies.remove(TokenKey)
  return Cookies.remove(TokenKey)
}
