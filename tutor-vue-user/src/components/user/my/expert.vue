<template>

  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="擅长科目"  />
    <el-button type="primary" @click="dialogFormVisible = true"><i class="el-icon-circle-plus-outline"></i>添加</el-button>
    <el-divider></el-divider>
    <el-dialog title="添加科目" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="选择科目" :label-width="formLabelWidth">
          <el-select v-model="form.e_big_type_name" >
            <el-option
              v-for="(item, index) in typeList"
              :key="index"
              :label="item.t_name"
              :value="item.t_name">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addType()">确 定</el-button>
      </div>
    </el-dialog>

    <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)">
      <el-table-column prop="e_big_type_name" label="详情" >
      </el-table-column>
      <el-table-column prop="time" label="创建时间" />
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="deleteType(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="total, sizes, prev, pager, next, jumper"
      style="align-content: center; align-items: center"
      :current-page="currentPage"
      :page-sizes="pageSizes"
      :total="totalCount"
      :page-size="PageSize"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    ></el-pagination>
  </div>
</template>

<script>
export default {
  name: "AllType",
  data() {
    return {
      dialogFormVisible: false,
      updateForm : false,
      //添加表单的数据
      tableData: [],
      // 默认显示第几页
      currentPage:1,
      // 个数选择器（可修改）
      pageSizes:[1,2,3,4,5,6,7,8,9,10],
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount:1,
      // 个数选择器（可修改）
      PageSize:10,
      form: {
        e_big_type_name: '',
      },

      typeList: [],
      formLabelWidth: '120px',
      beforeName : ''
    };
  },

  mounted() {
    this.load();
    this.loadList()
  },

  methods : {
    addType() {
      console.log(this.form.name);
      if(this.form.name != '') {
        this.axios.post(
          '/expert/addExpert',
          {
            e_teacher_id: this.$store.state.user.u_name.u_id,
            e_big_type_name: this.form.e_big_type_name,
          }
        ).then(
          (resp) => {
            if (resp.data == 1) {
              this.$confirm("添加成功");
              // this.form.name = ''//置空
              this.dialogFormVisible = false;
              this.load();
            }
            else {
              this.$confirm(resp.data);
            }
          }
        ).catch(
          (resp) => {
            this.$confirm("网络异常！");
          }
        )
      }
      else {
        this.dialogFormVisible = false;
      }
    },

    deleteType(row) {
      this.axios.get(
        '/expert/deleteExpert',
        {
          params : {
            id : row.e_id
          }
        }
      ).then(
        (resp) => {
          this.$confirm("删除成功！");
          this.load()
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )

    },
    // 分页
    // 每页显示的条数
    handleSizeChange(val) {
      this.PageSize=val
      this.currentPage=1
    },
    // 显示第几页
    handleCurrentChange(val) {
      // 改变默认的页数
      this.currentPage=val
    },

    loadList() {
      this.axios.get(
        '/type/getAllType',
      ).then(
        (resp) => {
          this.typeList = resp.data;
        }
      )
    },
    load() {
      this.axios.post(
        '/expert/queryExpert',
        {
          e_teacher_id: this.$store.state.user.u_name.u_id
        }
      ).then(
        (resp) => {
          this.tableData = resp.data;
          this.totalCount = resp.data.length
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    }
  }
}
</script>

<style scoped>

</style>
