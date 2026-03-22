<template>
  <div class="modal-mask" @click.self="$emit('close')">
    <div class="modal-sheet">
      <div class="modal-head">
        <h3>{{ title }}</h3>
        <button class="icon-btn" type="button" @click="$emit('close')">✕</button>
      </div>
      <div class="modal-body">
        <slot />
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    default: '弹窗'
  }
})

defineEmits(['close'])
</script>
