<template>
  <h1>rooms</h1>
  <BaseButton @click="me">내 정보</BaseButton>
</template>
<script setup>
import BaseButton from '@/components/BaseButton.vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const me = () => {
    router.push({ path: 'me' })
}
</script>