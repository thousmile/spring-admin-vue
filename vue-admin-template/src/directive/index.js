import Vue from 'vue'
import store from '@/store'

/** 注册全局指令**/

/**
 * 判断权限的指令
 * 用法: <el-button v-has="'perm:new'" class="btns">添加</el-button>
 */
Vue.directive('has', {
  inserted: function(el, binding, vnode) {
    if (!Vue.prototype.$_has(binding.value)) {
      el.parentNode.removeChild(el)
    }
  }
})

// 权限检查方法
Vue.prototype.$_has = function(value) {
  // 获取用户按钮权限
  let isExist = false
  // 当前用户所有的按钮列表
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

/**
 * 修改标题的指令
 * 用法: <div v-title>文章管理</div>
 */
Vue.directive('title', {
  inserted: function(el, binding) {
    document.title = el.innerText
    el.remove()
  }
})
