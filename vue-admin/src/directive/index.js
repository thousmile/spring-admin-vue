import Vue from 'vue'
import store from '@/store'

/** 权限指令**/

Vue.directive('has', {
  bind: function(el, binding) {
    if (!Vue.prototype.$_has(binding.value)) {
      el.parentNode.removeChild(el)
    }
  }
})

// 权限检查方法
Vue.prototype.$_has = function(value) {
  // 获取用户按钮权限
  let isExist = false
  const dynamicButtons = store.getters.buttons
  if (dynamicButtons === undefined || dynamicButtons === null || dynamicButtons.length < 1) {
    return isExist
  }
  dynamicButtons.forEach(button => {
    if (button.resources === value) {
      isExist = true
      return isExist
    }
  })
  return isExist
}
