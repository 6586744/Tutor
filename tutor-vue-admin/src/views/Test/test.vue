<template>
  <div>
    <el-alert
      :title="msg"
      type="success"
      :closable="false">
    </el-alert>
    <el-button round @click="dialogVisible = true">添加</el-button>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column prop="t_name" label="科目名" >
      </el-table-column>

      <el-table-column prop="time" label="创建时间" />
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateType(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="deleteType(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="添加分类"
      :visible.sync="dialogVisible"
      width="30%"
      >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="分类名称:">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addType">确 定</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
export default {
  name: "test",
  data() {
    return {
      dialogVisible: false,
      //页面数据
      msg: "12",
      tableData: [],

      form: {
        name: '',
      },
    }
  },

  /*
  可以渲染数据，进行初始化数据。
   */
  mounted() {
    this.loadData();
  },
  /**
   * 页面的方法区。
   */
  methods : {

    deleteType(row) {
      console.log(row.t_id)
      this.axios.get(
        '/type/deleteType', {
          params: {
            id: row.t_id
          }
        }
      ).then(
        (resp) => {
          this.$confirm("删除成功");
          this.loadData()
        }
      )
    },
    addType() {
      if(this.form.name == "") {
        this.$confirm("请输入类型名称！");
        return ;
      }
      this.axios.post(
        '/type/addType', {
          t_name: this.form.name
        }
      ).then(
        (resp) => {
          if (resp.data != '1') {
            this.$confirm(resp.data);
          }
          else {
            this.$confirm("添加成功！");
            this.dialogVisible = false;
            this.form.name = "";
            this.loadData();
          }
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },

    updateType() {
    },

    loadData() {
      this.axios.get(
        '/type/getAllType',
      ).then( //成功回调函数
        (resp) => {
          console.log(resp.data)
          this.tableData = resp.data
        }
      )
    },
  },

}
</script>

<style scoped>

</style>
