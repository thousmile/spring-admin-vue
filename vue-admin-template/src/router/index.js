import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in subMenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/
export const constantRouterMap = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index')
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

  {
    path: '*',
    redirect: '/error/404',
    hidden: true
  }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

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
