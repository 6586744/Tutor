<template>
  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="公告列表"  />
    <el-divider></el-divider>
    <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)">
      <el-table-column prop="b_title" label="标题" >
      </el-table-column>
      <el-table-column prop="b_user_name" label="创建者" >
      </el-table-column>
      <el-table-column prop="time" label="创建时间" width="200">
      </el-table-column>
      <el-table-column label="是否可见" width="160" >
        <template slot-scope="scope">
        <el-button v-if="scope.row.b_show == 1" type="success" disabled size="mini">已启用</el-button>
        <el-button v-else type="info" disabled size="mini">已禁用</el-button>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="230">
        <template slot-scope="scope">
          <el-button v-if="scope.row.b_show == 1" @click="enableBlog(scope.row, 0)" type="text" size="small"><i style="color: #ff0000">禁用</i></el-button>
          <el-button v-else @click="enableBlog(scope.row, 1)" type="text" size="small">启用</el-button>
          <el-button @click="deleteBlog(scope.row)" type="text" size="small">删除</el-button>
          <el-button @click="editBlog(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="previewBlog(scope.row)" type="text" size="small">预览</el-button>
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
    >
    </el-pagination>
  </div>
</template>

<script>

export default {
  name: "AllUser",
  data() {
    return {
      tableData: [],
      // 默认显示第几页
      currentPage:1,
      // 个数选择器（可修改）
      pageSizes:[1,2,3,4,5,6,7,8,9,10],
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount:1,
      // 个数选择器（可修改）
      PageSize:10,
    };
  },

  mounted() {
    this.load();
  },

  methods: {
    deleteBlog(row) {
      this.axios.get(
        '/blog/deleteBlog',
        {
          params : {
            id : row.b_id
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

    //启用禁用
    enableBlog(row, param) {
      this.axios.post(
        '/blog/updateBlog',
        {
          b_id : row.b_id,
          b_show : param,
        }
      ).then(
        (resp) => {
          this.$confirm("修改成功！");
          this.load();
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },

    //编辑
    editBlog(row) {
      this.$router.push({
        //path 与 params不能一起用，
        name:'MdEdit',
        params:{
          b_id : row.b_id
        }
      });
    },
    //评论页面

    previewBlog(row) {
      this.$router.push(
        {
          name: 'PreviewAnnouncement',
          params: {
            b_id : row.b_id
          }
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
        '/blog/getAllBlog',
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
  },
}
</script>

<style scoped>

</style>
