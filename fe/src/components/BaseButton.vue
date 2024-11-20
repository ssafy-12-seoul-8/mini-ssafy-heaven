<template>
  <button :class="[
      'cursor-pointer transition-all px-6 py-2 rounded-lg border-b-[4px] hover:brightness-110 hover:-translate-y-[1px] hover:border-b-[6px] active:border-b-[2px] active:brightness-90 active:translate-y-[2px]',
      buttonColor
    ]" :style="{ height: '50px' }">
    <slot>가입하기</slot>
  </button>
</template>
<script setup>
import { defineProps, computed } from 'vue';

const props = defineProps({
  type: {
    type: String,
    default: 'blue',
  }
})
// white로 주면 색 변경
const buttonColor = computed(() => {
  if (props.type === 'white') {
    return 'bg-white text-blue-400';
  }
  return 'bg-blue-400 text-white border-blue-500'
})
</script>