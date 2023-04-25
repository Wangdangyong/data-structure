<template>
<div style="">

  <h1 style="text-align: center" >{{'欢迎你！'+user.userName}}</h1>

  <el-descriptions  :column="2" :size="60" border>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-s-custom"></i>
        账号
      </template>
      {{user.userAccount}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        密码
      </template>
     {{user.userPassword}}
    </el-descriptions-item>

    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        角色
      </template>
      <el-tag
          type="success"
          disable-transitions>{{user.roleId === 0 ? '管理员' :
          (user.roleId === 1 ? '教师' : '学生')}}</el-tag>

    </el-descriptions-item>
  </el-descriptions>

</div>

</template>

<script>
import DateUtils from "./DateUtils"
export default {
  name: "Home",
  components:{
    DateUtils
  },
  data(){
    return {

      user: JSON.parse(sessionStorage.getItem('CurUser')),
      userName:'',
      userPassword:'',
     userAccount:''
    }
  },
  computed:{

  },
  methods:{

  }
}
</script>

<style scoped>
.h1{
  margin-top: 10px;
}

</style>