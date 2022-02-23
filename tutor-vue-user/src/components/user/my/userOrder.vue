<template>

  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="订单列表"  />

    <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)">
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
      <el-table-column prop="o_teacher_name" label="联系方式" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.o_status == '0'" >已屏蔽</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '1'" >已屏蔽</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '2'" >{{scope.row.o_teacher_phone}}</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '3'" >{{scope.row.o_teacher_phone}}</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '4'" >{{scope.row.o_teacher_phone}}</el-tag>
        </template>
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
          <el-button type="text" v-if="scope.row.o_status == '0'" size="small" @click="toEditOrder(scope.row)">编辑</el-button>
          <el-button type="text" v-if="scope.row.o_status == '0'" size="small" @click="deleteOrder(scope.row)">删除</el-button>
          <el-button type="text" v-if="scope.row.o_status == '0'" size="small" @click="openSubscribeTeacher(scope.row)">预约</el-button>
          <el-button type="text" v-if="scope.row.o_status == '0'" size="small" @click="userCheckSubscribe(scope.row)">预约我的</el-button>
          <el-button type="text" v-if="scope.row.o_status == '1' && scope.row.o_user_status == '0'" size="small" @click="addUserStatus(scope.row)">缴纳押金</el-button>
          <el-button type="text" v-if="scope.row.o_status == '1'" size="small" @click="updateOrderTeacher(scope.row)">换老师</el-button>
          <el-button type="text" v-if="scope.row.o_status == '1'" size="small" @click="showTeacherInfo(scope.row)">查看教师</el-button>
          <el-button type="text" v-if="scope.row.o_status == '2'" size="small" @click="updateOrderStatus3(scope.row)">完成授课</el-button>
          <el-button type="text" v-if="scope.row.o_status == '3'" size="small" @click="addComment(scope.row)">添加评价</el-button>
          <el-button type="text" v-if="scope.row.o_status == '3'" size="small" @click="openCommentTable(scope.row)">查看评价</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="编辑订单" :visible.sync="updateForm">
      <el-form :model="formUpdate">
        <el-form-item label="孩子姓名" :label-width="formLabelWidth">
          <el-input disabled v-model="formUpdate.o_name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="孩子性别" :label-width="formLabelWidth">
          <el-radio v-model="formUpdate.o_sex" label="男">男</el-radio>
          <el-radio v-model="formUpdate.o_sex" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="孩子年龄" :label-width="formLabelWidth">
          <el-input v-model="formUpdate.o_age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" :label-width="formLabelWidth">
          <el-input v-model="formUpdate.o_phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课时费" :label-width="formLabelWidth">
          <el-input v-model="formUpdate.o_price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="选择地址" :label-width="formLabelWidth">
          <el-input disabled v-model="formUpdate.o_address" placeholder="请选择">
          </el-input>
        </el-form-item>
        <el-form-item label="选择科目" :label-width="formLabelWidth">
          <el-select v-model="formUpdate.o_type" >
            <el-option
              v-for="(item, index) in typeList"
              :key="index"
              :label="item.t_name"
              :value="item.t_name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="教学时间说明" :label-width="formLabelWidth">
          <el-input v-model="formUpdate.o_time" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="教师要求" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="formUpdate.o_reamke" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateForm = false">取 消</el-button>
        <el-button type="primary" @click="updateOrderSubmit()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="预约列表" :visible.sync="showSubscribeTable">
      <el-table :data="subscribeList">
        <el-table-column prop="s_user_name" label="教师名" >
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="deleteSubscribe(scope.row)">删除</el-button>
            <el-button type="text" size="small" @click="toTeacherInfo(scope.row)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showSubscribeTable = false">取 消</el-button>
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
    <el-dialog title="添加评价" width="50%" :visible.sync="showCommentForm">
    <el-form :model="comment">
      <el-form-item label="评价内容" :label-width="formLabelWidth">
        <el-input type="textarea"  v-model="comment.c_content" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="showCommentForm = false">取 消</el-button>
      <el-button type="primary" @click="submitComment()">确 定</el-button>
    </div>
  </el-dialog>
    <el-dialog title="查看评价" width="50%" :visible.sync="showCommentTable">
      <el-table :data="commentList">
        <el-table-column prop="c_content" label="评价内容" >
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

      /**
       * 修改相关
       */
      formLabelWidth: '120px',
      typeList:[], // 类型列表
      formUpdate: {
        o_id: '',
        o_phone: '',
        o_price: '',
        o_type: '',
        o_sex: '',
        o_age: '',
        o_reamke: '',
        o_time: '',
        o_name:'',
        o_address:'',
      },

      /**
       * 预约相关
       */
      currentOrderId: '', //记录当前的订单id
      showSubscribeTable: false,
      subscribeList: [],

      /*
      * 评论相关
      * */
      showCommentForm: false,
      comment: {
        c_order_id :'',
        c_teacher_id :'',
        c_user_id :'',
        c_user_name :'',
        c_user_image :'',
        c_level :'',
        c_content :'',
      },

      showCommentTable: false,
      commentList:[],
    };
  },

  mounted() {
    this.load();
  },

  methods : {
    /*
    跳转到预约我的老师的界面
    */
    userCheckSubscribe(row) {
      this.$router.push({
        path:`/userCheckSubscribe/${row.o_id}`
      })
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
    * 评论相关
    */
    addComment(row) {
      this.comment.c_order_id =  row.o_id;
      this.comment.c_teacher_id =  row.o_teacher_id;
      this.comment.c_user_id =  this.$store.state.user.u_name.u_id;
      this.comment.c_user_name =  this.$store.state.user.u_name.u_name;
      this.comment.c_user_image =  this.$store.state.user.u_name.u_image;
      this.showCommentForm =  true;
    },
    submitComment() {
      this.axios.post(
        '/comment/addComment', {
          c_order_id: this.comment.c_order_id,
          c_teacher_id: this.comment.c_teacher_id,
          c_user_id: this.comment.c_user_id,
          c_user_name: this.comment.c_user_name,
          c_user_image: this.comment.c_user_image,
          c_content: this.comment.c_content,
          c_user_show: this.$store.state.user.u_name.u_show
        }
      ).then(
        (resp) => {
          if(resp.data != '1') {
            this.$confirm(resp.data)
          }
          else {
            this.$confirm("评价成功！");
            this.showCommentForm = false;
          }
        }
      )
    },
    /**
     * 订单状态为完成授课 ===3的时候，这个字段将修改为===0；
     */
    updateOrderStatus3(row) {
      this.axios.post(
        '/order/updateOrderStatus3',
        {
          o_id: row.o_id,
          o_status: '3',
          o_teacher_id: row.o_teacher_id,
        }
      ).then(
        (resp) => {
          this.$confirm("修改成功");
          this.load();
        }
      )
    },

    /**
     * 取消对接这个老师
     */
    updateOrderTeacher(row) {

      this.axios.post(
        '/order/addOrderTeacher', {
          o_id: row.o_id,
          o_teacher_id : row.o_teacher_id,
          o_teacher_name : '',
          o_teacher_image : '',
          o_status: '0',
          o_user_status : '0',
          o_teacher_status: "0",
        }
      ).then(
        (resp) => {
          this.$confirm("取消成功");
          this.load();
        }
      )

    },

    /**
     * 这里假装调用了支付接口、
     */
    addUserStatus(row) {
      this.axios.post(
        '/order/deposit', {
          o_id: row.o_id,
          o_user_status: '1'
        }
      ).then(
        (resp) => {
          this.$confirm("押金缴纳成功");
          this.load();
        }
      )
    },
    /**
     */
    showTeacherInfo(row) {
      this.$router.push({
        path:`/teacherInfo1/${row.o_teacher_id}`
      })
    },

    /**
     *
     */
    toTeacherInfo(row) {
      this.$router.push({
        path:`/teacherInfo1/${row.s_user_id}`
      })
    },

    deleteSubscribe(row) {
      this.axios.get(
        '/u/sub/deleteSubscribe', {
          params: {id: row.s_id }
        }
      ).then(
        (resp) => {
          this.$confirm("取消成功")
          this.showSubscribeTable = false
        }
      )
    },
    /**
     * 打开预约表单
     */
    openSubscribeTeacher(row) {
      this.currentOrderId = row.o_id;
      this.axios.post(
        '/u/sub/queryOrder',{
          s_order_id : row.o_id,
        }
      ).then(
        (resp) => {
          this.subscribeList = resp.data;
          this.showSubscribeTable = true;
        }
      )
    },
    /**
     * 编辑相关
     * @param row
     */
    toEditOrder(row) {
      console.log(row)
      this.formUpdate.o_id = row.o_id;
      this.formUpdate.o_phone = row.o_phone;
        this.formUpdate.o_price = row.o_price;
        this.formUpdate.o_type = row.o_type;
        this.formUpdate.o_sex = row.o_sex;
        this.formUpdate.o_age = row.o_age;
        this.formUpdate.o_reamke = row.o_reamke;
        this.formUpdate.o_time = row.o_time;
      this.formUpdate.o_name = row.o_name;
      this.formUpdate.o_address = row.o_address;

      this.updateForm = true
    },
    updateOrderSubmit(beforeName) {

        this.axios.post(
          '/order/updateOrder',
          {
            o_id: this.formUpdate.o_id,
            o_phone: this.formUpdate.o_phone,
            o_price: this.formUpdate.o_price,
            o_type: this.formUpdate.o_type,
            o_sex: this.formUpdate.o_sex,
            o_age: this.formUpdate.o_age,
            o_reamke: this.formUpdate.o_reamke,
            o_time: this.formUpdate.o_time,
            o_name:this.formUpdate.o_name,
            o_address:this.formUpdate.o_address,
          }
        ).then(
          (resp) => {
            if (resp.data == 1) {
              this.$confirm("修改成功");
              this.formUpdate.o_id = '';
              this.formUpdate.o_phone = '';
              this.formUpdate.o_price = '';
              this.formUpdate.o_type = '';
              this.formUpdate.o_sex = '';
              this.formUpdate.o_age = '';
              this.formUpdate.o_reamke = '';
              this.formUpdate.o_time = '';
              this.formUpdate.o_name = '';
              this.formUpdate.o_address = '';
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

    deleteOrder(row) {
      this.axios.get(
        '/order/deleteOrder',
        {
          params : {
            id : row.o_id
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
      this.axios.post(
        '/order/queryOrder', {
          o_student_id: this.$store.state.user.u_name.u_id
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
    },
  }
}
</script>

<style scoped>

</style>

