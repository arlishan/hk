<template>
  <div class="state-block">
    <h4>暂无数据</h4>
    <p>{{ text || '当前没有可显示的内容。' }}</p>
  </div>
</template>

<script setup>
defineProps({
  text: String
})
</script>
