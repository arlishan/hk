<template>
  <ModalShell title="编辑渠道预算" @close="$emit('close')">
    <div class="form-group">
      <label>渠道名称</label>
      <input v-model="form.name" type="text" placeholder="请输入渠道名称" />
    </div>

    <div class="form-group">
      <label>预算说明</label>
      <input v-model="form.budget" type="text" placeholder="如：当前预算：¥12,000 / 周" />
    </div>

    <button class="primary-btn full" @click="handleSave">保存预算</button>
  </ModalShell>
</template>

<script setup>
import { reactive, watch } from 'vue'
import ModalShell from './ModalShell.vue'

const props = defineProps({
  channel: {
    type: Object,
    default: () => ({
      id: null,
      name: '',
      budget: ''
    })
  }
})

const emit = defineEmits(['close', 'save'])

const form = reactive({
  id: null,
  name: '',
  budget: ''
})

watch(
  () => props.channel,
  value => {
    form.id = value?.id ?? null
    form.name = value?.name ?? ''
    form.budget = value?.budget ?? ''
  },
  { immediate: true, deep: true }
)

function handleSave() {
  emit('save', {
    id: form.id,
    name: form.name.trim(),
    budget: form.budget.trim()
  })
}
</script>
