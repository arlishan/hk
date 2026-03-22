<template>
  <div class="state-block error">
    <h4>加载失败</h4>
    <p>{{ text || '请求失败，请稍后重试。' }}</p>
    <button class="primary-btn small" @click="$emit('retry')">重试</button>
  </div>
</template>

<script setup>
defineProps({
  text: String
})

defineEmits(['retry'])
</script>
