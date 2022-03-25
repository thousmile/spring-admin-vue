import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import { getRelativePath } from '@/utils/index' // 验权

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      const relativePath = getRelativePath()
      if (store.getters.menus.length === 0) {
        try {
          // 拉取用户信息。和 菜单
          const { menus } = await store.dispatch('GetInfo')
          // 获取简单用户
          await store.dispatch('ListSimpleUsers')
          // 根据 后端API接口，的菜单信息。构建 vue-router 的路由信息
          const dr = await store.dispatch('toVueRoutes', menus)
          // dr 就是动态路由 获取已经解析好的路由列表，动态添加到router中
          dr.forEach(item => router.addRoute(item))
          // hack方法 确保addRoutes已完成
          if (relativePath.indexOf('login') !== -1) {
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
          } else {
            next({ path: relativePath, replace: true })
          }
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('FedLogOut')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      } else {
        next()
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
