<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="majorName" placeholder="请输入专业名" style="width: 200px;"
                @keyup.enter.native="loadPost" v-if="user.roleId===0">
      </el-input>
      <el-input v-model="majorTeacher" placeholder="请输入专业指导教师" style="width: 200px;"
                @keyup.enter.native="loadPost" v-if="user.roleId===0">
      </el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost" v-if="user.roleId===0">搜索</el-button>
      <el-button type="success " @click="resetForm" v-if="user.roleId===0">重置</el-button>
      <el-button type="primary " @click="add"  v-if="user.roleId===0">新增</el-button>
    </div>
    <el-table :data="tableData.filter(v => v.majorNo == (user.roleId != 2 ? v.majorNo : studentData.find(v => v.studentNumber == user.userStuId).studentMajorId))"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >
      <el-table-column prop="majorNo" label="专业编号" width="160">
      </el-table-column>
      <el-table-column prop="majorName" label="专业名" width="300">
      </el-table-column>
      <el-table-column prop="majorTeacher" label="专业指导教师" width="180">
      </el-table-column>
      <el-table-column prop="majorDepId" label="所属院系" width="300" :formatter="formatDep">
      </el-table-column>




      <el-table-column prop="operate" label="操作" width="200" v-if="user.roleId===0">
        <template slot-scope="scope">
          <el-button type="success" @click="mod(scope.row)"  v-if="user.roleId===0">编辑</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.majorId)"
              style="margin-left: 15px"
          >
            <el-button slot="reference" type="danger"   v-if="user.roleId===0">删除</el-button>
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
        <el-form-item label="专业编号"   >
          <el-col :span="20">
            <el-input v-model="form.majorNo"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="专业名"  label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.majorName"></el-input>
          </el-col>
        </el-form-item>


        <el-form-item label="专业指导教师"  label-width="180px">
          <el-col :span="20">
            <el-input v-model="form.majorTeacher"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="院系" >
          <el-col :span="20">
            <el-select v-model="form.majorDepId" placeholder="请选择院系" style="margin-left: 5px">
              <el-option
                  v-for="item in depData"
                  :key="item.depId"
                  :label="item.depName"
                  :value="item.depId">
              </el-option>
            </el-select>
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
  name: "MajorManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      depData: '',
      majorData: [],
      tableData: [],
      studentData:[],
      pageSize: 7,
      pageNum: 1,
      total: '',
      majorName: '',
      majorTeacher: '',
      centerDialogVisible: false,
      form: {
        majorId:'',
        majorNo:'',
        majorName: '',
        majorTeacher: '',
        majorDepId: ''



      },



    }
  },
  methods: {
    formatDep(row){
      let temp=this.depData.find(item=>{
        return item.depId==row.majorDepId

      })
      return temp && temp.depName

    },

    loadGet() {
      this.$axios.get(this.$httpUrl + '/major/list').then(res => res.data).then(res => {
        console.log(res)
        // this.tableData=res

      })

    },
    del(majorId){
      this.$axios.get(this.$httpUrl + '/major/del?majorId='+majorId,
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
      this.form.majorId=row.majorId
      this.form.majorNo=row.majorNo
      this.form.majorName=row.majorName
      this.form.majorTeacher=row.majorTeacher
      this.form.majorDepId=row.majorDepId



      this.centerDialogVisible = true

    },
    save() {
      if (this.form.majorId){
        this.$axios.post(this.$httpUrl + '/major/update', this.form
        ).then(res => res.data).then(res => {
          if (this.form.majorId) {
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
        this.$axios.post(this.$httpUrl + '/major/save', this.form
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
    loadDep(){
      this.$axios.get(this.$httpUrl + '/department/list').then(res => res.data).then(res => {
        console.log(res)
        if (res.code ==200) {
          this.depData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadStudent(){
      this.$axios.get(this.$httpUrl + '/student/list').then(res => res.data).then(res => {
        console.log(res)
        if (res.code ==200) {
          this.studentData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/major/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          majorName: this.majorName,
          majorTeacher: this.majorTeacher


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
      this.form.majorId=''
      this.form.majorNo=''
      this.form.majorName=''
      this.form.majorTeacher=''
      this.form.majorDepId=''
      this.majorName = ''
      this.majorTeacher=''
      this.form.majorDepId=''

    },
    add() {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm();
      })
    }
  },
  beforeMount() {
    this.loadStudent()
    this.loadDep()
    this.loadPost();
  },
  mounted() {
    this.loadStudent()
    this.loadDep()
    this.loadPost();
  }
}

</script>

<style scoped>

</style>