import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/views/layout/Layout'

export const constantRouterMap = [
  {
    path: '/login',
    name: 'Login',
    component: () =>
      import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () =>
        import('@/views/dashboard/index')
    }, {
      path: 'userinfo',
      name: 'UserInfo',
      component: () =>
        import('@/views/dashboard/userinfo')
    }]
  },

  {
    path: '/error',
    component: Layout,
    redirect: '/error/404',
    hidden: true,
    children: [{
      path: '404',
      component: () =>
        import('@/views/error/404/index')
    }, {
      path: '401',
      component: () =>
        import('@/views/error/401/index')
    }]
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
    component: Layout,
    name: 'pre',
    meta: {
      resources: 'pre',
      title: '权限管理'
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/pre/perm/index'),
        name: 'perm',
        meta: {
          resources: 'perm'
        }
      },
      {
        path: 'user',
        component: () => import('@/views/pre/user/index'),
        name: 'user',
        meta: {
          resources: 'user'
        }
      },
      {
        path: 'role',
        component: () => import('@/views/pre/role/index'),
        name: 'role',
        meta: {
          resources: 'role'
        }
      },
      {
        path: 'dept',
        component: () => import('@/views/pre/dept/index'),
        name: 'dept',
        meta: {
          resources: 'dept'
        }
      }
    ]
  },

  {
    path: '/sys',
    component: Layout,
    name: 'sys',
    meta: {
      resources: 'sys',
      title: '系统设置'
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/sys/backstage/index'),
        name: 'backstage',
        meta: {
          resources: 'backstage'
        }
      },
      {
        path: 'wechat',
        component: () => import('@/views/sys/wechat/index'),
        name: 'wechat',
        meta: {
          resources: 'wechat'
        }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    name: 'Link',
    meta: {
      resources: 'control',
      title: '系统监控',
      icon: 'link'
    },
    children: [{
      path: 'https://www.baidu.com/',
      meta: {
        resources: 'logs',
        title: '系统日志',
        icon: 'link'
      }
    },
    {
      path: 'https://v.qq.com/',
      meta: {
        resources: 'database',
        title: '数据库监控',
        icon: 'link'
      }
    }
    ]
  }

]
