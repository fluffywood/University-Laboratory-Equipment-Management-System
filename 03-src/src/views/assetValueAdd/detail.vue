<template>
  <el-card>
    <div slot="header">
      <span>资产增值详情</span>
    </div>
    <el-steps :active="approvalStep" finish-status="success" align-center style="margin-bottom: 24px">
      <el-step title="提交申请"></el-step>
      <el-step title="初审"></el-step>
      <el-step title="终审"></el-step>
      <el-step title="流程结束"></el-step>
    </el-steps>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="业务单号">{{ detail.ywdh }}</el-descriptions-item>
      <el-descriptions-item label="设备名称">{{ detail.zcmc }}</el-descriptions-item>
      <el-descriptions-item label="使用单位号">{{ detail.lydwh }}</el-descriptions-item>
      <el-descriptions-item label="使用单位名">{{ detail.lydwm }}</el-descriptions-item>
      <el-descriptions-item label="设备编号区间">{{ detail.zcbhqj }}</el-descriptions-item>
      <el-descriptions-item label="品牌型号">{{ detail.ppxh }}</el-descriptions-item>
      <el-descriptions-item label="增值前金额">{{ detail.zzqje }}</el-descriptions-item>
      <el-descriptions-item label="增值金额">{{ detail.zzje }}</el-descriptions-item>
      <el-descriptions-item label="增值后金额">{{ detail.zzhje }}</el-descriptions-item>
      <el-descriptions-item label="购置日期">{{ detail.ggrq }}</el-descriptions-item>
      <el-descriptions-item label="现状">{{ detail.xz }}</el-descriptions-item>
      <el-descriptions-item label="经费科目">{{ detail.jfkm }}</el-descriptions-item>
      <el-descriptions-item label="发票号">{{ detail.fph }}</el-descriptions-item>
      <el-descriptions-item label="供货商">{{ detail.ghs }}</el-descriptions-item>
      <el-descriptions-item label="存放地编号">{{ detail.cfdbh }}</el-descriptions-item>
      <el-descriptions-item label="存放地名称">{{ detail.cfdmc }}</el-descriptions-item>
      <el-descriptions-item label="使用人编号">{{ detail.syrbh }}</el-descriptions-item>
      <el-descriptions-item label="使用人">{{ detail.syr }}</el-descriptions-item>
      <el-descriptions-item label="经手人">{{ detail.jsr }}</el-descriptions-item>
      <el-descriptions-item label="记帐人">{{ detail.jzr }}</el-descriptions-item>
      <el-descriptions-item label="入账时间">{{ detail.rzrq }}</el-descriptions-item>
      <el-descriptions-item label="备注">{{ detail.bz }}</el-descriptions-item>
      <el-descriptions-item label="审核状态">
        <el-tag :type="getStatusType(detail.shzt)">
          {{ getStatusText(detail.shzt) }}
        </el-tag>
      </el-descriptions-item>
    </el-descriptions>
    <el-button style="margin-top:20px" @click="$router.back()">返回</el-button>
  </el-card>
</template>
<script>
import request from '@/utils/request'
export default {
  name: 'AssetValueAddDetail',
  data() {
    return {
      detail: {}
    }
  },
  computed: {
    approvalStep() {
      if (this.detail.shzt === '2' || this.detail.shzt === 2) {
        return 3
      } else if (this.detail.shzt === '1' || this.detail.shzt === 1) {
        return 2
      } else if (this.detail.shzt === '0' || this.detail.shzt === 0) {
        return 1
      } else {
        return 1
      }
    },
    getStatusType() {
      return status => {
        const statusMap = {
          '0': 'info',
          '1': 'warning',
          '2': 'success',
          '9': 'danger'
        }
        return statusMap[status] || 'info'
      }
    },
    getStatusText() {
      return status => {
        const statusMap = {
          '0': '未审',
          '1': '初审',
          '2': '终审',
          '9': '驳回'
        }
        return statusMap[status] || '未知'
      }
    }
  },
  created() {
    const ywdh = this.$route.query.ywdh
    if (ywdh) {
      request({ url: '/zczzb/detail', method: 'get', params: { ywdh } }).then(res => {
        this.detail = res.data
      })
    }
  }
}
</script> 