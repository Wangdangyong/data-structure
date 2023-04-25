<template>
  <div class="loginBody" >
    <div class="loginDiv">
      <div class="login-content">
        <h1 class="login-title">贵州大学教务管理系统</h1>
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="账号" prop="userAccount">
            <el-input style="width: 200px" type="text" v-model="loginForm.userAccount"
                      autocomplete="off" size="small"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="userPassword">
            <el-input style="width: 200px" type="password" v-model="loginForm.userPassword"
                      show-password autocomplete="off" size="small"  @keyup.enter.native></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="userRole">
            <el-select v-model="loginForm.userRole">
              <el-option label="管理员" value="admin"></el-option>
              <el-option label="教师" value="teacher"></el-option>
              <el-option label="学生" value="student"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="confirm" :disabled="confirm_disabled" >登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>

  </div>
</template>
<script>
export default {
  name: 'Login',
  data() {
    return {
      captchaImg:'',
      confirm_disabled:false,
      loginForm: {
        userAccount: '',
        userPassword: '',
        userRole: 'admin',
      },
      rules: {
        userAccount: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10个字符', trigger: 'blur' }
        ],
        userPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        userRole: [
          { required: true, message: '请选择角色', trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    confirm(){
      this.confirm_disabled=true;
      this.$refs.loginForm.validate((valid) =>{
        if(valid){
          this.$axios.post(this.$httpUrl + '/user/login',this.loginForm
          ).then(res => res.data).then(res => {

            if (res.code ==200) {
              //将某个对象转换成 JSON 字符串形式

              sessionStorage.setItem('CurUser',JSON.stringify(res.data.user))

              //  在vuex 中调用方法,  使用commit 方法调用一个函数， 第一个参数是方法名称,  第二个参数是:
              // 需要携带的参数
              this.$store.commit("setMenu",res.data.menu)
              this.$router.replace('/index')

            } else {
              this.confirm_disabled=false
              alert("校验失败，请重新匹配")
              return false

            }
          });
        }else {
          this.confirm_disabled=false;
          console.log("校验失败");
          return false;

        }
      });
    },

  },

}
</script>
<style>
.loginBody{
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url("https://p6.itc.cn/q_70/images03/20210814/add9149e125b4e81b59cd7372b96dc71.jpeg");
  background-size: 100% 100%;
}
.loginDiv{
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -250px;
  margin-left: -250px;
  width: 500px;
  height: 350px;
  background: #fff;
  border-radius: 5%;
}
.login-title{
  margin: 20px 0;
  text-align: center;
}
.login-content{
  width: 400px;
  height: 250px;
  position: absolute;
  top: 25px;
  left: 25px;

}
</style>