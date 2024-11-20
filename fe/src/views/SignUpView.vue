<template>
  <div class="flex flex-col gap-10">    
    <div>
      <BaseInput name="userName" label="아이디" v-model="userName"/>      
      <span v-if="userNameWarnMessage" class="text-red-700">{{ userNameWarnMessage }}</span>
    </div>
    <div>
      <BaseInput name="password" label="비밀번호" :password = "true" v-model="passWord" />
      <span v-if="passWordWarnMessage" class="text-red-700">{{ passWordWarnMessage }}</span>
    </div>
    <div>
      <BaseInput name="passWordCheck" label="비밀번호 확인" :password = "true" v-model="passWordCheck" />
      <span v-if="passWordCheckWarnMessage" class="text-red-700">{{ passWordCheckWarnMessage }}</span>
    </div>
    <div>
      <BaseInput name="nickName" label="닉네임" v-model="nickName" />
      <span v-if="nickNameWarnMessage" class="text-red-700">{{ nickNameWarnMessage }}</span>
    </div>
    <BaseButton @click="submitToGetReady"/>
  </div>
</template>

<script setup>
import BaseButton from '@/components/BaseButton.vue';
import BaseInput from '@/components/BaseInput.vue';
import { ref, onMounted } from 'vue'

const userName = ref('')
const passWord = ref('')
const passWordCheck = ref('')
const nickName = ref('')
const userNameWarnMessage = ref('')
const passWordWarnMessage = ref('')
const passWordCheckWarnMessage = ref('')
const nickNameWarnMessage = ref('')
const memberInfo = ref({ // mock
    type: Object,
    required: true,
})
// getReady 수정 필요
const emit = defineEmits(['getReady'])

onMounted(() => {
  userName.value = memberInfo.value.userName
  passWord.value = memberInfo.value.passWord
  passWordCheck.value = memberInfo.value.passWordCheck
  nickName.value = memberInfo.value.nickName
})

const submitToGetReady = () => {
  const userNameValidated = validateUserName(userName.value)
  const passWordValidated = validatePassWord(passWord.value)
  const passWordCheckValidated = validatePassWordCheck(passWordCheck.value)
  const nickNameValidated = validateNickName(nickName.value)

  if(!userNameValidated || !passWordValidated || !passWordCheckValidated || !nickNameValidated){
      return;
  }

  emit('getReady', userName.value, passWord.value, nickName.value)
}

const validateUserName = (userName) => {
  if(!userName) {
    userNameWarnMessage.value = '아이디가 입력되지 않았습니다!'

    return false
  }

  if(userName.length > 30) {
    userNameWarnMessage.value = '아이디는 30자 이내여야 합니다!'

    return false
  }

  // 중복 확인 구현 필요
  userNameWarnMessage.value = '';
    
  return true
}

const validatePassWord = (passWord) => {
  if (!passWord) {
    passWordWarnMessage.value = '비밀번호가 입력되지 않았습니다!'

    return false
  }

  if(passWord.length > 100) {
    passWordWarnMessage.value = '비밀번호는 100자 이내여야 합니다!'

    return false
  }

  passWordWarnMessage.value = ''

  return true
}

const validatePassWordCheck = (passWordCheck) => {
  if(passWord.value !== passWordCheck) {
    passWordCheckWarnMessage.value = '비밀번호가 다릅니다!'

    return false
  }

  passWordCheckWarnMessage.value = ''

  return true
}

const validateNickName = (nickName) => {
  if(!nickName) {
    nickNameWarnMessage.value = '닉네임이 입력되지 않았습니다!'

    return false
  }

  if(nickName.length > 30) {
    nickNameWarnMessage.value = '닉네임은 30자 이내여야 합니다!'

    return false
  }

  nickNameWarnMessage.value = ''

  return true
}
</script>