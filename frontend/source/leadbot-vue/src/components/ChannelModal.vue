<template>
  <ModalShell title="渠道投放设置" @close="$emit('close')">
    <LoadingBlock v-if="loading" text="正在加载渠道配置..." />

    <ErrorBlock
      v-else-if="error"
      :text="error"
      @retry="$emit('reload')"
    />

    <template v-else>
      <EmptyBlock
        v-if="!channels.length"
        text="当前没有渠道配置数据。"
      />

      <div v-else class="channel-list">
        <div class="strategy-card" v-for="item in channels" :key="item.id">
          <div class="channel-head">
            <strong>{{ item.name }}</strong>
            <button class="mini-btn" @click="$emit('edit', item)">编辑预算</button>
          </div>
          <p>{{ item.budget }}</p>
        </div>
      </div>
    </template>
  </ModalShell>
</template>

<script setup>
import ModalShell from './ModalShell.vue'
import LoadingBlock from './LoadingBlock.vue'
import EmptyBlock from './EmptyBlock.vue'
import ErrorBlock from './ErrorBlock.vue'

defineProps({
  channels: {
    type: Array,
    default: () => []
  },
  loading: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: ''
  }
})

defineEmits(['close', 'edit', 'reload'])
</script>

<style scoped>
.channel-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.channel-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 8px;
}
</style>
