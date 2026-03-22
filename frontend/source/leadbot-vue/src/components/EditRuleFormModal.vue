<template>
  <ModalShell title="编辑机器人规则" @close="$emit('close')">
    <div class="form-group">
      <label>触达时间窗</label>
      <input v-model="form.timeRange" type="text" placeholder="如：工作日 09:00 - 11:30、14:00 - 18:00" />
    </div>

    <div class="form-group">
      <label>跟进节奏</label>
      <input v-model="form.followRule" type="text" placeholder="如：A 类每天；B 类隔天；C 类每周 2 次" />
    </div>

    <div class="form-group">
      <label>行业策略</label>
      <input v-model="form.industryRule" type="text" placeholder="如：教育优先推演示，本地生活优先推到店引流" />
    </div>

    <button class="primary-btn full" @click="handleSave">保存规则</button>
  </ModalShell>
</template>

<script setup>
import { reactive, watch } from 'vue'
import ModalShell from './ModalShell.vue'

const props = defineProps({
  rule: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['close', 'save'])

const form = reactive({
  timeRange: '',
  followRule: '',
  industryRule: ''
})

watch(
  () => props.rule,
  (val) => {
    form.timeRange = val?.timeRange || ''
    form.followRule = val?.followRule || ''
    form.industryRule = val?.industryRule || ''
  },
  { immediate: true, deep: true }
)

function handleSave() {
  emit('save', {
    timeRange: form.timeRange,
    followRule: form.followRule,
    industryRule: form.industryRule
  })
}
</script>
