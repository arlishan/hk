<template>
  <ModalShell title="客户详情" @close="$emit('close')">
    <div class="detail-group">
      <h4>{{ lead.name }} · {{ lead.company }}</h4>
      <div class="detail-row">
        <span>行业</span>
        <span>{{ lead.industry || '未填写' }}</span>
      </div>
      <div class="detail-row">
        <span>联系方式</span>
        <span>{{ lead.phone || '未填写' }}</span>
      </div>
      <div class="detail-row">
        <span>客户状态</span>
        <span>{{ lead.status || '未知' }}</span>
      </div>
      <div class="detail-row">
        <span>来源渠道</span>
        <span>{{ lead.source || '未知' }}</span>
      </div>
      <div class="detail-row">
        <span>最近跟进</span>
        <span>{{ lead.lastFollow || '暂无' }}</span>
      </div>
      <div class="detail-row">
        <span>客户等级</span>
        <span>{{ levelLabel }}</span>
      </div>
    </div>

    <div class="detail-group">
      <h4>客户标签</h4>
      <div v-if="normalizedTags.length" class="lead-tags">
        <span class="tag" v-for="tag in normalizedTags" :key="tag">{{ tag }}</span>
      </div>
      <p v-else class="detail-empty">暂无标签</p>
    </div>

    <div class="detail-group">
      <h4>AI 跟进建议</h4>
      <p class="detail-note">{{ lead.note || '暂无建议' }}</p>
    </div>

    <div class="profile-menu">
      <button class="menu-item" @click="$emit('edit', lead.id)">编辑客户</button>
      <button class="menu-item danger-item" @click="$emit('delete', lead.id)">删除客户</button>
      <button class="menu-item" @click="$emit('add-log', lead.id)">新增跟进记录</button>
      <button class="menu-item" @click="$emit('show-logs', lead.id)">查看跟进记录</button>
      <button class="menu-item" @click="$emit('show-archive')">查看建档流程</button>
      <button class="primary-btn full" @click="$emit('follow', lead.id)">标记已跟进</button>
    </div>
  </ModalShell>
</template>

<script setup>
import { computed } from 'vue'
import ModalShell from './ModalShell.vue'

const props = defineProps({
  lead: {
    type: Object,
    required: true
  }
})

defineEmits([
  'close',
  'show-logs',
  'show-archive',
  'follow',
  'edit',
  'delete',
  'add-log'
])

const normalizedTags = computed(() => {
  if (Array.isArray(props.lead.tags)) return props.lead.tags
  if (typeof props.lead.tags === 'string' && props.lead.tags.trim()) {
    return props.lead.tags.split(',').map(item => item.trim()).filter(Boolean)
  }
  return []
})

const levelLabel = computed(() => {
  const level = props.lead.level
  if (level === 'high') return '高意向'
  if (level === 'follow') return '待跟进'
  if (level === 'new') return '新线索'
  return level || '未知'
})
</script>

<style scoped>
.detail-empty {
  margin: 0;
  color: var(--subtext);
  font-size: 14px;
}

.detail-note {
  margin: 0;
  color: var(--subtext);
  line-height: 1.7;
  font-size: 14px;
}
</style>
