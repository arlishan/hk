<template>
  <div class="toast" :class="{ show: visible }">
    {{ text }}
  </div>
</template>

<script setup>
defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  text: {
    type: String,
    default: ''
  }
})
</script>
