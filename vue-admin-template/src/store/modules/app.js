const sidebarKey = 'sidebarStatus'
const sizeKey = 'size'

const state = {
  sidebar: {
    opened: localStorage.getItem(sidebarKey) ? !!+localStorage.getItem(sidebarKey) : true,
    withoutAnimation: false
  },
  device: 'desktop',
  size: localStorage.getItem(sizeKey) || 'default'
}

const mutations = {
  TOGGLE_SIDEBAR: state => {
    state.sidebar.opened = !state.sidebar.opened
    state.sidebar.withoutAnimation = false
    if (state.sidebar.opened) {
      localStorage.setItem(sidebarKey, '1')
    } else {
      localStorage.setItem(sidebarKey, '0')
    }
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    localStorage.setItem(sidebarKey, '0')
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
  },
  TOGGLE_DEVICE: (state, device) => {
    state.device = device
  },
  SET_SIZE: (state, size) => {
    state.size = size
    localStorage.setItem(sizeKey, size)
  }
}

const actions = {
  toggleSideBar({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  toggleDevice({ commit }, device) {
    commit('TOGGLE_DEVICE', device)
  },
  setSize({ commit }, size) {
    commit('SET_SIZE', size)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
