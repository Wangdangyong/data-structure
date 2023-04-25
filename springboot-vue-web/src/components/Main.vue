<template>
<!-- 表头设置和表的边框-->
  <div>
  <div style="margin-left: 5px;margin-bottom: 20px">
<!--    回车设置-->
    <el-input v-model="username" placeholder="请输入名字" style="width: 200px;"
    @keyup.enter.native="loadPost">
  </el-input>
    <el-select v-model="sex" filterable placeholder="请选择性别">
      <el-option
          v-for="item in sexs"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
  <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
  <el-button type="success " @click="resetForm">重置</el-button>
    <el-button type="success " @click="add">新增</el-button>
</div>
  <el-table :data="tableData"
  :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
  >
    <el-table-column prop="id" label="ID" width="60">
    </el-table-column>
    <el-table-column prop="account" label="账号" width="180">
    </el-table-column>
    <el-table-column prop="username" label="姓名" width="80">
      </el-table-column>
    <el-table-column prop="password" label="密码" width="180">
    </el-table-column>
    <el-table-column prop="age" label="年龄" width="60">
    </el-table-column>
    <el-table-column prop="sex" label="性别" width="100">
      <template slot-scope="scope">
        <el-tag
            :type="scope.row.sex === 0 ? 'primary' : 'success'"
            disable-transitions>{{scope.row.sex === 0 ? '女' : '男'}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="roleId" label="角色" width="180">
      <template slot-scope="scope">
        <el-tag
            :type="scope.row.roleId === 0 ? 'default' : (scope.row.roleId === 1 ? 'primary' : 'success')"
            disable-transitions>{{scope.row.roleId === 0 ? '超级管理员' :
                                 (scope.row.roleId === 1 ? '管理员' : '普通用户')}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="phone" label="电话" width="180">
    </el-table-column>
    <el-table-column prop="isvalid" label="是否有效" width="80">
    </el-table-column>
    <el-table-column prop="operate" label="操作" width="180">
      <template slot-scope="scope">
      <el-button type="success" @click="mod(scope.row)">编辑</el-button>
        <el-popconfirm
            confirm-button-text='好的'
            cancel-button-text='不用了'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除吗？"
            @confirm="del(scope.row.id)"
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
        title="新增账号"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="账号" >
          <el-col :span="20">
            <el-input v-model="form.account"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="姓名"  >
          <el-col :span="20">
            <el-input v-model="form.username"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" >
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄"  >
          <el-col :span="20">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.resource">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色">
          <el-col :span="20">
            <el-input v-model="form.roleId"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="电话"  >
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="是否有效">
          <el-col :span="20">
            <el-input v-model="form.isvalid"></el-input>
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
  name: "Main",
  data() {
    return {
      tableData: [],
      pageSize: 7,
      pageNum: 1,
      total: '',
      username: '',
      sex: '',
      sexs: [
        {
          value: '0',
          label: '女'
        }, {
          value: '1',
          label: '男'
        }

      ],
      centerDialogVisible: false,
      form: {
        id:'',
        account: '',
        username: '',
        password: '',
        age: '',
        sex: '0',
        phone: '',
        roleId: '',
        isvalid: ''
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
    del(id){
      this.$axios.get(this.$httpUrl + '/user/del?id='+id,
      ).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
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
      this.form.id=row.id
      this.form.account=row.account
      this.form.username=row.username
      this.form.password=''
      this.form.age=row.age+''
      this.form.sex=row.sex+''
      this.form.phone=row.phone
      this.form.roleId=row.roleId
      this.form.isvalid=row.isvalid
      this.centerDialogVisible = true

    },
    save() {
      if (this.form.id){
        this.$axios.post(this.$httpUrl + '/user/update', this.form
        ).then(res => res.data).then(res => {
          if (this.form.id) {
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
      this.$axios.post(this.$httpUrl + '/user/listPageT', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          username: this.username,
          sex: this.sex

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
      this.username = '',
      this.sex = ''

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