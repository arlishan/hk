<template>
  <ModalShell title="修改密码" @close="$emit('close')">
    <div class="form-group">
      <label>旧密码</label>
      <input
        v-model.trim="form.oldPassword"
        type="password"
        placeholder="请输入旧密码"
        autocomplete="current-password"
      />
    </div>

    <div class="form-group">
      <label>新密码</label>
      <input
        v-model.trim="form.newPassword"
        type="password"
        placeholder="请输入新密码"
        autocomplete="new-password"
      />
    </div>

    <div class="form-group">
      <label>确认新密码</label>
      <input
        v-model.trim="form.confirmPassword"
        type="password"
        placeholder="请再次输入新密码"
        autocomplete="new-password"
        @keydown.enter="handleSubmit"
      />
    </div>

    <div v-if="errorText" class="form-error">
      {{ errorText }}
    </div>

    <button class="primary-btn full" @click="handleSubmit">
      确认修改
    </button>
  </ModalShell>
</template>

<script setup>
import { computed, reactive } from 'vue'
import ModalShell from './ModalShell.vue'

const emit = defineEmits(['close', 'save'])

const form = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const errorText = computed(() => {
  if (!form.oldPassword || !form.newPassword || !form.confirmPassword) {
    return ''
  }
  if (form.newPassword.length < 6) {
    return '新密码长度不能少于 6 位'
  }
  if (form.newPassword !== form.confirmPassword) {
    return '两次输入的新密码不一致'
  }
  if (form.oldPassword === form.newPassword) {
    return '新密码不能与旧密码相同'
  }
  return ''
})

function handleSubmit() {
  if (!form.oldPassword || !form.newPassword || !form.confirmPassword) {
    return
  }

  if (errorText.value) {
    return
  }

  emit('save', {
    oldPassword: form.oldPassword,
    newPassword: form.newPassword
  })
}
</script>

<style scoped>
.form-error {
  margin: 4px 0 12px;
  color: #ef4444;
  font-size: 13px;
  line-height: 1.5;
}
</style>
