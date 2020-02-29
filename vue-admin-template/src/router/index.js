import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://github.com/thousmile',
        meta: {
          title: 'Github',
          icon: 'link'
        }
      }
    ]
  },

  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard', noCache: true, affix: true }
    }]
  },

  {
    path: '/error',
    name: 'Error',
    component: Layout,
    redirect: '/error/404',
    hidden: true,
    children: [
      {
        path: '404',
        component: () => import('@/views/error/404/index')
      },
      {
        path: '401',
        component: () => import('@/views/error/401/index')
      }
    ]
  },

  // 404 page must be placed at the end !!!
  {
    path: '*',
    redirect: '/error/404',
    hidden: true
  }

]

// 异步挂载的路由
// 动态需要根据权限加载的路由表
export const asyncRouterMap = [
  {
    path: '/pre',
    name: 'Pre',
    component: Layout,
    redirect: '/pre/index',
    meta: {
      resources: 'pre',
      title: '权限管理'
    },
    children: [
      {
        path: 'index',
        name: 'PrePerm',
        component: () => import('@/views/pre/perm/index'),
        meta: {
          resources: 'pre_perm',
          title: '权限管理'
        }
      },
      {
        path: 'user',
        name: 'PreUser',
        component: () => import('@/views/pre/user/index'),
        meta: {
          resources: 'pre_user',
          title: '用户管理'
        }
      },
      {
        path: 'role',
        name: 'PreRole',
        component: () => import('@/views/pre/role/index'),
        meta: {
          resources: 'pre_role',
          title: '角色管理'
        }
      },
      {
        path: 'dept',
        component: () => import('@/views/pre/dept/index'),
        name: 'PreDept',
        meta: {
          resources: 'pre_dept',
          title: '部门管理'
        }
      }
    ]
  },

  {
    path: '/sys',
    name: 'Sys',
    component: Layout,
    redirect: '/sys/index',
    meta: {
      resources: 'sys',
      title: '系统设置'
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/sys/chinaArea/index'),
        name: 'SysChinaArea',
        meta: {
          resources: 'sys_china_area',
          title: '行政地区'
        }
      },
      {
        path: 'dictionary',
        component: () => import('@/views/sys/dictionary/index'),
        name: 'SysDictionary',
        meta: {
          resources: 'sys_dictionary',
          title: '字典集合'
        }
      },
      {
        path: 'wechat',
        name: 'SysWechat',
        component: () => import('@/views/sys/wechat/index'),
        meta: {
          resources: 'sys_wechat',
          title: '微信设置'
        }
      },
      {
        path: 'logs',
        name: 'SysLogs',
        component: () => import('@/views/sys/logs/index'),
        meta: {
          resources: 'sys_logs',
          title: '日志数据'
        }
      },
      {
        path: 'swagger',
        name: 'SysSwagger',
        component: () => import('@/views/sys/swagger/index'),
        meta: {
          resources: 'sys_swagger2',
          title: 'API文档'
        }
      }
    ]
  }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
