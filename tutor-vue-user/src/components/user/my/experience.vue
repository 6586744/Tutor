<template>

  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="教学经验"  />
    <el-button type="primary" @click="dialogFormVisible = true"><i class="el-icon-circle-plus-outline"></i>添加</el-button>
    <el-divider></el-divider>
    <el-dialog title="添加经验" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="所教科目" :label-width="formLabelWidth">
          <el-input v-model="form.e_address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" :label-width="formLabelWidth">
          <el-date-picker
            v-model="form.e_start_time"
            type="month"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" :label-width="formLabelWidth">
          <el-date-picker
            v-model="form.e_end_time"
            type="month"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="详情" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="form.e_remake" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addType()">确 定</el-button>
      </div>
    </el-dialog>

    <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)">
      <!--      设置一个隐藏域用来保存id-->
      <!--      <el-table-column prop="t_id" label="id" />-->
      <el-table-column prop="e_address" label="所教科目" >
      </el-table-column>
      <el-table-column prop="e_start_time" label="开始时间" >
      </el-table-column>
      <el-table-column prop="e_end_time" label="结束时间" >
      </el-table-column>
      <el-table-column prop="e_remake" label="详情" >
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

    <el-dialog title="编辑地址" :visible.sync="updateForm">
      <el-form :model="formUpdate">
        <el-form-item label="地址名称" :label-width="formLabelWidth">
          <el-input v-model="formUpdate.updateName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateForm = false">取 消</el-button>
        <el-button type="primary" @click="updateTypeSubmit(beforeName)">确 定</el-button>
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
        e_address: '',
        e_start_time : '',
        e_end_time : '',
        e_remake : '',
      },
      formUpdate: {
        updateName: '',
        id:'',
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
      console.log(this.form.name);
      if(this.form.name != '') {
        this.axios.post(
          '/experience/addExperience',
          {
            e_school: this.form.e_school,
            e_teacher_id: this.$store.state.user.u_name.u_id,
            e_address: this.form.e_address,
            e_start_time: this.form.e_start_time,
            e_end_time: this.form.e_end_time,
            e_remake: this.form.e_remake,
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

    updateType(row) {
      console.log(row)
      this.formUpdate.updateName = row.a_name;
      this.formUpdate.id = row.a_id;
      this.updateForm = true;
      this.beforeName =  this.formUpdate.updateName;
    },
    updateTypeSubmit(beforeName) {
      if (beforeName != this.formUpdate.updateName) {
        this.axios.post(
          '/experience/updateExperience',
          {
            a_name: this.formUpdate.updateName,
            a_id : this.formUpdate.id
          }
        ).then(
          (resp) => {
            if (resp.data == 1) {
              this.$confirm("修改成功");
              this.formUpdate.updateName = '';//置空
              this.formUpdate.id = '';
              this.updateForm = false;
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
        this.updateForm = false;
      }
    },

    deleteType(row) {
      this.axios.get(
        '/experience/deleteExperience',
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

    load() {
      this.axios.post(
        '/experience/queryExperience',
        {
          e_teacher_id: this.$store.state.user.u_name.u_id
        }
      ).then(
        (resp) => {
          for(let i = 0; i < resp.data.length; i ++) {
            // console.log(resp.data[i].e_start_time)
            resp.data[i].e_start_time = resp.data[i].e_start_time.substring(0, 7)
            resp.data[i].e_end_time = resp.data[i].e_end_time.substring(0, 7)
          }
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
