<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="welcome-card">
          <div slot="header" class="clearfix">
            <span>欢迎使用高校资产管理系统</span>
          </div>
          <div class="welcome-content">
            <h2>系统概览</h2>
            <p>高校资产管理系统为您提供完整的设备管理解决方案</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">{{ stats.totalEquipment }}</div>
            <div class="stat-label">设备总数</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">{{ stats.pendingReview }}</div>
            <div class="stat-label">待审核</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">{{ stats.approved }}</div>
            <div class="stat-label">已审核</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>快速操作</span>
          </div>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-button type="primary" icon="el-icon-plus" @click="$router.push('/equipment/register')" style="width: 100%; height: 60px;">
                资产入账
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button type="success" icon="el-icon-document" @click="$router.push('/equipment/list')" style="width: 100%; height: 60px;">
                设备列表
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button type="warning" icon="el-icon-check" @click="$router.push('/equipment/approval')" style="width: 100%; height: 60px;">
                审核管理
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button type="info" icon="el-icon-coin" @click="$router.push('/assetValueAdd/register')" style="width: 100%; height: 60px;">
                资产增值登记
              </el-button>
            </el-col>
            <el-col :span="6" style="margin-top: 20px;">
              <el-button type="success" icon="el-icon-coin" @click="$router.push('/assetValueAdd/approval')" style="width: 100%; height: 60px;">
                资产增值审核
              </el-button>
            </el-col>
            <!-- <el-col :span="6">
              <el-button type="info" icon="el-icon-data-analysis" style="width: 100%; height: 60px;">
                数据统计
              </el-button>
            </el-col> -->
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      stats: {
        totalEquipment: 0,
        pendingReview: 0,
        approved: 0
      }
    }
  },
  mounted() {
    this.loadStats()
  },
  methods: {
    loadStats() {
      // 这里可以调用API获取统计数据
      // 暂时使用模拟数据
      this.stats = {
        totalEquipment: 1250,
        pendingReview: 15,
        approved: 1235
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 20px;
  
  .welcome-card {
    .welcome-content {
      text-align: center;
      padding: 20px;
      
      h2 {
        color: #409EFF;
        margin-bottom: 20px;
      }
      
      p {
        font-size: 16px;
        margin: 10px 0;
        color: #666;
      }
    }
  }
  
  .stat-card {
    .stat-content {
      text-align: center;
      padding: 20px;
      
      .stat-number {
        font-size: 36px;
        font-weight: bold;
        color: #409EFF;
        margin-bottom: 10px;
      }
      
      .stat-label {
        font-size: 16px;
        color: #666;
      }
    }
  }
}
</style> 