<template>

  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="家长管理"  />
    <el-divider></el-divider>

    <el-table id="tableData" :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)">
      <el-table-column prop="u_name" label="真实姓名" >
      </el-table-column>
      <el-table-column prop="u_phone" label="电话" >
      </el-table-column>
      <el-table-column prop="u_sex" label="姓名" >
      </el-table-column>
      <el-table-column prop="u_show" label="昵称" >
      </el-table-column>
      <el-table-column label="头像" >
        <template slot-scope="scope">
          <el-button type="success" size="small" plain @click="mouseOver(scope.row)" >预览</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="u_email" label="邮箱" >
      </el-table-column>
      <el-table-column prop="u_address_name" label="地区" >
      </el-table-column>
      <el-table-column prop="time" label="创建时间" />
      <el-table-column label="状态" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.u_status == '1'" >启用</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.u_status == '0'" >禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150">
        <template slot-scope="scope">
          <el-button v-if="scope.row.u_status == '1'" type="text" size="small" @click="updateUserRootId2(scope.row)">禁用</el-button>
          <el-button v-if="scope.row.u_status == '0'" type="text" size="small" @click="updateUserRootId3(scope.row)">启用</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="预览" width="30%"  :visible.sync="showUserImg">
      <el-image :src="imageUrl" ></el-image>
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
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
import {validatePhone} from "@/utils/validator";
export default {
  name: "AllType",
  data() {
    return {
      imageUrl: '',
      showUserImg:false,
      /*
      * 基础数据
      * */
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
      formLabelWidth: '120px',
    };
  },

  mounted() {
    this.load();
    this.loadAcademic()
  },

  methods : {
    /**
     * 预览头像
     * @param row
     */
    mouseOver(row) {
      this.showUserImg = true;
      this.imageUrl = ''
      this.imageUrl = row.u_image
    },

    /**
     * 提升权限
     **/
    updateUserRootId2(row) {
      this.axios.post(
        'user/updateUser',
        {
          u_id: row.u_id,
          u_status : '0'}
      ).then(
        (resp) => {
          this.load();
          this.$confirm("修改成功")
        }
      )
    },
    /**
     * 降低权限
     **/
    updateUserRootId3(row) {
      this.axios.post(
        'user/updateUser',
        {u_id: row.u_id, u_status : '1'}
      ).then(
        (resp) => {
          this.load();
          this.$confirm("修改成功")
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
      this.axios.post(
        '/user/queryUser',
        {
          u_role: '1'
        }
      ).then(
        (resp) => {
          console.log(resp.data)
          this.tableData = resp.data
          this.totalCount = resp.data.length
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
