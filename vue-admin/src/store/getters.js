const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  nickname: state => state.user.nickname,
  roles: state => state.user.roles,
  user: state => state.user.user,
  uid: state => state.user.uid,
  // 从后台获取的用户菜单，没有解析过的
  menus: state => state.user.menus,
  // 从后台获取的用户 按钮权限
  buttons: state => state.user.buttons,

  // 通用信息
  website: state => state.common.website,

  // permission.js
  // 菜单路由，显示在页面上的
  menu_routers: state => state.permission.routers,
  // 当前用户的 动态菜单路由
  dynamicRouters: state => state.permission.dynamicRouters
}
export default getters
