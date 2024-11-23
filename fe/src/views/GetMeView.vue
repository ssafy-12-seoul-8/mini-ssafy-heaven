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
import { useRouter, useRoute } from 'vue-router'
import { meApi } from '@/apis/member'

const nickname = ref('')
const score = ref(0)
const router = useRouter()
const params = useRoute()

const userDetail = async () => {
  meApi
    .getDetail(params.id)
    .then(() => {
      nickname.value = params.nickname
      score.value = params.score
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
