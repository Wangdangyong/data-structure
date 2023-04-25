<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="teacherName" placeholder="请输入名字" style="width: 200px;"
                @keyup.enter.native="loadPost">
      </el-input>
      <el-select v-model="teacherDepId" placeholder="请选择院系" style="margin-left: 5px">
        <el-option
            v-for="item in depData"
            :key="item.depId"
            :label="item.depName"
            :value="item.depId">
        </el-option>
      </el-select>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">搜索</el-button>
      <el-button type="success " @click="resetForm">重置</el-button>
      <el-button type="primary " @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >
      <el-table-column prop="teacherNumber" label="教职工号" width="300">
      </el-table-column>
      <el-table-column prop="teacherName" label="教师姓名" width="300">
      </el-table-column>
      <el-table-column prop="teacherDepId" label="所属院系" width="300" :formatter="formatDep">
      </el-table-column>




      <el-table-column prop="operate" label="操作" width="300">
        <template slot-scope="scope">
          <el-button type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.teacherId)"
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

        <el-form-item label="教职工号"   label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.teacherNumber"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="教师姓名"  label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.teacherName"></el-input>
          </el-col>
        </el-form-item>


        <el-form-item label="院系" >
          <el-col :span="20">
            <el-select v-model="form.teacherDepId" placeholder="请选择院系" style="margin-left: 5px">
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
  name: "TeacherManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      depData: [],
      tableData: [],
      pageSize: 7,
      pageNum: 1,
      total: '',
      teacherName: '',
      teacherDepId: '',
      centerDialogVisible: false,
      form: {
        teacherId:'',
        teacherNumber:'',
        teacherName: '',
        teacherDepId: ''



      },



    }
  },
  methods: {
    formatDep(row){
      let temp=this.depData.find(item=>{
        return item.depId==row.teacherDepId

      })
      return temp && temp.depName

    },
    loadGet() {
      this.$axios.get(this.$httpUrl + '/teacher/list').then(res => res.data).then(res => {
        console.log(res)
        // this.tableData=res

      })

    },
    del(teacherId){
      this.$axios.get(this.$httpUrl + '/teacher/del?teacherId='+teacherId,
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
      this.form.teacherId=row.teacherId
      this.form.teacherNumber=row.teacherNumber
      this.form.teacherName=row.teacherName
      this.form.teacherDepId=row.teacherDepId


      this.centerDialogVisible = true


    },
    update(){
      this.$axios.post(this.$httpUrl + '/teacher/update', this.form
      ).then(res => res.data).then(res => {
        if (this.form.teacherId) {
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

    },

    save() {
      if (this.form.teacherId){
        this.$axios.post(this.$httpUrl + '/teacher/update', this.form
        ).then(res => res.data).then(res => {
          if (this.form.teacherId) {
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
        this.$axios.post(this.$httpUrl + '/teacher/save', this.form
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
    loadPost() {
      this.$axios.post(this.$httpUrl + '/teacher/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          teacherName: this.teacherName,
          teacherDepId: this.teacherDepId+''


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
      this.form.teacherNumber='',
      this.form.teacherDepId='',
      this.form.teacherName = ''
      this.teacherName=''

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
    this.loadDep()
    this.loadPost();
  }
}

</script>

<style scoped>

</style>