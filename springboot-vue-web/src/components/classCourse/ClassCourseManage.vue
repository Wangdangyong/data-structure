<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="classId" placeholder="请输入班级" style="width: 200px;"
                @keyup.enter.native="loadPost" v-if="user.roleId===0">
      </el-input>
      <el-input v-model="courseId" placeholder="请输入课程" style="width: 200px;"
                @keyup.enter.native="loadPost" v-if="user.roleId===0">
        <template v-slot="scope">
            <span v-if="scope.row.courseId">
              {{courseData.find(v =>v.courseId==scope.row.courseId).courseName}}

            </span>

        </template>
      </el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost" v-if="user.roleId===0">查询</el-button>
      <el-button type="success " @click="resetForm" v-if="user.roleId===0">重置</el-button>
      <el-button type="success " @click="add" v-if="user.roleId===0">新增</el-button>
    </div>
    <el-table :data="tableData.filter(v => v.classId === (user.roleId !== 2 ? v.classId : studentData.find(v => v.studentNumber === user.userStuId).classId))"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >
      <el-table-column prop="classId" label="班级" width="160">
        <template v-slot="scope">
            <span v-if="scope.row.classId">
              {{classData.find(v =>v.classId==scope.row.classId).className}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="courseId" label="课程" width="180">

        <template v-slot="scope">
            <span v-if="scope.row.courseId">
              {{courseData.find(v =>v.courseId==scope.row.courseId).courseName}}

            </span>

        </template>
      </el-table-column>
      <el-table-column prop="weekDay" label="日期" width="180" >
      </el-table-column>
      <el-table-column prop="section" label="第几节" width="180" >
      </el-table-column>




      <el-table-column prop="operate" label="操作" width="200" v-if="user.roleId===0">
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
        <el-form-item label="班级"   >
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
        <el-form-item label="日期"  label-width="180px" >
          <el-col :span="20">
            <el-select v-model="form.weekDay" placeholder="请选择日期" style="margin-left: 5px">
              <el-option
                  v-for="item in ['周一','周二','周三','周四','周五']"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-col>

        </el-form-item>


        <el-form-item label="第几节"  label-width="180px">
          <el-col :span="20">
            <el-select v-model="form.section" placeholder="请选择节数" style="margin-left: 5px">
              <el-option
                  v-for="item in ['一','二','三','四','五']"
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
  name: "ClassCourseManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      classData:[],
      courseData:[],
      studentData:[],
      tableData: [],
      pageSize: 7,
      pageNum: 1,
      total: '',
      classId: '',
      courseId: '',
      centerDialogVisible: false,
      form: {
        id:'',
        classId: '',
        courseId: '',
        weekDay: '',
        section: ''


      },



    }
  },
  methods: {

    loadGet() {
      this.$axios.get(this.$httpUrl + '/class-course/list').then(res => res.data).then(res => {
        console.log(res)
        // this.tableData=res

      })

    },
    del(id){
      this.$axios.get(this.$httpUrl + '/class-course/del?id='+id,
      ).then(res => res.data).then(res => {
        console.log(res)
        if (res.code ==200) {
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
      this.form.classId=row.classId
      this.form.courseId=row.courseId
      this.form.weekDay=row.weekDay

      this.form.section=row.section
      this.centerDialogVisible = true

    },
    save() {
      if (this.form.id){
        this.$axios.post(this.$httpUrl + '/class-course/update', this.form
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
        this.$axios.post(this.$httpUrl + '/class-course/save', this.form
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
          console.log(res)
          this.studentData = res.data

        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/class-course/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          classId: this.classId,
          courseId: this.courseId



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
      this.classId = ''
      this.courseId=''

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
    this.loadCourse()
    // this.loadGet();
    this.loadClass()
    this.loadPost();
  }
}

</script>

<style scoped>

</style>