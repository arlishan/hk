<template>
  <ModalShell :title="lead?.id ? '编辑客户' : '新增客户'" @close="$emit('close')">
    <div class="form-group">
      <label>客户姓名</label>
      <input v-model.trim="form.name" type="text" placeholder="请输入客户姓名" />
    </div>

    <div class="form-group">
      <label>公司名称</label>
      <input v-model.trim="form.company" type="text" placeholder="请输入公司名称" />
    </div>

    <div class="form-group">
      <label>行业</label>
      <input v-model.trim="form.industry" type="text" placeholder="请输入行业" />
    </div>

    <div class="form-group">
      <label>联系方式</label>
      <input v-model.trim="form.phone" type="text" placeholder="请输入联系方式" />
    </div>

    <button class="primary-btn full" @click="handleSave">
      保存修改
    </button>
  </ModalShell>
</template>

<script setup>
import { reactive, watch } from 'vue'
import ModalShell from './ModalShell.vue'

const props = defineProps({
  lead: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['close', 'save'])

const form = reactive({
  name: '',
  company: '',
  industry: '',
  phone: ''
})

function syncForm(value) {
  form.name = value?.name || ''
  form.company = value?.company || ''
  form.industry = value?.industry || ''
  form.phone = value?.phone || ''
}

watch(
  () => props.lead,
  value => {
    syncForm(value)
  },
  { immediate: true, deep: true }
)

function handleSave() {
  emit('save', {
    name: form.name,
    company: form.company,
    industry: form.industry,
    phone: form.phone
  })
}
</script>
