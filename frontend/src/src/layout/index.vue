<template>
  <el-container class="app-wrapper">
    <el-aside width="200px" class="sidebar-container">
      <div class="logo">
        <h2>资产管理系统</h2>
      </div>
      <el-menu
        :default-active="$route.path"
        class="el-menu-vertical"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <el-menu-item index="/dashboard">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        
        <el-submenu index="/equipment">
          <template slot="title">
            <i class="el-icon-s-management"></i>
            <span>资产入账</span>
          </template>
          <el-menu-item index="/equipment/list">设备列表</el-menu-item>
          <el-menu-item index="/equipment/register">资产登记</el-menu-item>
          <el-menu-item index="/equipment/approval">资产入账审核</el-menu-item>
        </el-submenu>
        <el-submenu index="/assetValueAdd">
          <template slot="title">
            <i class="el-icon-coin"></i>
            <span>资产增值</span>
          </template>
          <el-menu-item index="/assetValueAdd/register">资产增值登记</el-menu-item>
          <el-menu-item index="/assetValueAdd/approval">资产增值审核</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header class="app-header">
        <div class="header-left">
          <h3>{{ $route.meta.title || '首页' }}</h3>
        </div>
      </el-header>
      
      <el-main class="app-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'Layout'
}
</script>

<style lang="scss" scoped>
.app-wrapper {
  height: 100vh;
}

.sidebar-container {
  background-color: #304156;
  
  .logo {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #2b2f3a;
    
    h2 {
      color: #fff;
      font-size: 18px;
      margin: 0;
    }
  }
  
  .el-menu-vertical {
    border: none;
  }
}

.app-header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  
  .header-left h3 {
    margin: 0;
    color: #333;
  }
}

.app-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style> 