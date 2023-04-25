<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="userName" placeholder="请输入名字" style="width: 200px;"
                @keyup.enter.native="loadPost">
      </el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success " @click="resetForm">重置</el-button>
      <el-button type="success " @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >
      <el-table-column prop="userId" label="ID" width="160">
      </el-table-column>
      <el-table-column prop="userAccount" label="账号" width="180">
      </el-table-column>
      <el-table-column prop="userName" label="姓名" width="180">
      </el-table-column>
      <el-table-column prop="userPassword" label="密码" width="180">
      </el-table-column>


      <el-table-column prop="roleId" label="角色" width="180"  >
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId === 0 ? 'default' : (scope.row.roleId === 1 ? 'primary' : 'success')"
              disable-transitions>{{scope.row.roleId === 0 ? '管理员' :
              (scope.row.roleId === 1 ? '教师' : '学生')}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="operate" label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.userId)"
              style="margin-left: 15px"
          >
            <el-button slot="reference" type="danger" >删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[7, 14, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :model="form" label-width="180px">
        <el-form-item label="账号"   >
          <el-col :span="20">
            <el-input v-model="form.userAccount"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="姓名"   label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.userName"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码"  label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.userPassword"></el-input>
          </el-col>
        </el-form-item>


        <el-form-item label="角色"  label-width="180px">
          <el-col :span="20">
            <el-input v-model="form.roleId"></el-input>
          </el-col>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "AdminManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [],
      pageSize: 7,
      pageNum: 1,
      total: '',
      userName: '',
      centerDialogVisible: false,
      form: {
        userId:'',
        userAccount: '',
        userName: '',
        userPassword: '',
        roleId: '0'

      },



    }
  },
  methods: {
    loadGet() {
      this.$axios.get(this.$httpUrl + '/user/list').then(res => res.data).then(res => {
        console.log(res)
        // this.tableData=res

      })

    },
    del(userId){
      this.$axios.get(this.$httpUrl + '/user/del?userId='+userId,
      ).then(res => res.data).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message({
                message: '操作成功',
                type: 'success'
              }
          )
          this.loadPost()
        } else {
          this.$message.error('操作失败');

        }
      })


    },
    mod(row){
      console.log(row)
      this.form.userId=row.userId
      this.form.userAccount=row.userAccount
      this.form.userName=row.userName
      this.form.userPassword=row.userPassword

      this.form.roleId=row.roleId
      this.centerDialogVisible = true

    },
    save() {
      if (this.form.userId){
        this.$axios.post(this.$httpUrl + '/user/update', this.form
        ).then(res => res.data).then(res => {
          if (this.form.userId) {
            this.$message({
                  message: '修改成功',
                  type: 'success'
                }
            )
            this.centerDialogVisible = false
            this.loadPost()
          } else {
            this.$message.error('修改失败');

          }
        })
      }
      else {
        this.$axios.post(this.$httpUrl + '/user/save', this.form
        ).then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
            this.$message({
                  message: '操作成功',
                  type: 'success'
                }
            )
            this.centerDialogVisible = false
            this.loadPost()
          } else {
            this.$message.error('操作失败');

          }
        })
      }

    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/user/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          userName: this.userName,

          roleId: '0'

        }

      }).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          this.tableData = res.data
          this.total = res.total
        } else {
          alert("获取数据失败")
        }
      })

    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1
      this.pageSize = val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val
      this.loadPost()
    },
    resetForm() {
      this.userName = ''

    },
    add() {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm();
      })
    }
  },
  beforeMount() {
    // this.loadGet();
    this.loadPost();
  }
}

</script>

<style scoped>

</style>