<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="className" placeholder="请输入班级名" style="width: 200px;"
                @keyup.enter.native="loadPost">
      </el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">搜索</el-button>
      <el-button type="success " @click="resetForm">重置</el-button>
      <el-button type="primary " @click="add"  v-if="user.roleId===0">新增</el-button>
    </div>
    <el-table :data="tableData.filter(v => v.classId == (user.roleId != 2 ? v.classId : studentData.find(v => v.studentNumber == user.userStuId).classId))"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >

      <el-table-column prop="classNo" label="班级编号" width="300">
      </el-table-column>
      <el-table-column prop="className" label="班级名" width="300" >
<!--        <template v-slot="tableData" v-if="user.roleId==2">-->
<!--            <span v-if="tableData.classId">-->
<!--              {{tableData.find(v=>v.classId==(studentData.find(v =>v.studentNumber==user.userStuId).classId)).tableData.className}}-->

<!--            </span>-->

<!--        </template>-->


      </el-table-column>

      <el-table-column prop="classNumber" label="班级人数" width="300"  >
      </el-table-column>





      <el-table-column prop="operate" label="操作" width="300" v-if="user.roleId===0">
        <template slot-scope="scope">
          <el-button type="success" @click="handleCourse(scope.row)" >设置课程</el-button>
          <el-button type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.classId)"
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
        title="选课"
        :visible.sync="centerDialogVisible1"
        width="60%"
        center>
      <el-button type="success " @click="addCourse">新增</el-button>

      <el-table :data="classCourseData"
                :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
      >
        <el-table-column prop="classId" label="班级" width="160" >
          <template v-slot="scope">

            <el-select v-model="scope.row.classId" placeholder="请选择班级" style="margin-left: 5px">
              <el-option
                  v-for="item in tableData"
                  :key="item.classId"
                  :label="item.className"
                  :value="item.classId">
              </el-option>
            </el-select>
          </template>


        </el-table-column>

        <el-table-column prop="courseId" label="课程" width="180" >
          <template v-slot="scope">

          <el-select v-model="scope.row.courseId" placeholder="请选择课程" style="margin-left: 5px" >
            <el-option
                v-for="item in courseData.filter(v=>v.type=='是')"
                :key="item.courseId"
                :label="item.courseName"
                :value="item.courseId">
            </el-option>
          </el-select>
          </template>


        </el-table-column>
        <el-table-column prop="weekDay" label="日期" width="180">

          <template v-slot="scope">

            <el-select v-model="scope.row.weekDay" placeholder="请选择日期" style="margin-left: 5px">
              <el-option
                  v-for="item in ['周一','周二','周三','周四','周五','周六','周日']"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="section" label="第几节" width="180">
          <template v-slot="scope">

            <el-select v-model="scope.row.section" placeholder="请选择节数" style="margin-left: 5px">
              <el-option
                  v-for="item in ['一','二','三','四','五']"
                  :key="item"
                  :label="item"
                  :value="item">
              </el-option>
            </el-select>
          </template>
        </el-table-column>

        <el-table-column prop="operate" label="操作" width="300">
          <template slot-scope="scope">
            <el-button  type="danger" @click="delCourse(scope.row.id)" >删除</el-button>
          </template>
        </el-table-column>

      </el-table>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible1 = false">取 消</el-button>
    <el-button type="primary" @click="saveCCourse">确 定</el-button>
  </span>

    </el-dialog>


    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :model="form" label-width="180px">

        <el-form-item label="班级编号"  label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.classNo"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="班级名"  label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.className"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="班级人数"  label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.classNumber"></el-input>
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
  name: "ClassManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      courseData:[],
      classCourseData:[],
      depData: [],
      majorData: [],
      studentData:[],
      tableData: [],
      pageSize: 7,
      pageNum: 1,
      total: '',
      classNo: '',
      className: '',
      classNumber: '',
      majorId: null,
      centerDialogVisible: false,
      centerDialogVisible1: false,
      form: {
        classId:'',
        classNo:'',
        className: '',
        classNumber: ''




      },
      form1:{

      },



    }
  },
  methods: {
    delCourse(id){
      this.$axios.get(this.$httpUrl + '/class-course/del?id='+id,
      ).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
                message: '操作成功',
                type: 'success'

              },
          )
          this.loadcCourse()
          this.centerDialogVisible1= false

        } else {
          this.$message.error('操作失败');

        }
      })

    },
    saveCCourse(){
      //选课相同校验
      if(!this.classCourseData.length){
        this.$message.warning("未选择课程")
        return
      }
      // 课程id去重,对比原数组，看看是否有重复的课程
      if( new Set(this.classCourseData.map(v =>v.courseId+v.weekDay+v.section)).size !==this.classCourseData.length){
        this.$message.warning("课程重复")
        return
      }


      this.$axios.post(this.$httpUrl + '/class-course/save/', this.classCourseData
      ).then(res => res.data).then(res => {

        if (res.code === 200) {
          this.$message({
                message: '保存成功',
                type: 'success'
              }
          )
          this.loadcCourse()
          this.centerDialogVisible1 = false
          this.loadPost()
        } else {
          this.$message.error('保存失败');

        }
      })


    },
    addCourse(){

      this.classCourseData.push({

      })

    },
    formatClass(row){
      let temp=this.classCourseData.find(item=>{
        return item.classId===row.classId

      })
      return temp && row.className

    },

    handleCourse(classId){
      this.centerDialogVisible1 = true
      this.classId=classId
      this.loadcCourse()

    },

    loadGet() {
      this.$axios.get(this.$httpUrl + '/class/list').then(res => res.data).then(res => {
        // this.tableData=res

      })

    },
    del(classId){
      this.$axios.get(this.$httpUrl + '/class/del?classId='+classId,
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
      this.form.classId=row.classId
      this.form.classNo=row.classNo
      this.form.className=row.className
      this.form.classNumber=row.classNumber



      this.centerDialogVisible = true

    },
    save() {
      if (this.form.classId){
        this.$axios.post(this.$httpUrl + '/class/update', this.form
        ).then(res => res.data).then(res => {
          if (this.form.classId) {
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
        this.$axios.post(this.$httpUrl + '/class/save', this.form
        ).then(res => res.data).then(res => {
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
    loadcCourse(){
      this.$axios.get(this.$httpUrl + '/class-course/class/'+this.classId).then(res => res.data).then(res => {
        this.classCourseData=res.data
        if (res.code ==200) {
          this.courseData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },





    loadCourse(){
      this.$axios.get(this.$httpUrl + '/course/list').then(res => res.data).then(res => {
        this.courseData=res.data.filter(v => v.type==="是")

        if (res.code ==200) {
          this.courseData = res.data
        } else {
          // alert("获取数据失败")
        }
      })

    },
    loadClassCourse(){
      this.$axios.get(this.$httpUrl + '/class-course/list').then(res => res.data).then(res => {
        if (res.code ==200) {
          this.classCourseData = res.data
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
      this.$axios.post(this.$httpUrl + '/class/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          className: this.className,


        }

      }).then(res => res.data).then(res => {
        if (res.code == 200) {
          sessionStorage.setItem('CurClass',JSON.stringify(res.data.class))
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
      this.form.classNo=''
      this.form.className = ''
      this.form.classNumber=''


    },
    resetFormCCourse(){

          this.classCourseData.id=''


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
    this.loadClassCourse()

    this.loadPost();
  }
}

</script>

<style scoped>

</style>