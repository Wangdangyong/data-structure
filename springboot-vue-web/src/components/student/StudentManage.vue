<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="studentName" placeholder="请输入名字" style="width: 200px;"
                @keyup.enter.native="loadPost" v-if="user.roleId===0">
      </el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost" v-if="user.roleId===0">搜索</el-button>
      <el-button type="success " @click="resetForm" v-if="user.roleId===0">重置</el-button>
      <el-button type="primary " @click="add"  v-if="user.roleId===0">新增</el-button>
    </div>
    <el-table :data="tableData.filter(v => v.studentNumber == (user.roleId != 2 ? v.studentNumber : user.userStuId))"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >

      <el-table-column prop="studentNumber" label="学号" width="180">
      </el-table-column>
      <el-table-column prop="studentName" label="学生姓名" width="180">


      </el-table-column>
      <el-table-column prop="classId" label="班级" width="180" >
        <template v-slot="scope">
            <span v-if="scope.row.classId">
              {{classData.find(v =>v.classId==scope.row.classId).className}}
            </span>
        </template>
      </el-table-column>
      <el-table-column prop="studentMajorId" label="专业" width="200" >
        <template v-slot="scope">
            <span v-if="scope.row.studentMajorId">
              {{majorData.find(v =>v.majorId===scope.row.studentMajorId).majorName}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="studentGrade" label="年级" width="180" >
      </el-table-column>




      <el-table-column prop="operate" label="操作" width="230" v-if="user.roleId===0">
        <template slot-scope="scope">
          <el-button type="success" @click="mod(scope.row)" v-if="user.roleId===0">编辑</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.studentId)"
              style="margin-left: 15px"
          >
            <el-button slot="reference" type="danger"  v-if="user.roleId===0">删除</el-button>
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

        <el-form-item label="学号"   label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.studentNumber"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="学生姓名"  label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.studentName"></el-input>
          </el-col>
        </el-form-item>


        <el-form-item label="班级"  label-width="180px">

          <el-col :span="20">
            <el-select v-model="form.classId" placeholder="请选择班级" style="margin-left: 5px">
              <el-option
                  v-for="item in classData"
                  :key="item.classId"
                  :label="item.className"
                  :value="item.classId">
              </el-option>
            </el-select>
          </el-col>


        </el-form-item>
        <el-form-item label="专业" >
          <el-col :span="20">
            <el-select v-model="form.studentMajorId" placeholder="请选择专业" style="margin-left: 5px">
              <el-option
                  v-for="item in majorData"
                  :key="item.majorId"
                  :label="item.majorName"
                  :value="item.majorId">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="年级"  label-width="180px">
          <el-col :span="20">
            <el-select v-model="form.studentGrade" placeholder="请选择年级" style="margin-left: 5px">
              <el-option
                  v-for="item in ['大一','大二','大三','大四']"
                  :key="item"
                  :label="item"
                  :value="item">
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
  name: "StudentManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      classData: '',
      majorData:[],
      tableData: [],
      userData:[],
      pageSize: 7,
      pageNum: 1,
      total: '',
      studentMajorId:'',
      studentName: '',
      studentNumber: '',
      centerDialogVisible: false,
      form: {
        studentId:'',
        studentNumber:'',
        studentName: '',
        classId: '',
        studentMajorId:'',
        studentGrade:''



      },



    }
  },
  methods: {

    loadGet() {
      this.$axios.get(this.$httpUrl + '/student/list').then(res => res.data).then(res => {
        console.log(res)
        // this.tableData=res

      })

    },
    del(studentId){
      this.$axios.get(this.$httpUrl + '/student/del?studentId='+studentId,
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
      this.form.studentId=row.studentId
      this.form.studentNumber=row.studentNumber
      this.form.studentName=row.studentName
      this.form.classId=row.classId
      this.form.studentMajorId=row.studentMajorId
      this.form.studentGrade=row.studentGrade


      this.centerDialogVisible = true

    },
    save() {
      if (this.form.studentId){
        this.$axios.post(this.$httpUrl + '/student/update', this.form
        ).then(res => res.data).then(res => {
          if (this.form.studentId) {
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
        this.$axios.post(this.$httpUrl + '/student/save', this.form
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
    loadUser(){
      this.$axios.get(this.$httpUrl + '/user/list').then(res => res.data).then(res => {

        if (res.code ==200) {
          this.userData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadMajor(){
      this.$axios.get(this.$httpUrl + '/major/list').then(res => res.data).then(res => {

        if (res.code ==200) {
          this.majorData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadClass(){
      this.$axios.get(this.$httpUrl + '/class/list').then(res => res.data).then(res => {
        console.log(res)
        if (res.code ==200) {
          this.classData = res.data

        } else {
          // alert("获取数据失败")
        }
      })

    },


    loadPost() {
      this.$axios.post(this.$httpUrl + '/student/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          studentNumber: this.studentNumber+'',

          studentName: this.studentName,
          studentMajorId: this.studentMajorId+''


        }

      }).then(res => res.data).then(res => {
        console.log(res)
        if (res.code == 200) {
          sessionStorage.setItem('CurStudent',JSON.stringify(res.data))
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
      this.studentName = ''
      this.studentMajorId=''
      this.form.studentId=''
      this.form.studentNumber=''
      this.form.studentName=''
      this.form.classId=''
      this.form.studentMajorId=''
      this.form.studentGrade=''


    },
    add() {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm();
      })
    }
  },
  beforeMount() {
    this.loadUser();
    this.loadClass();
    this.loadMajor();
    this.loadPost()
  }
}

</script>

<style scoped>

</style>