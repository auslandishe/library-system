<template>
  <div class="app-header">
    <div class="header-left">
      <div class="brand-block">
        <div class="brand-icon">L</div>
        <div class="brand-text">
          <div class="brand-title">線上圖書借閱系統</div>
          <div class="brand-subtitle">Library Management System</div>
        </div>
      </div>
    </div>

    <div class="header-right">
      <el-button class="nav-btn" @click="router.push('/books')">
        書籍列表
      </el-button>
      <el-button class="nav-btn" @click="router.push('/my-borrows')">
        我的借閱
      </el-button>

      <div class="user-card">
        <el-avatar :size="40" class="avatar">
          {{ avatarText }}
        </el-avatar>

        <div class="user-meta">
          <div class="user-name">{{ authStore.userName || '使用者' }}</div>
          <div class="user-role">Library User</div>
        </div>
      </div>

      <el-button type="danger" plain class="logout-btn" @click="logout">
        登出
      </el-button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const avatarText = computed(() => {
  const name = authStore.userName || 'U'
  return name.slice(0, 1).toUpperCase()
})

const logout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.app-header {
  height: 72px;
  background: #ffffff;
  border-bottom: 1px solid #e5e7eb;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 28px;
  box-sizing: border-box;
  box-shadow: 0 4px 18px rgba(15, 23, 42, 0.04);
}

.header-left {
  display: flex;
  align-items: center;
}

.brand-block {
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand-icon {
  width: 44px;
  height: 44px;
  border-radius: 14px;
  background: linear-gradient(135deg, #409eff, #2563eb);
  color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  font-weight: 700;
  box-shadow: 0 8px 18px rgba(37, 99, 235, 0.22);
}

.brand-text {
  display: flex;
  flex-direction: column;
}

.brand-title {
  font-size: 20px;
  font-weight: 700;
  color: #111827;
  line-height: 1.2;
}

.brand-subtitle {
  font-size: 12px;
  color: #6b7280;
  margin-top: 2px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.nav-btn {
  border-radius: 10px;
}

.user-card {
  display: flex;
  align-items: center;
  width: 150px;
  gap: 10px;
  background: #f8fafc;
  border: 1px solid #e5e7eb;
  border-radius: 14px;
  padding: 8px 12px;
  margin-left: 6px;
}

.avatar {
  background: linear-gradient(135deg, #409eff, #2563eb);
  color: #ffffff;
  font-weight: 700;
}

.user-meta {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: #111827;
  line-height: 1.2;
}

.user-role {
  font-size: 12px;
  color: #6b7280;
  margin-top: 2px;
}

.logout-btn {
  border-radius: 10px;
  margin-left: 4px;
}
</style>