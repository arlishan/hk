<template>
  <ModalShell title="机器人规则编辑" @close="$emit('close')">
    <div class="strategy-card">
      <strong>触达时间窗</strong>
      <p>{{ rules?.timeRange || '未配置' }}</p>
    </div>

    <div class="strategy-card">
      <strong>跟进节奏</strong>
      <p>{{ rules?.followRule || '未配置' }}</p>
    </div>

    <div class="strategy-card">
      <strong>行业策略</strong>
      <p>{{ rules?.industryRule || '未配置' }}</p>
    </div>

    <button class="primary-btn full" @click="$emit('edit')">编辑规则</button>
  </ModalShell>
</template>

<script setup>
import ModalShell from './ModalShell.vue'

defineProps({
  rules: {
    type: Object,
    default: () => ({})
  }
})

defineEmits(['close', 'edit'])
</script>
