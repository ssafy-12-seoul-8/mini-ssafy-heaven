<template>
  <div class="flex flex-col items-center">
    <div class="flex justify-center mb-24 items-center">
      <BaseLogo />
    </div>
    <div
      class="h-50 flex flex-col gap-10 flex-wrap justify-center items-center md:flex-row md:gap-10"
    >
      <div class="flex flex-col gap-5">
        <BaseInput name="userName" label="아이디" />
        <BaseInput name="password" label="비밀번호" />
      </div>
      <div class="flex flex-col items-center gap-3">
        <BaseButton>로그인</BaseButton>
        <div class="h-50">
          <BaseButton type="white" size="w-24" @click="signUp">가입</BaseButton>
          <BaseButton type="white" size="w-24" @click="loginGuest">게스트</BaseButton>
        </div>
      </div>
    </div>
  </div>
</template>
<!-- 게스트 router 정의 필요 -->
<script setup>
import { memberApi } from '@/apis/members'
import BaseButton from '@/components/BaseButton.vue'
import BaseInput from '@/components/BaseInput.vue'
import BaseLogo from '@/components/BaseLogo.vue'
import { useMemberStore } from '@/stores/members'
import { useRouter } from 'vue-router'

const router = useRouter()
const memberStore = useMemberStore()
const { updateMe } = memberStore

const signUp = () => {
  router.push({ path: '/signUp' })
}

const loginGuest = () => {
  memberApi
    .loginGuest()
    .then((res) => afterGuestLogin(res.data))
    .then(() => moveToMain())
    .catch((err) => console.error(err))
}

const afterGuestLogin = (data) => {
  updateMe(data.id, data.nickname)
}

const moveToMain = () => {
  router.replace('/rooms')
}
</script>
