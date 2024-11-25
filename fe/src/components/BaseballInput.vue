<template>
  <div class="flex flex-col justify-center items-center gap-4">
    <div v-if="failMessage" class="text-red-500 text-md">{{ failMessage }}</div>
    <div class="z-50">
      <BaseInput class="z-50" number v-model="first" label="" name="" />
      <BaseInput class="z-50" number v-model="second" label="" name="" />
      <BaseInput class="z-50" number v-model="third" label="" name="" />
      <BaseInput class="z-50" v-if="ballCount == 4" number v-model="fourth" />
    </div>
    <BaseButton @click="submitAnswer" class="z-50">숫자 제출</BaseButton>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import BaseInput from './BaseInput.vue'
import BaseButton from './BaseButton.vue'

const first = ref(0)
const second = ref(0)
const third = ref(0)
const fourth = ref(0)
const failMessage = ref()
const { ballCount } = defineProps({
  ballCount: {
    type: Number,
    required: true,
  },
})

const emit = defineEmits(['submit'])

const submitAnswer = () => {
  if (!validateNumbers()) {
    return
  }

  const numbers = first.value.toString() + second.value.toString() + third.value.toString()

  if (ballCount == 4) {
    numbers.concat(fourth.value.toString())
  }

  emit('submit', numbers)
}

const validateNumbers = () => {
  if (
    !validateNumber(first.value) ||
    !validateNumber(second.value) ||
    !validateNumber(third.value)
  ) {
    return false
  }

  if (fourth.value && !validateNumber(fourth.value)) {
    return false
  }

  failMessage.value = ''
  return true
}

const validateNumber = (number) => {
  if (number <= 0) {
    failMessage.value = '숫자는 양수여야합니다!'

    return false
  }

  if (number > 10) {
    failMessage.value = '한 자리수 숫자를 입력해주세요!'

    return false
  }

  return true
}
</script>
