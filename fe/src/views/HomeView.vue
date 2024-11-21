<template>
  <div class="flex flex-col items-center gap-40">
    <BaseLogo></BaseLogo>
    <div class="gap-20">
      <BaseButton @click="login">입장하기</BaseButton>
    </div>
  </div>
</template>

<script setup>
import BaseButton from '@/components/BaseButton.vue'
import BaseLogo from '@/components/BaseLogo.vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const login = () => {
  router.push({ path: '/login' })
}
</script>
