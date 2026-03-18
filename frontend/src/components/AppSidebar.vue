<template>
  <div class="app-header">
    <div class="left-section">
      <span class="system-title">線上圖書借閱系統</span>
    </div>

    <div class="right-section">
      <div class="user-info">
        <el-avatar :size="38" class="avatar">
          {{ avatarText }}
        </el-avatar>
        <div class="user-meta">
          <div class="user-name">{{ authStore.userName || '訪客' }}</div>
          <div class="user-role">Library User</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const logout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.app-sidebar {
  width: 240px;
  min-height: 100vh;
  background: #111827;
  color: #fff;
  display: flex;
  flex-direction: column;
}

.logo-block {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 22px 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.logo-icon {
  width: 40px;
  height: 40px;
  border-radius: 12px;
  background: #409eff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
}

.logo-title {
  font-size: 16px;
  font-weight: 700;
}

.logo-subtitle {
  font-size: 12px;
  color: #9ca3af;
}

.sidebar-menu {
  border-right: none;
  flex: 1;
}

.sidebar-footer {
  padding: 16px;
}

.logout-btn {
  width: 100%;
}
</style>