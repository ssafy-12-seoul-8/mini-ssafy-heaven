<template>
  <div class="flex flex-col items-center gap-10">
    <div>
      <BaseInput name="username" label="아이디" v-model="username" />
      <span v-if="userNameWarnMessage" class="text-red-700">{{ userNameWarnMessage }}</span>
    </div>
    <div>
      <BaseInput name="password" label="비밀번호" password v-model="password" />
      <span v-if="passWordWarnMessage" class="text-red-700">{{ passWordWarnMessage }}</span>
    </div>
    <div>
      <BaseInput name="passWordCheck" label="비밀번호 확인" password v-model="passWordCheck" />
      <span v-if="passWordCheckWarnMessage" class="text-red-700">{{
        passWordCheckWarnMessage
      }}</span>
    </div>
    <div>
      <BaseInput name="nickname" label="닉네임" v-model="nickname" />
      <span v-if="nickNameWarnMessage" class="text-red-700">{{ nickNameWarnMessage }}</span>
    </div>
    <div class="flex gap-4">
      <BaseButton @click="submitToGetReady">가입하기</BaseButton>
      <BaseButton @click="goBack">뒤로가기</BaseButton>
    </div>
  </div>
</template>

<script setup>
import { memberApi } from '@/apis/members'
import BaseButton from '@/components/BaseButton.vue'
import BaseInput from '@/components/BaseInput.vue'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const username = ref('')
const password = ref('')
const passWordCheck = ref('')
const nickname = ref('')
const userNameWarnMessage = ref('')
const passWordWarnMessage = ref('')
const passWordCheckWarnMessage = ref('')
const nickNameWarnMessage = ref('')

const memberInfo = ref({
  // mock
  type: Object,
  required: true,
})

onMounted(() => {
  username.value = memberInfo.value.username
  password.value = memberInfo.value.password
  passWordCheck.value = memberInfo.value.passWordCheck
  nickname.value = memberInfo.value.nickname
})

const submitToGetReady = async () => {
  const userNameValidated = validateUserName(username.value)
  const passWordValidated = validatePassWord(password.value)
  const passWordCheckValidated = validatePassWordCheck(passWordCheck.value)
  const nickNameValidated = validateNickName(nickname.value)

  if (!userNameValidated || !passWordValidated || !passWordCheckValidated || !nickNameValidated) {
    return
  }

  const request = {
    username: username.value,
    password: password.value,
    nickname: nickname.value,
  }

  memberApi
    .signup(request)
    .then((res) => {
      addMember(res.data.id)
      alert(`환영합니다 ${username.value}님! 해당 정보로 로그인을 진행해주세요!`)
      router.push({ path: '/login' })
    })
    .catch((err) => {
      alert(err.response.data.message)
    })
}

const addMember = (id) => {
  memberInfo.value.id = id
}

const goBack = () => {
  router.replace('/login')
}

const validateUserName = (username) => {
  if (!username) {
    userNameWarnMessage.value = '아이디가 입력되지 않았습니다!'

    return false
  }

  if (username.length > 30) {
    userNameWarnMessage.value = '아이디는 30자 이내여야 합니다!'

    return false
  }

  // 중복 확인 구현 필요
  userNameWarnMessage.value = ''

  return true
}

const validatePassWord = (password) => {
  if (!password) {
    passWordWarnMessage.value = '비밀번호가 입력되지 않았습니다!'

    return false
  }

  if (password.length > 100) {
    passWordWarnMessage.value = '비밀번호는 100자 이내여야 합니다!'

    return false
  }

  passWordWarnMessage.value = ''

  return true
}

const validatePassWordCheck = (passWordCheck) => {
  if (password.value !== passWordCheck) {
    passWordCheckWarnMessage.value = '비밀번호가 다릅니다!'

    return false
  }

  passWordCheckWarnMessage.value = ''

  return true
}

const validateNickName = (nickname) => {
  if (!nickname) {
    nickNameWarnMessage.value = '닉네임이 입력되지 않았습니다!'

    return false
  }

  if (nickname.length > 30) {
    nickNameWarnMessage.value = '닉네임은 30자 이내여야 합니다!'

    return false
  }

  nickNameWarnMessage.value = ''

  return true
}
</script>
