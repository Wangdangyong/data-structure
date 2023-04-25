<template>
<div style="display: flex;ine-height: 61px ">
  <div style="cursor: pointer">
  <i :class="icon" style="font-size: 20px;text-align: center" @click="collapse"></i>
  </div>
  <div style="flex: 1;text-align: center;font-size: 30px">
    <span style="text-align: center">贵州大学教务管理管理系统</span>
  </div>

  <el-dropdown >
    <span class="el-dropdown-link">
						{{user.userName}}<i class="el-icon-arrow-down" style="margin-left: 5px"></i>
						</span>

    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
      <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Header",
  data(){
    return{
      // 获取用户名
      user: JSON.parse(sessionStorage.getItem('CurUser'))

    }
  },
  props: {
    icon:String

  },
  methods: {
    toUser(){

      this.$router.push("/home")

    },
    logout(){
      console.log("退出")
      this.$confirm('您确定退出当前账号吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center:true
      }).then(() => {   // 确定操作
        this.$message({
          type:'success',
          message: '退出登录成功'

        })
        this.$router.push('/')
        sessionStorage.clear()
      }).catch(() => {  // 取消操作
        this.$message({
          type: 'info',
          message: '已取消退出登录'
        });
      });

    },
    collapse(){
      this.$emit('doCollapse')

    }
  },
  created() {
    this.$router.push("/hPage")
  }
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
element.style{
  cursor: pointer;
}

</style>