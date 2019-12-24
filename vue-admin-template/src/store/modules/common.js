const common = {
  state: {
    website: {
      upload: {
        image: '/upload/image',
        avatar: '/upload/avatar'
      },
      title: '哈哈后台管理系统',
      logo: '/static/logo.png',
      dept: {
        alert: '请勿乱删除部门, 确实需要删除部门的时候, (1).请确定这个部门下面没有子部门, (2).请确定这个部门下面没有用户. 只有满足以上两种情况才可以删除部门成功!'
      },
      role: {
        alert: '请勿乱删除角色, 确实需要删除角色的时候, (1).请确定这个角色下面没有用户, (2).请确定这个角色没有分配权限. 只有满足以上两种情况才可以删除角色成功!'
      }
    }
  },
  actions: {},
  mutations: {

  }
}
export default common
