<template>
  <div class="page">
    <el-card class="card">
      <template #header>
        <div class="card-header">圖書借閱系統註冊</div>
      </template>

      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="手機號碼" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="請輸入 09 開頭手機號碼" clearable />
        </el-form-item>

        <el-form-item label="密碼" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="8~20 碼" />
        </el-form-item>

        <el-form-item label="姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="請輸入姓名" clearable />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleRegister">註冊</el-button>
          <el-button @click="router.push('/login')">返回登入</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { registerApi } from '@/api/auth'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  phoneNumber: '',
  password: '',
  userName: '',
})

const rules = {
  phoneNumber: [
    { required: true, message: '請輸入手機號碼', trigger: 'blur' },
    { pattern: /^09\d{8}$/, message: '手機號碼格式錯誤', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '請輸入密碼', trigger: 'blur' },
    { min: 8, max: 20, message: '密碼需為 8~20 碼', trigger: 'blur' },
  ],
  userName: [
    { required: true, message: '請輸入使用者名稱', trigger: 'blur' },
    { max: 100, message: '使用者名稱不可超過 100 字', trigger: 'blur' },
  ],
}

const handleRegister = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    await registerApi(form)
    ElMessage.success('註冊成功，請登入')
    router.push('/login')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f7fa;
}
.card {
  width: 460px;
}
.card-header {
  font-size: 18px;
  font-weight: 600;
  text-align: center;
}
</style>