import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '首页', icon: 'dashboard' }
      }
    ]
  },
  {
    path: '/equipment',
    component: Layout,
    redirect: '/equipment/list',
    name: 'Equipment',
    meta: { title: '设备管理', icon: 'equipment' },
    children: [
      {
        path: 'list',
        name: 'EquipmentList',
        component: () => import('@/views/equipment/list'),
        meta: { title: '设备列表' }
      },
      {
        path: 'register',
        name: 'EquipmentRegister',
        component: () => import('@/views/equipment/register'),
        meta: { title: '资产入账' }
      },
      {
        path: 'approval',
        name: 'EquipmentApproval',
        component: () => import('@/views/equipment/approval'),
        meta: { title: '审核管理' }
      }
    ]
  },
  {
    path: '/assetValueAdd',
    component: Layout,
    redirect: '/assetValueAdd/register',
    name: 'AssetValueAdd',
    meta: { title: '资产增值', icon: 'el-icon-coin' },
    children: [
      {
        path: 'register',
        name: 'AssetValueAddRegister',
        component: () => import('@/views/assetValueAdd/register.vue'),
        meta: { title: '资产登记' }
      },
      {
        path: 'approval',
        name: 'AssetValueAddApproval',
        component: () => import('@/views/assetValueAdd/approval.vue'),
        meta: { title: '资产审核' }
      },
      {
        path: 'detail',
        name: 'AssetValueAddDetail',
        component: () => import('@/views/assetValueAdd/detail.vue'),
        meta: { title: '资产增值详情' },
        hidden: true
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router 