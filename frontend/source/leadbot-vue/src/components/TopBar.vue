<template>
  <header class="topbar">
    <div>
      <p class="topbar-sub">LeadBot · AI 获客工作台</p>
      <h1>{{ title }}</h1>
    </div>

    <div class="topbar-actions">
      <button class="icon-btn" type="button" @click="$emit('toggle-theme')">☼</button>
      <button class="avatar-btn" type="button">{{ user?.avatar || 'L' }}</button>
    </div>
  </header>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    default: 'LeadBot'
  },
  user: {
    type: Object,
    default: null
  }
})

defineEmits(['toggle-theme'])
</script>
