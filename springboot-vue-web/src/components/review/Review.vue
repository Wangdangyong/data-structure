<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">



      <el-button type="primary " @click="add" v-if="user.roleId==2">评价</el-button>
    </div>
    <el-table :data="tableData.filter(v=>v.teacherId==(user.roleId!==1 ? v.teacherId :tableData.find(v =>v.teacherId==user.userTeaId).teacherId))"
              :header-cell-style="{textAlign: 'center',background: '#f2f5fc',color:'#555555'}"
    >

      <el-table-column prop="studentId" label="学生" width="160" style="color: crimson" >
        <template v-slot="scope">
            <span v-if="scope.row.studentId">
              {{studentData.find(v =>v.studentId===scope.row.studentId).studentName}}
            </span>
        </template>
      </el-table-column>

      <el-table-column prop="teacherId" label="教师" width="180" style="color: crimson" >
        <template v-slot="scope">
            <span v-if="scope.row.teacherId">
              {{teacherData.find(v =>v.teacherId==scope.row.teacherId).teacherName}}
            </span>
        </template>
      </el-table-column>
      <el-table-column prop="review" label="评价" height="200" width="800" style="color: crimson" >
      </el-table-column>

      <el-table-column prop="operate" label="操作" width="400">
        <template slot-scope="scope">
          <el-button type="success" @click="mod(scope.row)" v-if="user.roleId==2">修改评价</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
              style="margin-left: 15px"
          >
            <el-button slot="reference" type="danger"  v-if="user.roleId==0">删除评价</el-button>




          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>


    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="60%"

        center>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px"  label-height="100px">

        <el-form-item label="学生" prop="studentId">
          <el-col :span="20">
            <el-select v-model="form.studentId" placeholder="请选择" style="margin-left: 5px">
              <el-option
                  v-for="item in studentData.filter(v=>v.studentNumber==(user.roleId !==2 ?v.studentNumber : studentData.find(v=>v.studentNumber==user.userStuId).studentNumber))"
                  :key="item.studentId"
                  :label="item.studentName"
                  :value="item.studentId">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="教师" prop="review">
          <el-col :span="20">
            <el-select v-model="form.teacherId" placeholder="请选择教师" style="margin-left: 5px">
              <el-option
                  v-for="item in teacherData"
                  :key="item.teacherId"
                  :label="item.teacherName"
                  :value="item.teacherId">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="评价" prop="review">
          <el-input type="textarea" v-model="form.review"></el-input>
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
  name: "Review",
  data() {
    return {
      studentData:[],
      teacherData:[],
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [],
      centerDialogVisible: false,
      form: {
        id:'',
        review:'',
        teacherId:'',
        studentId:''

      },
      rules:{
        review: [

          { required: true, message: '请填写评价', trigger: 'blur' }


        ]

      },


    }
  },
  methods: {

    loadGet() {
      this.$axios.get(this.$httpUrl + '/review/list').then(res => res.data).then(res => {
        console.log(111)
        this.tableData=res.data

      })

    },
    loadTeacher() {
      this.$axios.get(this.$httpUrl + '/teacher/list').then(res => res.data).then(res => {
        console.log(res)
        this.teacherData=res.data

      })

    },
    loadStudent() {
      this.$axios.get(this.$httpUrl + '/student/list').then(res => res.data).then(res => {
        console.log(res)
        this.studentData=res.data

      })

    },
    del(id){
      this.$axios.get(this.$httpUrl + '/review/del?id='+id,
      ).then(res => res.data).then(res => {

        if (res.code === 200) {
          this.$message({
                message: '操作成功',
                type: 'success'
              }
          )
          this.loadGet()
        } else {
          this.$message.error('操作失败');

        }
      })


    },
    mod(row){
      console.log(row)
      this.form.id=row.id
      this.form.review=row.review
      this.form.teacherId=row.teacherId
      this.form.studentId=row.studentId

      this.centerDialogVisible = true

    },
    save() {
      if (this.form.id){
        this.$axios.post(this.$httpUrl + '/review/update', this.form
        ).then(res => res.data).then(res => {
          if (this.form.id) {
            this.$message({
                  message: '修改成功',
                  type: 'success'
                }
            )
            this.centerDialogVisible = false
            this.loadGet()
          } else {
            this.$message.error('修改失败');

          }
        })
      }
      else {
        this.$axios.post(this.$httpUrl + '/review/save', this.form
        ).then(res => res.data).then(res => {

          if (res.code == 200) {
            this.$message({
                  message: '操作成功',
                  type: 'success'
                }
            )
            this.centerDialogVisible = false
            this.loadGet()
          } else {
            this.$message.error('操作失败');

          }
        })
      }

    },


    resetForm() {
      this.form.review='',
          this.form.teacherId=''

    },
    add() {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm();
      })
    }
  },
  beforeMount() {
    this.loadStudent();
    this.loadGet();
    this.loadTeacher()

  }
}

</script>

<style scoped >

</style>