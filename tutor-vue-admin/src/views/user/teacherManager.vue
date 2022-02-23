<template>

  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="教师管理"  />
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
      <el-table-column label="认证" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.u_authentication == '1'" >已认证</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.u_authentication == '0'" >未认证</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150">
        <template slot-scope="scope">
          <!--          <el-button type="text" size="small" @click="updateType(scope.row)">编辑</el-button>-->
          <el-button v-if="scope.row.u_status == '1'" type="text" size="small" @click="updateUserRootId2(scope.row)">禁用</el-button>
          <el-button v-if="scope.row.u_status == '0'" type="text" size="small" @click="updateUserRootId3(scope.row)">启用</el-button>

          <el-button type="text" size="small" @click="previewUser(scope.row)">查看</el-button>

          <el-button v-if="scope.row.u_authentication == '1'" type="text" size="small" @click="updateUserAuthId2(scope.row)">取消认证</el-button>
          <el-button v-if="scope.row.u_authentication == '0'" type="text" size="small" @click="openAuthForm(scope.row)">认证</el-button>
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

    <el-dialog title="认证" :visible.sync="showAuthForm">
      <el-form :model="authForm">
        <el-form-item label="身份证号" :label-width="formLabelWidth">
          <el-input disabled v-model="authForm.u_card" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="头像" :label-width="formLabelWidth">
            <img :src="authForm.u_card_image" class="avatar">
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showAuthForm = false">关 闭</el-button>
        <el-button v-if="authForm.u_authentication == '0'" type="primary" @click="deleteAuth()">不通过</el-button>
        <el-button v-if="authForm.u_authentication == '0'" type="primary" @click="addAuth()">通过</el-button>
      </div>
    </el-dialog>
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

      showAuthForm: false,
      authForm: {
        u_id: '',
        u_card: '',
        u_card_image: '',
        u_authentication : '',
      }
    };
  },

  mounted() {
    this.load();
    this.loadAcademic()
  },

  methods : {

    /**
     * 认证相关。打开认证表单
     */
    openAuthForm(row) {
      this.authForm.u_id = row.u_id;
      this.authForm.u_card = row.u_card;
      this.authForm.u_card_image = row.u_card_image;
      this.authForm.u_authentication = row.u_authentication;
      this.showAuthForm = true
    },

    /**
     * 不通过
     */
    deleteAuth() {
      this.axios.post(
        'user/updateUser',
        {
          u_id: this.authForm.u_id, //这里取表单数据
          u_card : '',
          u_card_image: '',
        }
      ).then(
        (resp) => {
          this.load();
          this.$confirm("修改成功")
        }
      )
    },
    previewUser(row) {
      this.$router.push({
        path:`/previewUser/${row.u_id}`
      })
    },

    /**
     * 预览头像
     * @param row
     */
    mouseOver(row) {
      this.showUserImg = true;
      this.imageUrl = ''
      this.imageUrl = row.u_image
    },
    addAuth() { //提升权限
      this.axios.post(
        'user/updateUser',
        {
          u_id: this.authForm.u_id, //这里取表单数据
          u_authentication : '1'}
      ).then(
        (resp) => {
          this.load();
          this.$confirm("修改成功")
        }
      )
    },
    updateUserAuthId2(row) {
      this.axios.post(
        'user/updateUser',
        {
          u_id: row.u_id,
          u_authentication : '0'}
      ).then(
        (resp) => {
          this.load();
          this.$confirm("修改成功")
        }
      )
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
          u_role: '2'
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
