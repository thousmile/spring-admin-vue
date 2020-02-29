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
        // 拉取用户信息(请确保在 GetInfo 方法中 已经获取到菜单列表)
        store.dispatch('GetInfo').then(res => {
          // 动态设置路由（把上一步获取到的用户传递给 GenerateRoutes方法 解析）
          store.dispatch('GenerateRoutes', store.getters.menus).then(r => {
            // 获取已经解析好的路由列表，动态添加到router中
            router.addRoutes(store.getters.dynamicRouters)
            // hack方法 确保addRoutes已完成
            if (relativePath.indexOf('login') !== -1) {
              next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
            } else {
              next({ path: relativePath, replace: true })
            }
          })
        }).catch((err) => {
          store.dispatch('LogOut').then(() => {
            Message.error(err || '您的登录已经过期，请重新登录!')
            next({ path: '/' })
          })
        })
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
