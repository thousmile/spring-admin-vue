// store/permission.js
import { asyncRouterMap, constantRoutes } from '@/router'
/**
 *
 * @param  {Array} userRouter 后台返回的用户权限json
 * @param  {Array} allRouter  前端配置好的所有动态路由的集合
 * @return {Array} realRoutes 过滤后的路由
 */

export function recursionRouter(userRouter = [], allRouter = []) {
  var realRoutes = []
  allRouter.forEach((v, i) => {
    userRouter.forEach((item, index) => {
      if (item.resources === v.meta.resources) {
        if (item.children && item.children.length > 0) {
          v.children = recursionRouter(item.children, v.children)
        }
        v.meta.title = item.title
        v.meta.icon = item.icon
        realRoutes.push(v)
      }
    })
  })
  return realRoutes
}

/**
*
* @param {Array} routes 用户过滤后的路由
*
* 递归为所有有子路由的路由设置第一个children.path为默认路由
*/
export function setDefaultRoute(routes) {
  routes.forEach((v, i) => {
    if (v.children && v.children.length > 0) {
      v.redirect = { name: v.children[0].name }
      setDefaultRoute(v.children)
    }
  })
}

const permission = {
  state: {
    routers: constantRoutes, // 这是默认权限列表 比如404 500等路由
    dynamicRouters: [] // 这是通过后台获取的权利列表
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.dynamicRouters = routers
      state.routers = constantRoutes.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        commit('SET_ROUTERS', recursionRouter(data, asyncRouterMap))
        resolve()
      })
    }
  }
}

export default permission
