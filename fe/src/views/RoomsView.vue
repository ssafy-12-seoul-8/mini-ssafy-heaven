<template>
  <h1>rooms</h1>
  <BaseButton @click="me">내 정보</BaseButton>
  <BaseButton @click="logout">로그아웃</BaseButton>
</template>
<script setup>
import { memberApi } from '@/apis/members'
import BaseButton from '@/components/BaseButton.vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const me = () => {
  router.push({ path: 'me' })
}

const logout = () => {
  memberApi
    .logout()
    .then(() => moveToMain())
    .catch((err) => {
      alert(err.response.data.message)
      moveToMain()
    })
}

const moveToMain = () => {
  router.replace('/login')
}
</script>
