<template>
  <ModalShell title="跟进记录" @close="$emit('close')">
    <div class="timeline-list">
      <template v-if="logs.length">
        <div class="timeline-item" v-for="(item, index) in logs" :key="item.id || index">
          <strong>{{ item.title }} · {{ item.time }}</strong>
          <p>{{ item.desc }}</p>
        </div>
      </template>

      <div v-else class="timeline-item">
        <strong>暂无记录</strong>
        <p>当前客户尚未产生跟进日志。</p>
      </div>
    </div>
  </ModalShell>
</template>

<script setup>
import ModalShell from './ModalShell.vue'

defineProps({
  logs: {
    type: Array,
    default: () => []
  }
})

defineEmits(['close'])
</script>
