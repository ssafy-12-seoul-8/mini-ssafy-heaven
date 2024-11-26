<template>
  <div class="text-2xl">{{ timer }}</div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const timer = ref(3)
const emit = defineEmits(['next'])

onMounted(() => {
  const timerInterval = setInterval(() => {
    timer.value--

    if (timer.value <= 0) {
      emit('next')
      clearInterval(timerInterval)
    }
  }, 1000)
})
</script>
