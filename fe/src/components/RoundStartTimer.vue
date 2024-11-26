<template>
  <div class="text-2xl">{{ timer }}</div>
</template>

<script setup>
import { useBaseballStore } from '@/stores/baseball'
import { storeToRefs } from 'pinia'
import { ref, onMounted, watch } from 'vue'

const { init } = defineProps({
  init: {
    type: [Number, String],
    default: 0,
  },
})
const timer = ref()
const interval = ref()
const baseballStore = useBaseballStore()
const { hasTried } = storeToRefs(baseballStore)
const { openForFirstTrial } = baseballStore
const emit = defineEmits(['start', 'miss', 'answer', 'try'])

onMounted(() => {
  timer.value = 3

  const timerInterval = setInterval(() => {
    timer.value--

    if (timer.value <= 0) {
      openForFirstTrial()
      emit('start')
      clearInterval(timerInterval)
    }
  }, 1000)
})

watch(hasTried, (tried) => {
  if (tried) {
    emit('try')
    clearInterval(interval.value)
  }
})

watch(
  () => init,
  (timerInit) => {
    if (timerInit == 0) {
      return
    }

    timer.value = timerInit

    interval.value = setInterval(() => {
      timer.value--

      if (timer.value <= 0) {
        emit('miss')
        clearInterval(interval.value)
      }
    }, 1000)
  },
)
</script>
