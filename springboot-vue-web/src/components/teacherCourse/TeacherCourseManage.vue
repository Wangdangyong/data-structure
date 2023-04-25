<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
<!--      //此处需改-->
      <el-input v-model="studentId" placeholder="请输入学生学号" style="width: 200px;"
                @keyup.enter.native="loadPost">

      </el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">搜索</el-button>
      <el-button type="success " @click="resetForm">重置</el-button>
      <el-button type="primary " @click="add" v-if="user.roleId===0">新增</el-button>
    </div>
    <el-table :data="tableData.filter(v => v.teacherId == (user.userTeaId == null ? v.teacherId : user.userTeaId))"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"

    >

      <el-table-column prop="studentId" label="学生" width="280">


        <template v-slot="scope">
            <span v-if="scope.row.studentId">
              {{studentData.find(v =>v.studentNumber==scope.row.studentId).studentName}}

            </span>

        </template>
      </el-table-column>

      <el-table-column prop="teacherId" label="课程" width="280">

        <template v-slot="scope">
            <span v-if="scope.row.teacherId">
              {{courseData.find(v =>v.courseTeacherId==scope.row.teacherId).courseName}}

            </span>
        </template>
      </el-table-column>
      <el-table-column prop="classId" label="班级" width="300">

        <template v-slot="scope">
            <span v-if="scope.row.classId">
              {{classData.find(v =>v.classId==scope.row.classId).className}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="teacherId" label="教师" width="180">

        <template v-slot="scope">
            <span v-if="scope.row.teacherId">
              {{teacherData.find(v =>v.teacherId==scope.row.teacherId).teacherName}}
            </span>
        </template>
      </el-table-column>

      <el-table-column prop="usualScore" label="平时成绩" width="180" v-if="user.roleId===1">
      </el-table-column>
      <el-table-column prop="finalScore" label="期末成绩" width="180" v-if="user.roleId===1">
      </el-table-column>


      <el-table-column prop="allScore" label="总成绩" width="180"  v-if="user.roleId===1">
      </el-table-column>
      <el-table-column prop="flag" label="补考" width="180" v-if="user.roleId!==0">
      </el-table-column>




      <el-table-column prop="operate" label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="success" @click="mod(scope.row)" v-if="user.roleId===1"> 打分</el-button>

          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
              style="margin-left: 15px"
          >
            <el-button slot="reference" type="danger" v-if="user.roleId===0">删除</el-button>
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


        <el-form-item label="课程"   label-width="180px"  v-if="user.roleId===0">
          <el-col :span="20">
            <el-select v-model="form.teacherId" placeholder="请选择课程" style="margin-left: 5px">
              <el-option
                  v-for="item in courseData"
                  :key="item.courseTeacherId"
                  :label="item.courseName"
                  :value="item.courseTeacherId">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="班级"   label-width="180px"  v-if="user.roleId===0">
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
        <el-form-item label="学生"   label-width="180px"   v-if="user.roleId===0">
          <el-col :span="20">
            <el-select v-model="form.studentId" placeholder="请选择学生" style="margin-left: 5px">
              <el-option
                  v-for="item in studentData" v-show="item.classId === form.classId"
                  :key="item.studentNumber"
                  :label="item.studentName"
                  :value="item.studentNumber">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="平时成绩"   label-width="180px"  v-if="user.roleId===1">
          <el-col :span="20">
            <el-input v-model.number="form.usualScore"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="期末成绩"  label-width="180px"   v-if="user.roleId===1">
          <el-col :span="20">
            <el-input v-model.number="form.finalScore"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="平时成绩占比"  label-width="180px"   v-if="user.roleId===1">
          <el-col :span="20">
            <el-input v-model.flout="form.usualScoreCom"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="期末成绩占比"  label-width="180px"   v-if="user.roleId===1">
          <el-col :span="20">
            <el-input v-model.flout="form.finalScoreCom"></el-input>
          </el-col>
        </el-form-item>



        <el-form-item label="总成绩"  label-width="180px"  v-if="user.roleId===1" >
          <template v-slot="scope">
          <el-col :span="20">
            <el-input v-model.number="allScore"> </el-input>
          </el-col>
          </template>


        </el-form-item>
        <el-form-item label="绩点"  label-width="180px"  v-if="user.roleId===1" >
          <template v-slot="scope">
            <el-col :span="20">
              <el-input v-model.float="scorePoint"> </el-input>
            </el-col>
          </template>


        </el-form-item>

        <el-form-item label="是否补考"  v-if="form.allScore" >
          <el-col :span="20">
            <el-radio v-model="form.flag" label="是"  v-if="form.allScore<60">是</el-radio>
            <el-radio v-model="form.flag" label="否"  v-if="form.allScore>=60">否</el-radio>
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
  name: "TeacherCourseManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      classData: [],
      teaClassData:[],
      courseData:[],
      tableData: [],
      studentData:[],
      teacherData:[],
      pageSize: 7,
      pageNum: 1,
      total: '',
     studentId:'',
      centerDialogVisible: false,
      form: {
        id:'',
        teacherId: '',
        courseId:'',
        classId: '',
        studentId:'',
        usualScore:0,
        finalScore:0,
        allScore:0,
        scoreId:'',
        flag:'',
        scopePoint:0,
        usualScoreCom:0,
         finalScoreCom:0

      },
    }
  },
  computed:{

    scorePoint(){

      if(this.form.allScore>=95){
         this.form.scorePoint=5
        return this.form.scorePoint
      }else if(this.form.allScore<94&&this.form.allScore>=85){
         this.form.scorePoint=4.5
        return this.form.scorePoint

      }else if(this.form.allScore<84&&this.form.allScore>=75){
         this.form.scorePoint=4
        return this.form.scorePoint

      }else if(this.form.allScore<74&&this.form.allScore>=65){
        this.form.scorePoint=3.5
        return this.form.scorePoint

      }else if(this.form.allScore<64&&this.form.allScore>=60){
         this.form.scorePoint=3
        return this.form.scorePoint

      }else
         this.form.scorePoint=0
      return this.form.scorePoint





    },

    allScore(){
      this.form.allScore = Math.round(this.form.usualScoreCom*(this.form.usualScore) +  this.form.finalScoreCom*(this.form.finalScore))
      return this.form.allScore
    }
  },
  methods: {

    loadGet() {
      this.$axios.get(this.$httpUrl + '/doScore/list').then(res => res.data).then(res => {

      })

    },
    del(id){
      this.$axios.get(this.$httpUrl + '/doScore/del?id='+id,
      ).then(res => res.data).then(res => {

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
      this.form.id=row.id
      this.form.teacherId=row.teacherId
      this.form.courseId=row.courseId
      this.form.classId=row.classId
      this.form.studentId=row.studentId
      if (row.usualScore)
         this.form.usualScore=row.usualScore
      if(row.finalScore)
          this.form.finalScore=row.finalScore
      if(row.allScore)
        this.form.allScore=row.allScore
      if(row.scorePoint)
        this.form.scopePoint=row.scorePoint
      this.form.scoreId=row.scoreId
      this.form.flag=row.flag
      this.centerDialogVisible = true

    },
    save() {
      console.log(this.form)
      if (this.form.id){
        this.$axios.post(this.$httpUrl + '/doScore/update', this.form
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
        this.$axios.post(this.$httpUrl + '/doScore/save', this.form
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
    loadCourse(){
      this.$axios.get(this.$httpUrl + '/course/list').then(res => res.data).then(res => {
        if (res.code ==200) {
          this.courseData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadTeacher(){
      this.$axios.get(this.$httpUrl + '/teacher/list').then(res => res.data).then(res => {
        if (res.code ==200) {
          this.teacherData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadTeaClass(){
      this.$axios.get(this.$httpUrl + '/tea-class/list').then(res => res.data).then(res => {
        if (res.code ==200) {
          this.teaClassData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadClass(){
      this.$axios.get(this.$httpUrl + '/class/list').then(res => res.data).then(res => {
        if (res.code ==200) {
          this.classData = res.data

        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadStudent(){
      this.$axios.get(this.$httpUrl + '/student/list').then(res => res.data).then(res => {
        if (res.code ==200) {
          this.studentData = res.data

        } else {
          // alert("获取数据失败")
        }
      })

    },


    loadPost() {
      this.$axios.post(this.$httpUrl + '/doScore/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          studentId:this.studentId+''



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
       this.studentId=''
      this.form.teacherId=''
      this.form.courseId=''
      this.form.classId=''
      this.form.studentId=''
      this.form.usualScore=''
        this.form.finalScore=''
        this.form.allScore=''
      this.form.scoreId=''
      this.form.flag=''

    },
    add() {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm();
      })
    }
  },
  beforeMount() {
    this.loadTeaClass()
    this.loadTeacher();
    this.loadStudent();
    this.loadCourse();
    this.loadClass();
    this.loadPost()
  }
}

</script>

<style scoped>

</style>