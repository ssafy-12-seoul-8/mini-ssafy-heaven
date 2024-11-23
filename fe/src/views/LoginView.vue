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
          <!-- 게스트로 입장 로직 필요 -->
          <BaseButton type="white" size="w-24">게스트</BaseButton>
        </div>
      </div>
    </div>
  </div>
</template>
<!-- 게스트 router 정의 필요 -->
<script setup>
import BaseButton from '@/components/BaseButton.vue'
import BaseInput from '@/components/BaseInput.vue'
import BaseLogo from '@/components/BaseLogo.vue'
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import { loginApi } from '@/apis/member'

const router = useRouter()
const username = ref('')
const password = ref('')

const signUp = () => {
  router.push({ path: '/signUp' })
}

const login = async () => {
  if (!validateUserName(username.value)) {
    alert('아이디를 입력하세요!')
    return
  }
  if (!validatePassword(password.value)) {
    alert('비밀번호를 입력하세요!')
    return
  }

  const request = {
    username: username.value,
    password: password.value,
  }

  loginApi
    .login(request)
    .then(() => {
      router.push({ path: '/rooms' })
    })
    .catch((err) => {
      alert(err.response.data.message)
      username.value = ''
      password.value = ''
    })
}

const validateUserName = (username) => {
  return !!username
}

const validatePassword = (password) => {
  return !!password
}
</script>
