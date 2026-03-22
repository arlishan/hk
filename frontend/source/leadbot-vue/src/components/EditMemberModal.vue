<template>
  <ModalShell :title="member?.id ? '编辑成员' : '新增成员'" @close="$emit('close')">
    <div class="form-group">
      <label>姓名</label>
      <input v-model="form.name" type="text" placeholder="请输入成员姓名" />
    </div>

    <div class="form-group">
      <label>岗位</label>
      <input v-model="form.role" type="text" placeholder="请输入岗位名称" />
    </div>

    <div class="form-group">
      <label>本周线索数</label>
      <input v-model.number="form.leads" type="number" min="0" placeholder="请输入线索数" />
    </div>

    <div class="form-group">
      <label>头像字母</label>
      <input v-model="form.avatar" type="text" maxlength="1" placeholder="如：L" />
    </div>

    <button class="primary-btn full" @click="handleSave">保存成员</button>
  </ModalShell>
</template>

<script setup>
import { reactive, watch } from 'vue'
import ModalShell from './ModalShell.vue'

const props = defineProps({
  member: {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['close', 'save'])

const form = reactive({
  name: '',
  role: '',
  leads: 0,
  avatar: ''
})

function syncForm(value) {
  form.name = value?.name || ''
  form.role = value?.role || ''
  form.leads = Number(value?.leads || 0)
  form.avatar = value?.avatar || ''
}

watch(
  () => props.member,
  value => {
    syncForm(value)
  },
  { immediate: true }
)

function handleSave() {
  emit('save', {
    name: form.name.trim(),
    role: form.role.trim(),
    leads: Number(form.leads || 0),
    avatar: (form.avatar || '').trim().slice(0, 1).toUpperCase()
  })
}
</script>
