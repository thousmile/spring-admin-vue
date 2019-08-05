import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/views/layout/Layout'

export const constantRouterMap = [{
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
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRouterMap
})

// 异步挂载的路由
// 动态需要根据权限加载的路由表
export const asyncRouterMap = [{
  path: '/pre',
  component: Layout,
  name: 'pre',
  meta: {
    resources: 'pre',
    title: '权限管理'
  },
  children: [{
    path: 'index',
    component: () => import('@/views/pre/perm/index'),
    name: 'pre_perm',
    meta: {
      resources: 'pre_perm'
    }
  },
  {
    path: 'user',
    component: () => import('@/views/pre/user/index'),
    name: 'pre_user',
    meta: {
      resources: 'pre_user'
    }
  },
  {
    path: 'role',
    component: () => import('@/views/pre/role/index'),
    name: 'pre_role',
    meta: {
      resources: 'pre_role'
    }
  },
  {
    path: 'dept',
    component: () => import('@/views/pre/dept/index'),
    name: 'pre_dept',
    meta: {
      resources: 'pre_dept'
    }
  }
  ]
},

{
  path: '/article',
  component: Layout,
  name: 'article',
  redirect: '/article/index',
  meta: {
    resources: 'article',
    title: '文章管理'
  },
  children: [{
    path: 'index',
    component: () => import('@/views/article/list/index'),
    name: 'article_list',
    meta: {
      resources: 'article_list'
    }
  },
  {
    path: 'article_write',
    component: () => import('@/views/article/write/index'),
    name: 'article_write',
    meta: {
      resources: 'article_write'
    }
  },
  {
    path: 'category',
    component: () => import('@/views/article/category/index'),
    name: 'category',
    meta: {
      resources: 'article_category'
    }
  },
  {
    path: 'article_my',
    component: () => import('@/views/article/my/index'),
    name: 'article_my',
    meta: {
      resources: 'article_my'
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
  children: [{
    path: 'index',
    component: () => import('@/views/sys/backstage/index'),
    name: 'sys_backstage',
    meta: {
      resources: 'sys_backstage'
    }
  },
  {
    path: 'wechat',
    component: () => import('@/views/sys/wechat/index'),
    name: 'sys_wechat',
    meta: {
      resources: 'sys_wechat'
    }
  },
  {
    path: 'database',
    component: () => import('@/views/sys/database/index'),
    name: 'sys_database',
    meta: {
      resources: 'sys_database'
    }
  },
  {
    path: 'logs',
    component: () => import('@/views/sys/logs/index'),
    name: 'sys_logs',
    meta: {
      resources: 'sys_logs'
    }
  }
  ]
}

]
