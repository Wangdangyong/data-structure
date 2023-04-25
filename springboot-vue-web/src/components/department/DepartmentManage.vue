<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">
      <!--    回车设置-->
      <el-input v-model="depName" placeholder="请输入院系名" style="width: 200px;"
                @keyup.enter.native="loadPost">
      </el-input>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">搜索</el-button>
      <el-button type="success " @click="resetForm">重置</el-button>
      <el-button type="primary " @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background: '#f2f5fc',color:'#555555'}"
    >
      <el-table-column prop="depNo" label="院系编号" width="200">
      </el-table-column>
      <el-table-column prop="depName" label="院系名" width="300">
      </el-table-column>
      <el-table-column prop="depMajorCount" label="专业数量" width="200">
      </el-table-column>
      <el-table-column prop="depTeaCount" label="教师人数" width="200">
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
              @confirm="del(scope.row.depId)"
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
        <el-form-item label="院系编号"   >
          <el-col :span="20">
            <el-input v-model="form.depNo"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="院系名"  label-width="180px" >
          <el-col :span="20">
            <el-input v-model="form.depName"></el-input>
          </el-col>
        </el-form-item>

<!--        <el-form-item label="院系名" >-->
<!--          <el-col :span="20">-->
<!--            <el-select v-model="form.depId" placeholder="请选择院系" style="margin-left: 5px">-->
<!--              <el-option-->
<!--                  v-for="item in tableData"-->
<!--                  :key="item.depId"-->
<!--                  :label="item.depName"-->
<!--                  :value="item.depId">-->
<!--              </el-option>-->
<!--            </el-select>-->
<!--          </el-col>-->
<!--        </el-form-item>-->


        <el-form-item label="专业数量"  label-width="180px">
          <el-col :span="20">
            <el-input v-model="form.depMajorCount"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="教师数量"  label-width="180px">
          <el-col :span="20">
            <el-input v-model="form.depTeaCount"></el-input>
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
  name: "DepartmentManage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [],
      pageSize: 7,
      pageNum: 1,
      total: '',
      depName: '',
      centerDialogVisible: false,
      form: {
        depId:'',
        depNo:'',
        depName: '',
        depMajorCount: '',
        depTeaCount: ''



      },



    }
  },
  methods: {
    loadGet() {
      this.$axios.get(this.$httpUrl + '/department/list').then(res => res.data).then(res => {
        console.log(res)
        // this.tableData=res

      })

    },
    del(depId){
      this.$axios.get(this.$httpUrl + '/department/del?depId='+depId,
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
      this.form.depId=row.depId
      this.form.depNo=row.depNo


      this.form.depName=row.depName
      this.form.depMajorCount=row.depMajorCount
      this.form.depTeaCount=row.depTeaCount


      this.centerDialogVisible = true

    },
    save() {
      if (this.form.depId){
        this.$axios.post(this.$httpUrl + '/department/update', this.form
        ).then(res => res.data).then(res => {
          if (this.form.depId) {
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
        this.$axios.post(this.$httpUrl + '/department/save', this.form
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
    loadPost() {
      this.$axios.post(this.$httpUrl + '/department/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          depName: this.depName,


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
      this.form.depNo=''


      this.form.depName=''
      this.form.depMajorCount=''
      this.form.depTeaCount=''
      this.depName = ''

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
    this.loadPost();
  }
}

</script>

<style scoped>

</style>