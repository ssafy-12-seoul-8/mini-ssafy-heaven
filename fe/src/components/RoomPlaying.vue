<template>
  <div class="flex flex-col gap-4 justify-center items-center">
    {{ content }}
  </div>
</template>

<script setup>
import { ref } from 'vue'

const content = ref('게임을 시작합니다!')
</script>
