<!-- eslint-disable vue/multi-word-component-names -->
<template>
<!--页面布局-->
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside :width="aside_width" style="background-color: rgb(238, 241, 246)">
      <Aside isCollapse="isCollapse"></Aside>
    </el-aside>

    <el-container style="height: 100%">
      <el-header style="text-align: right; font-size: 12px;height: 100%;border-bottom: #B3C0D1 1px solid">
        <Header @doCollapse="doCollapse" :icon="icon"></Header>
      </el-header>

      <el-main style="height: 100%">
<!--        <Main></Main>-->
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "./Aside";
import Header from "./Header";
// import Main from "./Main";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Index",
  data() {
    return{
      isCollapse: false,
      aside_width: "200px",
      icon: 'el-icon-s-fold'
    }

  },
  props:{
    isCollapsed:Boolean

  },
  components: {
    // Main,
    Header,
    Aside
  },
  methods: {
    doCollapse(){
      console.log(111)
      this.isCollapse=!this.isCollapse
      if (this.isCollapse){//展开
        this.aside_width='200px'
      }
      else {//关闭
        this.aside_width='50px'

      }
    }

  }
}
</script>

<style scoped>
.el-header {
  /*background-color: #B3C0D1;*/
  color: #333;
  line-height: 60px;
}
.el-main{
  margin: 5px;
}

.el-aside {
  color: #333;
}
</style>