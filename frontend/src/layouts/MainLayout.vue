<template>
  <div class="layout-shell">
    <!-- 多寫MainLayout保留未來添加Sidebar擴充系統的空間 -->
    <!-- 未來能擴充成具rbac架構的圖書管理系統 不同的用戶擁有不同的權限訪問頁面 -->
    <div class="main-area">
      <AppHeader />
      <div class="page-content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup>
import AppHeader from '@/components/AppHeader.vue'
</script>

<style scoped>
.main-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.page-content {
  flex: 1;
  padding: 24px;
  box-sizing: border-box;
}
</style>