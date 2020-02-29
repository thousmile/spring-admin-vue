const getters = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device,
  settings: state => state.settings,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,

  errorLogs: state => state.errorLog.logs,

  token: state => state.user.token,
  nickname: state => state.user.nickname,
  username: state => state.user.username,
  avatar: state => state.user.avatar,
  userinfo: state => state.user.userinfo,

  roles: state => state.user.roles,
  // 从后台获取的用户菜单，没有解析过的
  menus: state => state.user.menus,
  // 从后台获取的用户 按钮权限
  buttons: state => state.user.buttons,

  // permission.js
  // 菜单路由，显示在页面上的
  menuRouters: state => state.permission.routers,
  // 当前用户的 动态菜单路由
  dynamicRouters: state => state.permission.dynamicRouters

}
export default getters
