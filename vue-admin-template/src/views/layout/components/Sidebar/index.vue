<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">

    <logo :is-collapse="isCollapse"/>

    <el-menu
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      :background-color="variables.menuBg"
      :text-color="variables.menuText"
      :active-text-color="variables.menuActiveText"
      mode="vertical"
    >

      <sidebar-item v-for="route in menu_routers" :key="route.path" :item="route" :base-path="route.path"/>

    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import variables from '@/styles/variables.scss'
import SidebarItem from './SidebarItem'
import Logo from './Logo'

export default {
  components: {
    SidebarItem,
    Logo
  },
  computed: {
    ...mapGetters([
      'menu_routers',
      'sidebar'
    ]),
    routes() {
      return this.$router.options.routes
    },
    variables() {
      return variables
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
