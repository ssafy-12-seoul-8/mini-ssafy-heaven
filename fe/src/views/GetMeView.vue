<template>
  <div class="flex flex-col items-center gap-10">
    <div>닉네임 : {{ nickname }}</div>
    <div>점수 : {{ score }}</div>
    <BaseButton @click="goBack">돌아가기</BaseButton>
  </div>
</template>
<script setup>
import BaseButton from '@/components/BaseButton.vue'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { memberApi } from '@/apis/members'

const nickname = ref('')
const score = ref(0)
const router = useRouter()

const userDetail = () => {
  memberApi
    .getDetail()
    .then((response) => {
      const data = response.data
      nickname.value = data.nickname
      score.value = data.score
    })
    .catch((err) => {
      alert(err.response.data.message)
    })
}

const goBack = () => {
  router.push('/rooms')
}

onMounted(() => {
  userDetail()
})
</script>
