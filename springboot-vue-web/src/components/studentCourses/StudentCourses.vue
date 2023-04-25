<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="studentNumber" placeholder="请输入学号" style="width: 200px;"
                @keyup.enter.native="loadPost"   v-if="user.roleId===0">
      </el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost"   v-if="user.roleId===0">搜索</el-button>
      <el-button type="success " @click="resetForm"   v-if="user.roleId===0">重置</el-button>
      <el-button type="primary " @click="add">选课</el-button>
    </div>
    <el-table :data="tableData.filter(v => v.studentNumber == (user.roleId != 2 ? v.studentNumber : user.userStuId))"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >

      <el-table-column prop="studentNumber" label="学生" width="150">

        <template v-slot="scope">
            <span v-if="scope.row.studentNumber">
              {{studentData.find(v =>v.studentNumber==scope.row.studentNumber).studentName}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="classId" label="班级" width="180">

        <template v-slot="scope">
            <span v-if="scope.row.classId">
              {{classData.find(v =>v.classId===scope.row.classId).className}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="courseId" label="课程" width="180">

        <template v-slot="scope">
            <span v-if="scope.row.courseId">
              {{courseData.find(v =>v.courseId===scope.row.courseId).courseName}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="weekDay" label="日期" width="120">

        <template v-slot="scope">
            <span v-if="scope.row.courseId">
              {{courseData.find(v =>v.courseId===scope.row.courseId).weekDay}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="section" label="节数" width="120">

        <template v-slot="scope">
            <span v-if="scope.row.courseId">
              {{courseData.find(v =>v.courseId===scope.row.courseId).section}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="courseNumber" label="已选人数" width="120">

        <template v-slot="scope">
            <span v-if="scope.row.courseId">
              {{courseData.find(v =>v.courseId===scope.row.courseId).courseNumber}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="courseMaxNumber" label="最大容量" width="120">

        <template v-slot="scope">
            <span v-if="scope.row.courseId">
              {{courseData.find(v =>v.courseId===scope.row.courseId).courseMaxNumber}}

            </span>

        </template>
      </el-table-column>






      <el-table-column prop="operate" label="操作" width="310">
        <template slot-scope="scope">
          <el-button type="primary " @click="addTable(scope)" v-if="user.roleId===0">加入课表</el-button>
          <el-button type="success" @click="mod(scope.row)"   v-if="user.roleId===0">修改</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
              style="margin-left: 15px"
          >
            <el-button slot="reference" type="danger" >退课</el-button>

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

        <el-form-item label="班级"   label-width="180px" >
          <el-col :span="20">
            <el-select v-model="form.classId" placeholder="请选择班级" style="margin-left: 5px">
              <el-option
                  v-for="item in classData.filter(v => v.classId == (user.roleId != 2 ? v.classId : studentData.find(v=>v.studentNumber==user.userStuId).classId))"
                  :key="item.classId"
                  :label="item.className"
                  :value="item.classId">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="姓名"   label-width="180px" >
          <el-col :span="20">
          <el-select v-model="form.studentNumber" placeholder="请选择学生" style="margin-left: 5px">
            <el-option
                v-for="item in studentData.filter(v => v.studentNumber == (user.roleId != 2 ? v.studentNumber : user.userStuId))"  v-show="item.classId === form.classId"
                :key="item.studentNumber"
                :label="item.studentName"
                :value="item.studentNumber">
            </el-option>
          </el-select>
          </el-col>
        </el-form-item>

        <el-form-item label="课程"  label-width="180px" >
         <el-col :span="20">
          <el-select v-model="form.courseId" placeholder="请选择课程" style="margin-left: 5px" >
            <el-option
                v-for="item in courseData.filter(v=>v.type=='否')"
                :key="item.courseId"
                :label="item.courseName"
                :value="item.courseId">
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
  name: "StudentCourses",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [],
      classData:[],
      course1Data:[],
    courseData:[],
      studentData:[],
      pageSize: 7,
      pageNum: 1,
      total: '',
      studentNumber: '',
      centerDialogVisible: false,
      form: {
       id:'',
        studentNumber:"",
        classId:'',
        courseId:'',
        weekDay:'',
        section:'',
        courseNumber:'',
        courseMaxNumber:''


      },





    }
  },
  methods: {
    form1(scope){


    },

    del(id){
      this.$axios.get(this.$httpUrl + '/student-courses/del?id='+id,
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
      this.form.id=row.id
      this.form.studentNumber=row.studentNumber
      this.form.courseId=row.courseId
      this.form.classId=row.classId
      this.centerDialogVisible = true

    },

    async addTable(scope){
      // Promise
      // 同步任务的队列
      // 异步任务的队列

      let weekDay = await this.courseData.find(v =>v.courseId===scope.row.courseId).weekDay;
      let section = await this.courseData.find(v =>v.courseId===scope.row.courseId).section;
      this.$axios.post(this.$httpUrl + '/class-course/addClassCourse', {
        studentNumber:scope.row.studentNumber,
        classId:scope.row.classId,
        courseId: scope.row.courseId,
        weekDay: weekDay,
        section: section

          }
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

    },
    save() {
      if (this.form.id){
        this.$axios.post(this.$httpUrl + '/student-courses/update', this.form
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
        this.$axios.post(this.$httpUrl + '/student-courses/save', this.form
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
            this.loadcCourse()
          } else {
            this.$message.error('操作失败');

          }
        })
      }

    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/student-courses/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
         studentNumber: this.studentNumber+''



        }

      }).then(res => res.data).then(res => {

        if (res.code == 200) {
          this.tableData = res.data
          this.total = res.total
        } else {
          alert("获取数据失败")
        }
      })

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
    loadcCourse(){
      this.$axios.get(this.$httpUrl + '/class-course/list').then(res => res.data).then(res => {
        console.log(res)

        if (res.code ==200) {
          this.course1Data = res.data
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
        this.studentData=res.data

        if (res.code ==200) {
          this.studentData=res.data

        } else {
          // alert("获取数据失败")
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
      this.studentNumber = ''
      this.form.id='',
          this.form.studentNumber='',
          this.form.courseId='',
          this.form.classId=''


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
    this.loadcCourse()
    this.loadClass()
    this.loadCourse()
    this.loadStudent()
    this.loadPost();
  }
}

</script>

<style scoped>

</style>