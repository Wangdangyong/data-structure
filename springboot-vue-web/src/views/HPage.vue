<template>
  <!-- 表头设置和表的边框-->
  <div>
    <div style="margin-left: 5px;margin-bottom: 20px">



      <el-button type="primary " @click="add">新增公告</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{textAlign: 'center',background: '#f2f5fc',color:'#555555'}"
    >
      <el-table-column prop="notice" label="公告" height="200" width="800" style="color: crimson" >
      </el-table-column>

      <el-table-column prop="operate" label="操作" width="400">
        <template slot-scope="scope">
          <el-button type="success" @click="mod(scope.row)" style="margin-right: 20px">修改公告</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
              style="margin-left: 15px"
          >
            <el-button slot="reference" type="danger" >删除公告</el-button>




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
        <el-form-item label="公告" prop="notice">
          <el-input type="textarea" v-model="form.notice"></el-input>
        </el-form-item>


      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>

    <div class="block" style="margin-top: 1px;text-align: center">
      <el-carousel height="500px">

        <el-carousel-item>
          <img src="../assets/gz2.jpg" alt="" height="100%" width="90%">
        </el-carousel-item>
        <el-carousel-item>
          <img src="../assets/gz3.jpg" alt="" height="100%" width="90%">
        </el-carousel-item>

        <el-carousel-item>
          <img src="../assets/gz4.jpg" alt="" height="100%" width="90%">
        </el-carousel-item>
        <el-carousel-item>
          <img src="../assets/gz5.jpg" alt="" height="100%" width="90%">
        </el-carousel-item>
        <el-carousel-item>
          <img src="../assets/gz6.jpg" alt="" height="100%" width="90%">
        </el-carousel-item>
        <el-carousel-item>
          <img src="../assets/www.jpg" alt="" height="100%" width="90%">
        </el-carousel-item>

      </el-carousel>
    </div>
  </div>








</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "HPage",
  data() {
    return {
      registerShow:false,
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      tableData: [],
      centerDialogVisible: false,
      form: {
        id:'',
        notice:''

      },
      rules:{
        notice: [

          { required: true, message: '请填写活公告', trigger: 'blur' }


        ]

      },



    }
  },
  methods: {

    loadGet() {
      this.$axios.get(this.$httpUrl + '/notice/list').then(res => res.data).then(res => {
        this.tableData=res.data

      })

    },
    del(id){
      this.$axios.get(this.$httpUrl + '/notice/del?id='+id,
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
      this.form.notice=row.notice

      this.centerDialogVisible = true

    },
    save() {
      if (this.form.id){
        this.$axios.post(this.$httpUrl + '/notice/update', this.form
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
        this.$axios.post(this.$httpUrl + '/notice/save', this.form
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
      this.form.notice=''

    },
    add() {
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm();
      })
    }
  },
  beforeMount() {
    this.loadGet();

  }
}

</script>

<style  >




</style>