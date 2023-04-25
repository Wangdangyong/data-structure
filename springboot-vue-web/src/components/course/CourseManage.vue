<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="courseName" placeholder="请输入课程名" style="width: 200px;"
                @keyup.enter.native="loadPost" >
      </el-input>



      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">搜索</el-button>
      <el-button type="success " @click="resetForm">重置</el-button>
      <el-button type="primary " @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >

      <el-table-column prop="courseName" label="课程名" width="100">
      </el-table-column>
      <el-table-column prop="courseTeacherId" label="任课教师" width="100" :formatter="formatTeacher">
      </el-table-column>
      <el-table-column prop="courseNo" label="课程编号" width="100">
      </el-table-column>

      <el-table-column prop="courseGrade" label="年级" width="80">
      </el-table-column>
      <el-table-column prop="room" label="教室" width="120">
      </el-table-column>
      <el-table-column prop="courseCredit" label="学分" width="60">
      </el-table-column>

      <el-table-column prop="type" label="是否必修" width="120">
      </el-table-column>
      <el-table-column prop="courseNumber" label="已选人数" width="120">
      </el-table-column>
      <el-table-column prop="courseMaxNumber" label="最大人数" width="120">
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
              @confirm="del(scope.row.courseId)"
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

        <el-form-item label="课程名"   label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.courseName"></el-input>
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
        <el-form-item label="授课教师" >
          <el-col :span="20">
            <el-select v-model="form.courseTeacherId" placeholder="请选择教师" style="margin-left: 5px">
              <el-option
                  v-for="item in teacherData" v-show="item.teacherDepId === form.majorDepId"
                  :key="item.teacherId"
                  :label="item.teacherName"
                  :value="item.teacherId">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="课程编号"   >
          <el-col :span="20">
            <el-input v-model="form.courseNo"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="年级" >
          <el-col :span="20">
            <el-select v-model="form.courseGrade" placeholder="请选择年级" style="margin-left: 5px">
              <el-option
                  v-for="item in ['大一','大二','大三','大四']"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="教室"   >
          <el-col :span="20">
            <el-input v-model="form.room"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="学分"   label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.courseCredit"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="是否必修"   >
          <el-col :span="20">
            <el-radio v-model="form.type" label="是">是</el-radio>
            <el-radio v-model="form.type" label="否">否</el-radio>
          </el-col>
        </el-form-item>

        <el-form-item prop="weekDay" label="日期" width="180" v-if="form.type=='否'">



            <el-select v-model="form.weekDay" placeholder="请选择日期" style="margin-left: 5px" >
              <el-option
                  v-for="item in ['周一','周二','周三','周四','周五','周六','周日']"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item prop="section" label="第几节" width="180" v-if="form.type=='否'">


            <el-select v-model="form.section" placeholder="请选择节数" style="margin-left: 5px">
              <el-option
                  v-for="item in ['一','二','三','四','五']"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>

        </el-form-item>





        <el-form-item label="已选人数"  label-width="180px">
          <el-col :span="20">
            <el-input v-model="form.courseNumber"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="最大人数"  label-width="180px">
          <el-col :span="20">
            <el-input v-model="form.courseMaxNumber"></el-input>
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
  name: "CourseManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      teacherData:[],
      depData:[],
      tableData: [],
      pageSize: 7,
      pageNum: 1,
      total: '',
      courseName: '',

      centerDialogVisible: false,
      form: {
        courseId:'',

        courseName: '',
        courseTeacherId: '',
        courseNo: '',
        courseDepId: '',
        courseGrade: '',
        room: '',
        courseCredit: '',
        weekDay: '',
        section: '',
        type: '',
        courseNumber: '',
        courseMaxNumber: ''



      },



    }
  },
  methods: {
    formatDep(row){
      let temp=this.depData.find(item=>{
        return item.depId==row.courseDepId

      })
      return temp && temp.depName

    },
    formatTeacher(row){
      let temp=this.teacherData.find(item=>{
        return item.teacherId==row.courseTeacherId

      })
      return temp && temp.teacherName

    },
    loadGet() {
      this.$axios.get(this.$httpUrl + '/course/list').then(res => res.data).then(res => {
        console.log(res)
        // this.tableData=res

      })

    },
    del(courseId){
      this.$axios.get(this.$httpUrl + '/course/del?courseId='+courseId,
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
      this.form.courseId=row.courseId
      this.form.courseName=row.courseName
      this.form.courseTeacherId=row.courseTeacherId
      this.form.courseNo=row.courseNo
      this.form.courseDepId=row.courseDepId
      this.form.courseGrade=row.courseGrade
      this.form.room=row.room
      this.form.courseCredit=row.courseCredit
      this.form.weekDay=row.weekDay
      this.form.type=row.type
      this.form.courseNumber=row.courseNumber
      this.form.courseMaxNumber=row.courseMaxNumber


      this.centerDialogVisible = true

    },
    save() {
      if (this.form.courseId){
        this.$axios.post(this.$httpUrl + '/course/update', this.form
        ).then(res => res.data).then(res => {
          if (this.form.courseId) {
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
        this.$axios.post(this.$httpUrl + '/course/save', this.form
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
    loadTeacher(){
      this.$axios.get(this.$httpUrl + '/teacher/list').then(res => res.data).then(res => {
        console.log(res)
        if (res.code ==200) {
          this.teacherData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/course/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          courseName: this.courseName



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
      this.courseName = ''
      this.courseTeacherId=''

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
    this.loadTeacher()
    this.loadDep()
    this.loadPost();
  }
}

</script>

<style scoped>

</style>