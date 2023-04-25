<template>
 <el-menu
     background-color="#545c64"
     text-color="#fff"
     active-text-color="#ffd04b"
     default-active="/hPage"
     :collapse="isCollapse"
     :collapse-transition="false"
     style="height: 100vh"
 router>
   <el-menu-item index="/hPage">
     <i class="el-icon-s-home"></i>
     <span slot="title">首页</span>
   </el-menu-item>

   <el-menu-item :index="'/'+item.menuClick" v-for="(item,i) in menu"
   :key="i">
     <i :class="item.menuIcon"></i>
     <span slot="title">{{item.menuName}}</span>
   </el-menu-item>



 </el-menu>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Aside",
  data() {
    return{
      // isCollapse:false
      // menu:[
      //   {
      //     menuClick:'admin',
      //     menuNane:'管理员管理',
      //     menuIcon:'el-icon-s-custom'
      //   },
      //   {
      //     menuClick: 'user',
      //     menuNane: '用户管理',
      //     menuIcon: 'el-icon-user-solid'
      //   }
      //
      //
      // ]
    }
  },
  computed:{
    "menu":{
      get(){
        return this.$store.state.menu
      }
    }
  }
}
</script>

<style scoped>

</style>