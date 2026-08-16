<template>
  <div class="equipment-approval">
    <el-card>
      <div slot="header">
        <span>审核管理</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="refreshData">刷新</el-button>
      </div>
      
      <div class="filter-bar">
        <!-- <el-select v-model="statusFilter" placeholder="审核状态" style="width: 150px; margin-right: 10px" @change="handleFilter">
          <el-option label="全部" value=""></el-option>
          <el-option label="未审" value="0"></el-option>
          <el-option label="初审" value="1"></el-option>
          <el-option label="终审" value="2"></el-option>
          <el-option label="驳回" value="9"></el-option>
        </el-select> -->
        
        <el-input
          v-model="searchKeyword"
          placeholder="请输入设备名称或业务单号"
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
        <el-table-column prop="srrq" label="提交时间" width="150">
          <template slot-scope="scope">
            {{ formatDate(scope.row.srrq) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleView(scope.row)">查看</el-button>
            <el-button 
              v-if="scope.row.shzt === '0'" 
              size="mini" 
              type="success" 
              @click="handleFirstReview(scope.row)"
            >
              初审
            </el-button>
            <el-button 
              v-if="scope.row.shzt === '1'" 
              size="mini" 
              type="warning" 
              @click="handleMiddleReview(scope.row)"
            >
              中审
            </el-button>
            <el-button 
              v-if="scope.row.shzt === '2'" 
              size="mini" 
              type="primary" 
              @click="handleFinalReview(scope.row)"
            >
              终审
            </el-button>
            <el-button 
              v-if="scope.row.shzt === '0' || scope.row.shzt === '1' || scope.row.shzt === '2'" 
              size="mini" 
              type="danger" 
              @click="handleReject(scope.row)"
            >
              驳回
            </el-button>
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
      <!-- 审核流程进度条 -->
      <el-steps :active="approvalStep" finish-status="success" align-center style="margin-bottom: 24px">
        <el-step title="老师申请"></el-step>
        <el-step title="单位审核"></el-step>
        <el-step title="院级审核"></el-step>
        <el-step title="归口部门"></el-step>
        <el-step title="流程结束"></el-step>
      </el-steps>
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
        <el-descriptions-item label="提交时间">{{ formatDate(currentItem.srrq) }}</el-descriptions-item>
      </el-descriptions>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
    
    <!-- 审核对话框 -->
    <el-dialog :title="approvalTitle" :visible.sync="approvalDialogVisible" width="40%">
      <el-form :model="approvalForm" label-width="80px">
        <el-form-item label="审核意见">
          <el-input
            v-model="approvalForm.shyj"
            type="textarea"
            :rows="4"
            placeholder="请输入审核意见"
          ></el-input>
        </el-form-item>
        <el-form-item label="审核人">
          <el-input v-model="approvalForm.shr" placeholder="请输入审核人姓名"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="approvalDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitApproval" :loading="approvalLoading">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'
import moment from 'moment'

export default {
  name: 'EquipmentApproval',
  data() {
    return {
      loading: false,
      approvalLoading: false,
      searchKeyword: '',
      statusFilter: '',
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      approvalDialogVisible: false,
      currentItem: {},
      approvalForm: {
        ywdh: '',
        shyj: '',
        shr: ''
      },
      approvalType: '' // 'first', 'middle', 'final', 'reject'
    }
  },
  computed: {
    approvalTitle() {
      const titleMap = {
        'first': '初审',
        'middle': '中审',
        'final': '终审',
        'reject': '驳回'
      }
      return titleMap[this.approvalType] || '审核'
    },
    approvalStep() {
      // 0-未审, 1-初审, 2-中审, 3-终审, 9-驳回
      // 0:老师申请, 1:单位审核, 2:院级审核, 3:归口部门, 4:流程结束
      if (this.currentItem.shzt === '3' || this.currentItem.shzt === 3) {
        return 4 // 终审通过，流程结束
      } else if (this.currentItem.shzt === '2' || this.currentItem.shzt === 2) {
        return 3 // 归口部门
      } else if (this.currentItem.shzt === '1' || this.currentItem.shzt === 1) {
        return 2 // 院级审核
      } else if (this.currentItem.shzt === '0' || this.currentItem.shzt === 0) {
        return 1 // 单位审核
      } else {
        return 1 // 默认显示到单位审核
      }
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
            keyword: this.searchKeyword,
            status: this.statusFilter
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
    
    handleFilter() {
      this.currentPage = 1
      this.fetchData()
    },
    
    clearSearch() {
      this.searchKeyword = ''
      this.statusFilter = ''
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
    
    handleFirstReview(row) {
      this.approvalType = 'first'
      this.approvalForm.ywdh = row.ywdh
      this.approvalForm.shyj = ''
      this.approvalForm.shr = ''
      this.approvalDialogVisible = true
    },
    handleMiddleReview(row) {
      this.approvalType = 'middle'
      this.approvalForm.ywdh = row.ywdh
      this.approvalForm.shyj = ''
      this.approvalForm.shr = ''
      this.approvalDialogVisible = true
    },
    handleFinalReview(row) {
      this.approvalType = 'final'
      this.approvalForm.ywdh = row.ywdh
      this.approvalForm.shyj = ''
      this.approvalForm.shr = ''
      this.approvalDialogVisible = true
    },
    
    handleReject(row) {
      this.approvalType = 'reject'
      this.approvalForm.ywdh = row.ywdh
      this.approvalForm.shyj = ''
      this.approvalForm.shr = ''
      this.approvalDialogVisible = true
    },
    
    async submitApproval() {
      if (!this.approvalForm.shyj.trim()) {
        this.$message.warning('请输入审核意见')
        return
      }
      
      if (!this.approvalForm.shr.trim()) {
        this.$message.warning('请输入审核人姓名')
        return
      }
      
      this.approvalLoading = true
      try {
        let url = ''
        const params = {
          ywdh: this.approvalForm.ywdh,
          shyj: this.approvalForm.shyj,
          shr: this.approvalForm.shr
        }
        
        switch (this.approvalType) {
          case 'first':
            url = '/equipment/first-review'
            break
          case 'middle':
            url = '/equipment/middle-review'
            break
          case 'final':
            url = '/equipment/final-review'
            break
          case 'reject':
            url = '/equipment/reject'
            break
        }
        
        await request({
          url: url,
          method: 'post',
          params: params
        })
        
        this.$message.success('审核完成')
        this.approvalDialogVisible = false
        this.fetchData()
      } catch (error) {
        console.error('审核失败:', error)
        this.$message.error('审核失败')
      } finally {
        this.approvalLoading = false
      }
    },
    
    refreshData() {
      this.fetchData()
    },
    
    getStatusType(status) {
      const statusMap = {
        '0': 'info',
        '1': 'warning',
        '2': 'primary',
        '3': 'success',
        '9': 'danger'
      }
      return statusMap[status] || 'info'
    },
    
    getStatusText(status) {
      const statusMap = {
        '0': '未审',
        '1': '初审',
        '2': '中审',
        '3': '终审',
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
.equipment-approval {
  .filter-bar {
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