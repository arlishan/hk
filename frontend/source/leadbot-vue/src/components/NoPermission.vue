<template>
  <div class="no-permission">
    <div class="no-permission-card">
      <div class="no-permission-icon">⚠</div>
      <h3>无权限访问</h3>
      <p>你当前没有访问此模块的权限，请联系管理员开通对应功能。</p>
      <button class="primary-btn" @click="$router.push('/dashboard')">
        返回工作台
      </button>
    </div>
  </div>
</template>

<script setup>
</script>
