import Cookies from 'js-cookie'

// token 值 在 Cookie 中key名称
const TokenKey = 'TokenValue'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
