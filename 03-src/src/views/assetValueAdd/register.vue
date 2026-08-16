<template>
  <el-card>
    <div slot="header">
      <span>资产增值登记</span>
    </div>
    <el-form :model="form" :rules="rules" ref="form" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="使用单位号" prop="lydwh">
            <el-input v-model="form.lydwh" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="使用单位名" prop="lydwm">
            <el-input v-model="form.lydwm" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备编号区间" prop="zcbhqj">
            <el-input v-model="form.zcbhqj" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备名称" prop="zcmc">
            <el-input v-model="form.zcmc" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="品牌型号" prop="ppxh">
            <el-input v-model="form.ppxh" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="增值前金额" prop="zzqje">
            <el-input-number v-model="form.zzqje" :min="0" :precision="2"  />
            <span style="margin-left: 8px;">元</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="增值金额" prop="zzje">
            <el-input-number v-model="form.zzje" :min="0" :precision="2" @change="calcZzhje" />
            <span style="margin-left: 8px;">元</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="增值后金额" prop="zzhje">
            <el-input-number v-model="form.zzhje" :min="0" :precision="2" :disabled="true" />
            <span style="margin-left: 8px;">元</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="购置日期" prop="ggrq">
            <el-date-picker v-model="form.ggrq" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="现状" prop="xz">
            <el-select v-model="form.xz" placeholder="请选择现状">
              <el-option label="在用" value="1" />
              <el-option label="闲置" value="2" />
              <el-option label="待修" value="3" />
              <el-option label="待报废" value="4" />
              <el-option label="丢失" value="5" />
              <el-option label="报废" value="6" />
              <el-option label="出售" value="7" />
              <el-option label="其它" value="9" />
              <el-option label="调入" value="A" />
              <el-option label="转入" value="B" />
              <el-option label="转出" value="C" />
              <el-option label="注销" value="D" />
              <el-option label="盘亏" value="E" />
              <el-option label="调剂" value="F" />
              <el-option label="对外捐赠" value="G" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="经费科目" prop="jfkm">
            <el-select v-model="form.jfkm" placeholder="请选择经费科目">
              <el-option label="教学" value="1" />
              <el-option label="科研" value="2" />
              <el-option label="基建" value="3" />
              <el-option label="自筹经费" value="4" />
              <el-option label="世界银行贷款" value="5" />
              <el-option label="捐增" value="6" />
              <el-option label="其它" value="9" />
              <el-option label="研究生" value="A" />
              <el-option label="贷款配套费" value="B" />
              <el-option label="行政事业费" value="C" />
              <el-option label="211经费" value="D" />
              <el-option label="十五投资" value="E" />
              <el-option label="985经费" value="F" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发票号" prop="fph">
            <el-input v-model="form.fph" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="供货商" prop="ghs">
            <el-input v-model="form.ghs" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="存放地编号" prop="cfdbh">
            <el-input v-model="form.cfdbh" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="存放地名称" prop="cfdmc">
            <el-input v-model="form.cfdmc" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="使用人编号" prop="syrbh">
            <el-input v-model="form.syrbh" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="使用人" prop="syr">
            <el-input v-model="form.syr" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="经手人" prop="jsr">
            <el-input v-model="form.jsr" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="记帐人" prop="jzr">
            <el-input v-model="form.jzr" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入账时间" prop="rzrq">
            <el-date-picker v-model="form.rzrq" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="bz">
            <el-input v-model="form.bz" type="textarea" :rows="2" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">提交审核</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import request from '@/utils/request'
export default {
  name: 'AssetValueAddRegister',
  data() {
    return {
      loading: false,
      form: {
        lydwh: '', lydwm: '', zcbhqj: '', zcmc: '', ppxh: '', zzqje: null, zzje: null, zzhje: null,
        ggrq: '', xz: '', jfkm: '', fph: '', ghs: '', cfdbh: '', cfdmc: '', syrbh: '', syr: '', jsr: '',
        jzr: '', rzrq: '', bz: ''
      },
      rules: {
        lydwh: [{ required: true, message: '请输入使用单位号', trigger: 'blur' }],
        zcmc: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
        ppxh: [{ required: true, message: '请输入品牌型号', trigger: 'blur' }],
        zzqje: [{ required: true, message: '请输入增值前金额', trigger: 'blur' }],
        zzje: [{ required: true, message: '请输入增值金额', trigger: 'blur' }],
        zzhje: [{ required: true, message: '请输入增值后金额', trigger: 'blur' }],
        ggrq: [{ required: true, message: '请选择购置日期', trigger: 'change' }],
        xz: [{ required: true, message: '请选择现状', trigger: 'change' }],
        jfkm: [{ required: true, message: '请选择经费科目', trigger: 'change' }],
        syr: [{ required: true, message: '请输入使用人', trigger: 'blur' }],
        jsr: [{ required: true, message: '请输入经手人', trigger: 'blur' }],
        jzr: [{ required: true, message: '请输入记帐人', trigger: 'blur' }],
        rzrq: [{ required: true, message: '请选择入账时间', trigger: 'change' }],
      }
    }
  },
  methods: {
    calcZzhje() {
      if (this.form.zzqje != null && this.form.zzje != null) {
        this.form.zzhje = Number(this.form.zzqje) + Number(this.form.zzje)
      } else {
        this.form.zzhje = null
      }
    },
    submitForm() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        this.loading = true
        try {
          const response = await request({
            url: '/zczzb/submit',
            method: 'post',
            data: { ...this.form }
          })
          this.$message.success(response.message || '提交成功')
          this.resetForm()
        } catch (e) {
          this.$message.error('提交失败')
        } finally {
          this.loading = false
        }
      })
    },
    resetForm() {
      this.$refs.form.resetFields()
    }
  }
}
</script> 