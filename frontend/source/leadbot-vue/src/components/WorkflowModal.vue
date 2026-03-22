<template>
  <ModalShell :title="title" @close="$emit('close')">
    <template v-if="type === 'archive'">
      <div class="step-list">
        <div class="step-item done">
          <div class="step-dot">1</div>
          <div>
            <strong>基础信息录入</strong>
            <p>姓名、公司、行业、联系方式</p>
          </div>
        </div>
        <div class="step-item active">
          <div class="step-dot">2</div>
          <div>
            <strong>客户画像分析</strong>
            <p>规模、需求、预算、角色、决策周期</p>
          </div>
        </div>
        <div class="step-item">
          <div class="step-dot">3</div>
          <div>
            <strong>自动分层打标</strong>
            <p>A / B / C 分类、行业标签、优先级</p>
          </div>
        </div>
        <div class="step-item">
          <div class="step-dot">4</div>
          <div>
            <strong>进入跟进流程</strong>
            <p>分配负责人并设置触达节奏</p>
          </div>
        </div>
      </div>
    </template>

    <template v-else-if="type === 'segment'">
      <div class="segment-detail a">
        <strong>A 类客户</strong>
        <p>高意向、可快速成交，建议 24 小时内持续推进。</p>
      </div>

      <div class="segment-detail b" style="margin-top: 12px;">
        <strong>B 类客户</strong>
        <p>已有沟通基础，建议保持节奏跟进并补充案例。</p>
      </div>

      <div class="segment-detail c" style="margin-top: 12px;">
        <strong>C 类客户</strong>
        <p>新线索或尚未明确需求，适合自动培育和内容触达。</p>
      </div>
    </template>

    <template v-else-if="type === 'strategy'">
      <div class="strategy-card">
        <strong>首轮触达策略</strong>
        <p>优先在 09:00 - 11:30、14:00 - 18:00 自动发起触达。</p>
      </div>
      <div class="strategy-card">
        <strong>跟进频率</strong>
        <p>A 类每天跟进，B 类隔天跟进，C 类每周培育 2 次。</p>
      </div>
      <div class="strategy-card">
        <strong>话术模板</strong>
        <p>当前已启用：教育行业、本地生活、医疗门店。</p>
      </div>
    </template>

    <template v-else>
      <div class="state-block">
        <h4>暂无工作流内容</h4>
        <p>当前工作流类型未配置，请检查传入参数。</p>
      </div>
    </template>
  </ModalShell>
</template>

<script setup>
import { computed } from 'vue'
import ModalShell from './ModalShell.vue'

const props = defineProps({
  type: {
    type: String,
    required: true
  }
})

defineEmits(['close'])

const title = computed(() => {
  if (props.type === 'archive') return '客户建档流程'
  if (props.type === 'segment') return '客户分层页'
  if (props.type === 'strategy') return '机器人策略配置'
  return '工作流'
})
</script>
