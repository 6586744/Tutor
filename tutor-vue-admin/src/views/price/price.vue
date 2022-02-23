<template>

  <div>
    <el-alert style="background-color: #409EFF; color: #222222; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="科目价格管理"  />
    <el-button type="primary" @click="dialogFormVisible = true"><i class="el-icon-circle-plus-outline"></i>添加价格</el-button>
    <el-divider></el-divider>
    <el-dialog title="添加价格" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="科目名称" :label-width="formLabelWidth">
          <el-input v-model="form.p_name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="最低价格" :label-width="formLabelWidth">
          <el-input v-model="form.p_start_price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="最高价格" :label-width="formLabelWidth">
          <el-input v-model="form.p_end_price" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addType()">确 定</el-button>
      </div>
    </el-dialog>

    <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)">
      <el-table-column prop="p_name" label="科目名称" >
      </el-table-column>
      <el-table-column prop="p_start_price" label="最低价格" >
      </el-table-column>
      <el-table-column prop="p_end_price" label="最高价格" >
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

    <el-dialog title="编辑价格" :visible.sync="updateForm">
      <el-form :model="formUpdate">
        <el-form-item label="科目名称" :label-width="formLabelWidth">
          <el-input v-model="formUpdate.p_name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="最低价格" :label-width="formLabelWidth">
          <el-input v-model="formUpdate.p_start_price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="最高价格" :label-width="formLabelWidth">
          <el-input v-model="formUpdate.p_end_price" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateForm = false">取 消</el-button>
        <el-button type="primary" @click="updateTypeSubmit()">确 定</el-button>
      </div>
    </el-dialog>

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
        p_name:'',
        p_start_price:'',
        p_end_price:'',
      },
      formUpdate: {
        p_id: '',
        p_name:'',
        p_start_price:'',
        p_end_price:'',
      },
      formLabelWidth: '120px',
      beforeName : ''
    };
  },

  mounted() {
    this.load();
  },

  methods : {
    addType() {
      if(this.form.name != '') {
        this.axios.post(
          '/price/addPrice',
          {
            p_name: this.form.p_name,
            p_start_price: this.form.p_start_price,
            p_end_price: this.form.p_end_price,
          }
        ).then(
          (resp) => {
            if (resp.data == 1) {
              this.$confirm("添加成功");
              this.form.p_name = ''//置空
              this.form.p_start_price = ''//置空
              this.form.p_end_price = ''//置空
              this.dialogFormVisible = false;
              this.load();
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

    updateType(row) {
      console.log(row)

      this.formUpdate.p_id = row.p_id;
      this.formUpdate.p_name = row.p_name;
      this.formUpdate.p_start_price = row.p_start_price;
      this.formUpdate.p_end_price = row.p_end_price;
      this.updateForm = true;
    },
    updateTypeSubmit() {
      console.log(this.formUpdate)
        this.axios.post(
          '/price/updatePrice',
          {
            p_id : this.formUpdate.p_id,
            p_name : this.formUpdate.p_name,
            p_start_price : this.formUpdate.p_start_price,
            p_end_price : this.formUpdate.p_end_price,
          }
        ).then(
          (resp) => {
            if (resp.data == 1) {
              this.$confirm("修改成功");
              this.formUpdate.p_id = '';//置空
              this.formUpdate.p_name = ''//置空
              this.formUpdate.p_start_price = ''//置空
              this.formUpdate.p_end_price = ''//置空
              this.updateForm = false;
              this.load();
            }
          }
        ).catch(
          (resp) => {
            this.$confirm("网络异常！");
          }
        )
    },

    deleteType(row) {
      this.axios.get(
        '/price/deletePrice',
        {
          params : {
            id : row.p_id
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
      // 改变每页显示的条数
      this.PageSize=val
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.currentPage=1
    },
    // 显示第几页
    handleCurrentChange(val) {
      // 改变默认的页数
      this.currentPage=val
    },

    load() {
      this.axios.get(
        '/price/getAllPrice',
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
