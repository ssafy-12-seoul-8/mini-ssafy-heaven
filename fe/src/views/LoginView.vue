<template>
  <div class="flex flex-col items-center">
    <div class="flex justify-center mb-24 items-center">
      <BaseLogo />
    </div>
    <div
      class="h-50 flex flex-col gap-10 flex-wrap justify-center items-center md:flex-row md:gap-10"
    >
      <div class="flex flex-col gap-5">
        <BaseInput name="username" label="아이디" v-model="username" />
        <BaseInput name="password" password label="비밀번호" v-model="password" />
      </div>
      <div class="flex flex-col items-center gap-3">
        <BaseButton @click="login">로그인</BaseButton>
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
import { ref } from 'vue'

const router = useRouter()
const username = ref('')
const password = ref('')
const memberStore = useMemberStore()
const { updateMe } = memberStore

const signUp = () => {
  router.push({ path: '/signUp' })
}

const login = async () => {
  if (!username.value) {
    alert('아이디를 입력하세요!')
    return
  }
  if (!password.value) {
    alert('비밀번호를 입력하세요!')
    return
  }

  const request = {
    username: username.value,
    password: password.value,
  }

  memberApi
    .login(request)
    .then(() => fetchMe())
    .then(() => {
      router.push({ path: '/rooms' })
    })
    .catch((err) => {
      alert(err.response.data.message)
      username.value = ''
      password.value = ''
    })
}

const loginGuest = () => {
  memberApi
    .loginGuest()
    .then((res) => afterLogin(res.data))
    .then(() => moveToMain())
    .catch((err) => console.error(err))
}

const fetchMe = () => {
  return memberApi.getDetail().then((res) => afterLogin(res.data))
}

const afterLogin = (data) => {
  updateMe(data.nickname)
}

const moveToMain = () => {
  router.replace('/rooms')
}
</script>
