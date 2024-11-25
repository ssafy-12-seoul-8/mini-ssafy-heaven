<template>
  <button
    class="'cursor-pointer transition-all px-6 py-2 rounded-lg border-b-[4px] hover:brightness-110 hover:-translate-y-[1px] active:border-b-[2px] active:brightness-90 active:translate-y-[2px]'"
    :class="[buttonColor, buttonSize]"
    :style="{ height: '50px' }"
    :disabled="props.disabled"
  >
    <slot></slot>
  </button>
</template>
<script setup>
import { defineProps, computed } from 'vue'

const props = defineProps({
  type: {
    type: String,
    default: 'blue',
  },
  size: {
    type: String,
    default: 'w-48',
  },
  disabled: {
    type: Boolean,
    default: false,
  },
})
// white로 주면 색 변경
const buttonColor = computed(() => {
  if (props.type === 'white') {
    return 'bg-slate-50 text-sky-400'
  }
  return 'bg-sky-400 text-white border-sky-500'
})
// size 입력으로 너비 조절
const buttonSize = computed(() => {
  const validSizes = ['w-16', 'w-24', 'w-32', 'w-48', 'w-64'] // 유효한 너비
  return validSizes.includes(props.size) ? props.size : 'w-48'
})
</script>
