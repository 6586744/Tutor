<template>

  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="订单管理"  />
    <el-row style="margin-left: 30px; margin-bottom: 10px">
      <label style=";margin-right: 10px">订单号:</label>
      <el-input style="width: 200px" v-model="searchForm.o_id" placeholder="请输入材料名称"></el-input>
      <label style="margin-left: 30px;margin-right: 10px">课程类型:</label>
      <el-select v-model="searchForm.o_type" placeholder="请选择">
        <el-option
          v-for="(item, index) in typeList"
          :key="index"
          :label="item.t_name"
          :value="item.t_name">
        </el-option>
      </el-select>
      <label style="margin-left: 30px;margin-right: 10px">课程状态:</label>
      <el-select v-model="searchForm.o_status" placeholder="请选择">
        <el-option label="寻找中" value="0"></el-option>
        <el-option label="对接中" value="1"></el-option>
        <el-option label="教学中" value="2"></el-option>
        <el-option label="已完成" value="3"></el-option>
      </el-select>
      <label style="margin-left: 30px;margin-right: 10px">发布者:</label>
      <el-input style="width: 200px" v-model="searchForm.o_student_name" placeholder="请输入用户名"></el-input>
      <el-button size="small" type="primary" @click="queryOrder()"><i class="el-icon-search"></i>搜索</el-button>
      <el-button size="small" @click="resetSearch()"><i class="el-icon-refresh-left"></i>重置</el-button>
    </el-row>
    <el-row style="margin-left: 30px; margin-bottom: 10px">
      <el-button size="small" type="success" @click="exportExcel()"><i class="el-icon-upload2"></i>批量导出</el-button>
    </el-row>
    <el-divider></el-divider>
    <el-table id="tableData" :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)">
      <!--      设置一个隐藏域用来保存id-->
      <!--      <el-table-column prop="t_id" label="id" />-->
      <el-table-column prop="o_id" label="订单号" >
      </el-table-column>
      <el-table-column prop="o_student_name" label="发布者" >
      </el-table-column>
      <el-table-column prop="o_name" label="学生姓名" >
      </el-table-column>
      <el-table-column prop="o_type" label="课程类型" >
      </el-table-column>
      <el-table-column prop="o_teacher_name" label="教师" >
      </el-table-column>
      <el-table-column prop="a_name" label="学生押金" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.o_user_status == '1'" >已交</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_user_status == '0'" >未交</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="a_name" label="教师押金" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.o_teacher_status == '1'" >已交</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_teacher_status == '0'" >未交</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="a_name" label="订单状态" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.o_status == '0'" >寻找中</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '1'" >对接中</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '2'" >教学中</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '3'" >已完成</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '4'" >已完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="创建时间" />
      <el-table-column
        fixed="right"
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="toOrderInfo(scope.row)">详情</el-button>
          <el-button v-if="scope.row.o_status == '0'" type="text" size="small" @click="toSubscribeInfo(scope.row)">分配</el-button>
