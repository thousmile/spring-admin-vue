import { login, logout, getUserInfo } from '@/api/sysUser'
import { getToken, setToken, removeToken } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    header: 'Authorization',
    nickname: '',
    username: '',
    avatar: '',
    userinfo: {},
    roles: [],
    menus: [], // 菜单权限
    buttons: [] // 安装权限
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER_INFO: (state, user) => {
      if (user === null) {
        state.avatar = ''
        state.username = ''
        state.nickname = ''
        state.userinfo = {}
      } else {
        state.avatar = user.avatar
        state.username = user.username
        state.nickname = user.nickname
        state.userinfo = user
      }
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_MENUS: (state, menus) => {
      state.menus = menus
    },
    SET_BUTTONS: (state, buttons) => {
      state.buttons = buttons
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo)
          .then(response => {
            const tokenValue = response.prefix + response.value
            setToken(tokenValue)
            commit('SET_TOKEN', tokenValue)
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getUserInfo()
          .then(response => {
            if (response.roles !== null && response.roles.length > 0) {
              // 验证返回的roles是否是一个非空数组
              commit('SET_ROLES', response.roles)
            } else {
              reject('getInfo: 当前用户没有角色 !')
            }
            commit('SET_USER_INFO', response)
            commit('SET_MENUS', response.menus)
            commit('SET_BUTTONS', response.buttons)
            resolve(response)
          })
          .catch(error => {
            reject(error)
          })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout()
          .then(() => {
            commit('SET_USER_INFO', null)
            commit('SET_TOKEN', '')
            commit('SET_ROLES', [])
            commit('SET_MENUS', [])
            commit('SET_BUTTONS', [])
            removeToken()
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    },

    // 前端登录，不用请求后台，直接删除所有 cookie
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_USER_INFO', null)
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        commit('SET_MENUS', [])
        commit('SET_BUTTONS', [])
        removeToken()
        resolve()
      })
    }

  }
}

export default user
