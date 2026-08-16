<template>
  <div class="equipment-list">
    <el-card>
      <div slot="header">
        <span>设备列表</span>
        <el-button style="float: right; margin-left: 10px;" type="success" icon="el-icon-download" @click="exportExcel">导出Excel</el-button>
        <el-button style="float: right; padding: 3px 0" type="text" @click="refreshData">刷新</el-button>
      </div>
      
      <div class="search-bar">
        <el-input
          v-model="searchKeyword"
          placeholder="请输入设备名称、业务单号或使用单位名"
          style="width: 300px; margin-right: 10px"
          @keyup.enter.native="handleSearch"
        >
          <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
        </el-input>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button @click="clearSearch">清空</el-button>
      </div>
      
      <el-table
        :data="tableData"
        v-loading="loading"
        style="width: 100%"
        border
      >
        <el-table-column prop="ywdh" label="业务单号" width="120"></el-table-column>
        <el-table-column prop="zcmc" label="设备名称" width="150"></el-table-column>
        <el-table-column prop="lydwm" label="使用单位" width="120"></el-table-column>
        <el-table-column prop="ppxh" label="品牌型号" width="120"></el-table-column>
        <el-table-column prop="je" label="金额(元)" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.je }}
          </template>
        </el-table-column>
        <el-table-column prop="shzt" label="审核状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.shzt)">
              {{ getStatusText(scope.row.shzt) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="srrq" label="输入日期" width="150">
          <template slot-scope="scope">
            {{ formatDate(scope.row.srrq) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleView(scope.row)">查看</el-button>
            <!-- <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button> -->
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-card>
    
    <!-- 查看详情对话框 -->
    <el-dialog title="设备详情" :visible.sync="dialogVisible" width="60%">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="业务单号">{{ currentItem.ywdh }}</el-descriptions-item>
        <el-descriptions-item label="设备名称">{{ currentItem.zcmc }}</el-descriptions-item>
        <el-descriptions-item label="使用单位号">{{ currentItem.lydwh }}</el-descriptions-item>
        <el-descriptions-item label="使用单位名">{{ currentItem.lydwm }}</el-descriptions-item>
        <el-descriptions-item label="分类号">{{ currentItem.zcflh }}</el-descriptions-item>
        <el-descriptions-item label="品牌型号">{{ currentItem.ppxh }}</el-descriptions-item>
        <el-descriptions-item label="规格">{{ currentItem.gg }}</el-descriptions-item>
        <el-descriptions-item label="数量">{{ currentItem.sl }}</el-descriptions-item>
        <el-descriptions-item label="单价(元)">{{ currentItem.dj }}</el-descriptions-item>
        <el-descriptions-item label="金额(元)">{{ currentItem.je }}</el-descriptions-item>
        <el-descriptions-item label="厂家">{{ currentItem.cj }}</el-descriptions-item>
        <el-descriptions-item label="使用人">{{ currentItem.syr }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">
          <el-tag :type="getStatusType(currentItem.shzt)">
            {{ getStatusText(currentItem.shzt) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="输入日期">{{ formatDate(currentItem.srrq) }}</el-descriptions-item>
      </el-descriptions>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'
import moment from 'moment'

export default {
  name: 'EquipmentList',
  data() {
    return {
      loading: false,
      searchKeyword: '',
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      currentItem: {}
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      this.loading = true
      try {
        const response = await request({
          url: '/equipment/page',
          method: 'get',
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            keyword: this.searchKeyword
          }
        })
        
        this.tableData = response.data.records
        this.total = response.data.total
      } catch (error) {
        console.error('获取数据失败:', error)
        this.$message.error('获取数据失败')
      } finally {
        this.loading = false
      }
    },
    
    handleSearch() {
      this.currentPage = 1
      this.fetchData()
    },
    
    clearSearch() {
      this.searchKeyword = ''
      this.handleSearch()
    },
    
    handleSizeChange(val) {
      this.pageSize = val
      this.fetchData()
    },
    
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchData()
    },
    
    handleView(row) {
      this.currentItem = row
      this.dialogVisible = true
    },
    
    handleEdit(row) {
      this.$router.push(`/equipment/edit/${row.id}`)
    },
    
    async handleDelete(row) {
      try {
        await this.$confirm('确认删除该设备记录吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await request({
          url: `/equipment/${row.id}`,
          method: 'delete'
        })
        
        this.$message.success('删除成功')
        this.fetchData()
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除失败:', error)
          this.$message.error('删除失败')
        }
      }
    },
    
    refreshData() {
      this.fetchData()
    },

    exportExcel() {
      // 导出所有符合当前搜索条件的数据
      const keyword = this.searchKeyword || '';
      window.open(`/api/equipment/export?keyword=${encodeURIComponent(keyword)}`);
    },
    
    getStatusType(status) {
      const statusMap = {
        '0': 'info',
        '1': 'warning',
        '2': 'success',
        '9': 'danger'
      }
      return statusMap[status] || 'info'
    },
    
    getStatusText(status) {
      const statusMap = {
        '0': '未审',
        '1': '初审',
        '2': '终审',
        '9': '驳回'
      }
      return statusMap[status] || '未知'
    },
    
    formatDate(date) {
      if (!date) return ''
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    }
  }
}
</script>

<style lang="scss" scoped>
.equipment-list {
  .search-bar {
    margin-bottom: 20px;
  }
  
  .pagination {
    margin-top: 20px;
    text-align: right;
  }
  
  .el-table {
    margin-top: 20px;
  }
}
</style> 