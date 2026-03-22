<template>
  <ModalShell title="新增跟进记录" @close="$emit('close')">
    <div class="form-group">
      <label>跟进标题</label>
      <input v-model="form.title" type="text" placeholder="如：电话跟进 / 微信回复" />
    </div>
    <div class="form-group">
      <label>跟进说明</label>
      <input v-model="form.desc" type="text" placeholder="请输入跟进内容" />
    </div>
    <button class="primary-btn full" @click="$emit('save', form)">保存记录</button>
  </ModalShell>
</template>

<script setup>
import { reactive } from 'vue'
import ModalShell from './ModalShell.vue'

const form = reactive({
  title: '',
  desc: ''
})

defineEmits(['close', 'save'])
</script>
