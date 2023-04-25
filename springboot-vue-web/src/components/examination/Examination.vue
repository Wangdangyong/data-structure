<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="studentId" placeholder="请输入学号" style="width: 200px;"
                @keyup.enter.native="loadPost"  v-if="user.roleId===0">
      </el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost" v-if="user.roleId===0">搜索</el-button>
      <el-button type="success " @click="resetForm" v-if="user.roleId===0">重置</el-button>
      <el-button type="primary " @click="add" v-if="user.roleId===0">新增</el-button>
    </div>
    <el-table :data="tableData.filter(v =>v.studentId===(user.roleId !==2 ? v.studentId : tableData.find(v =>v.studentId===user.userStuId).studentId))"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >
      <el-table-column prop="studentId" label="学生" width="160" v-if="user.roleId!==1">
        <template v-slot="scope">
            <span v-if="scope.row.studentId">
              {{studentData.find(v =>v.studentNumber===scope.row.studentId).studentName}}
            </span>
        </template>
      </el-table-column>
      <el-table-column prop="courseId" label="课程" width="200">
        <template v-slot="scope">
            <span v-if="scope.row.courseId">
              {{courseData.find(v =>v.courseId===scope.row.courseId).courseName}}
            </span>
        </template>
      </el-table-column>
      <el-table-column prop="teacherId" label="教师" width="200">
        <template v-slot="scope">
            <span v-if="scope.row.teacherId">
              {{teacherData.find(v =>v.teacherId===scope.row.teacherId).teacherName}}
            </span>
        </template>

      </el-table-column>
      <el-table-column prop="room" label="教室" width="200">
      </el-table-column>
      <el-table-column prop="time" label="考试时间" width="200">
      </el-table-column>



      <el-table-column prop="operate" label="操作" width="200"v-if="user.roleId===0">
        <template slot-scope="scope">
          <el-button type="success" @click="mod(scope.row)" v-if="user.roleId===0">编辑</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
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
        <el-form-item label="学生"   >
          <el-col :span="20">
            <el-select v-model="form.studentId" placeholder="请选择学生" style="margin-left: 5px">
              <el-option
                  v-for="item in studentData"
                  :key="item.studentNumber"
                  :label="item.studentName"
                  :value="item.studentNumber">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="课程"   label-width="180px" >
          <el-col :span="20">
            <el-select v-model="form.courseId" placeholder="请选择课程" style="margin-left: 5px">
              <el-option
                  v-for="item in courseData"
                  :key="item.courseId"
                  :label="item.courseName"
                  :value="item.courseId">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="监考教师"  label-width="180px" >
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


        <el-form-item label="教室"  label-width="180px">
          <el-col :span="20">
            <el-input v-model="form.room"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="日期"  label-width="180px">
          <el-col :span="20">
            <el-date-picker
                v-model="form.time"
                type="date"
                placeholder="选择日期">
            </el-date-picker>
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
  name: "Examination",
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      value1: '',
      value2: '',
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [],
      studentData:[],
      courseData:[],
      teacherData:[],
      pageSize: 7,
      pageNum: 1,
      total: '',
      studentId: '',
      courseId:'',
      centerDialogVisible: false,
      form: {
        id:'',
        studentId:'',
        courseId:'',
        teacherId:'',
        room:'',
        time:'',


      },



    }
  },
  methods: {
    loadGet() {
      this.$axios.get(this.$httpUrl + '/examination/list').then(res => res.data).then(res => {


      })

    },
    del(id){
      this.$axios.get(this.$httpUrl + '/examination/del?id='+id,
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
      this.form.studentId=row.studentId
      this.form.courseId=row.courseId
      this.form.teacherId=row.teacherId
      this.form.room=row.room
      this.form.time=row.time
      this.centerDialogVisible = true

    },
    save() {
      if (this.form.id){
        this.$axios.post(this.$httpUrl + '/examination/update', this.form
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
        this.$axios.post(this.$httpUrl + '/examination/save', this.form
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
    loadStudent(){
      this.$axios.get(this.$httpUrl + '/student/list').then(res => res.data).then(res => {
        if (res.code ==200) {
          this.studentData = res.data
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
    loadCourse(){
      this.$axios.get(this.$httpUrl + '/course/list').then(res => res.data).then(res => {
        if (res.code ==200) {
          this.courseData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/examination/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          studentId: this.studentId+'',
          courseId: this.courseId+''



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
          this.studentId = '',
          this.courseId=''
          this.form.studentId=''
          this.form.courseId=''
          this.form.teacherId=''
          this.form.room=''
          this.form.time=''


    },
    add() {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm();
      })
    }
  },
  beforeMount() {
    this.loadCourse()
    this.loadStudent()
    this.loadTeacher()
    this.loadPost();
  }
}

</script>

<style scoped>

</style>