<!--          <el-button v-if="scope.row.o_status == '1' && scope.row.o_teacher_status == '0'-->
<!--                     && scope.row.o_user_status == '0'" type="text" size="small" @click="toSubscribeInfo(scope.row)">分配</el-button>-->
          <el-button v-if="scope.row.o_status == '3'" type="text" size="small" @click="openCommentTable(scope.row)">查看评价</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="详情" :visible.sync="showOrderInfo">
      <div class="grid-content bg-purple">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>订单详情</span>
          </div>
          <div class="name-role">
            <span class="sender">{{user.o_student_name}}</span>
          </div>
          <div class="name-role">
            <el-avatar style="text-align: center" :size="60">
              <img :src="user.o_student_image"/>
            </el-avatar>
          </div>
          <div class="registe-info">
          <span class="registe-info">
            发布时间：
            <li class="fa fa-clock-o"></li>
             {{user.time}}
          </span>
          </div>
          <el-divider></el-divider>
          <div class="personal-relation">
            <div class="relation-item">学生姓名:  <div style="float: right; padding-right:20px;">{{user.o_name}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">联系方式:  <div style="float: right; padding-right:20px;">{{user.o_phone}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">学生性别:  <div style="float: right; padding-right:20px;">{{user.o_sex}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">学生年龄  <div style="float: right; padding-right:20px;">{{user.o_age}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">科目:  <div style="float: right; padding-right:20px;">{{user.o_type}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">价格:  <div style="float: right; padding-right:20px;">{{user.o_price}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">上课时间:  <div style="float: right; padding-right:20px;">{{user.o_time}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">备注:  <div style="float: right; padding-right:20px;">{{user.o_reamke}}</div></div>
          </div>
        </el-card>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showOrderInfo = false">取 消</el-button>
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

    <el-dialog title="查看评价" width="50%" :visible.sync="showCommentTable">
      <el-table :data="commentList">
        <el-table-column prop="c_content" label="评价内容" >
        </el-table-column>
        <el-table-column prop="c_user_show" label="用户昵称" >
        </el-table-column>
        <el-table-column prop="time" label="时间" >
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showCommentTable = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
export default {
  name: "order",
  data() {
    return {

      /**
       * 搜索相关数据
       */
      searchForm : {
        o_student_name: '',
        o_id: '',
        o_type: '',
        o_status: '',
      },

      typeList: [],
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
      formLabelWidth: '120px',

      /**
       * 订单详情相关
       */
      showOrderInfo: false,
      user : {
        o_student_id: '',
        o_student_name: '',
        o_student_image: '',
        o_name: '',
        o_phone: '',
        o_price: '',
        o_type: '',
        o_sex: '',
        o_age: '',
        o_reamke: '',
        o_address: '',
        o_time: '',
      },

      showCommentTable: false,
      commentList:[],
    };
  },

  mounted() {
    this.load();
    this.loadType();
  },

  methods : {

    /**
     * 动态查询
     */
    queryOrder() {
      this.axios.post(
        '/order/findOrder', {
          o_id: this.searchForm.o_id,
          o_student_name: this.searchForm.o_student_name,
          o_type: this.searchForm.o_type,
          o_status: this.searchForm.o_status,
        }
      ).then(
        (resp) => {
            this.tableData = resp.data;
            this.totalCount = resp.data.length
        }
      )
    },

    /**
     * 重置搜索表单。
     */
    resetSearch() {
      this.searchForm.o_student_name = '';
      this.searchForm.o_id = '';
      this.searchForm.o_type = '';
      this.searchForm.o_status = '';
      this.load()
    },
    loadType() {
      this.axios.get(
        '/type/getAllType',
      ).then(
        (resp) => {
          this.typeList = resp.data
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },


    /**
     * 批量导出
     * @returns {any}
     */
    exportExcel () {
      /* generate workbook object from table */
      let wb = XLSX.utils.table_to_book(document.querySelector('#tableData'))
      /* get binary string as output */
      let wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
      try {
        FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), 'sheetjs.xlsx')
      } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
      return wbout
    },

    openCommentTable(row) {
      this.axios.post(
        '/comment/queryComment', {
          c_order_id: row.o_id
        }
      ).then(
        (resp) => {
          this.commentList =resp.data;
          this.showCommentTable = true;
        }
      )
    },

    /**
     * 跳转到预约详情。
     */
    toSubscribeInfo(row) {
      console.log(row.o_id)
      this.$router.push({
        path:`/subscribeInfo/${row.o_id}`
      })
    },

    toOrderInfo(row) {
        this.showOrderInfo = true
        this.axios.get(
          '/order/getOrderById',
          {
            params: {
              id: row.o_id,}
          }
        ).then(
          (resp) => {
            this.user = resp.data;
            // this.user.o_phone = resp.data.o_phone.substring(0,3) + "****" + resp.data.o_phone.substring(7,11)
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
        '/order/getAllOrder',
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

<style lang="scss" scoped>
//卡片样式
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
}
//文本样式区
  .name-role {
    font-size: 16px;
    padding: 5px;
    text-align:center;
  }
.sender{
  text-align:center;
  font-family: 华文行楷;
  color: #303133;
  font-size: 25px;
}
.registe-info{
  text-align: center;
  padding-top:10px;
}
.personal-relation {
  font-size: 16px;
  padding: 0px 5px 15px;
  margin-right: 1px;
  width: 100%
}

.relation-item {
  padding: 12px;

}
.dialog-footer{
  padding-top:10px ;
  padding-left: 10%;
}
//布局样式区
  .el-row {
    margin-bottom: 20px;
&:last-child {
   margin-bottom: 0;
 }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
