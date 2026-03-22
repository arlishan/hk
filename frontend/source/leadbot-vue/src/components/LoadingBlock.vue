<template>
  <div class="state-block">
    <div class="state-spinner"></div>
    <p>{{ text || '加载中...' }}</p>
  </div>
</template>

<script setup>
defineProps({
  text: String
})
</script>
