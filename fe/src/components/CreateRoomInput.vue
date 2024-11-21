<template>
  <div class="flex flex-col gap-28 w-full items-center">
    <!-- TODO: 인풋 컴포넌트 구현 후 교체 -->
    <div class="flex flex-col gap-4 w-full items-center">
      <BaseInput name="방 제목" label="방 제목" v-model="title" class="w-5/6 m-auto" />
      <span v-if="titleWarnMessage" class="text-red-700">{{ titleWarnMessage }}</span>
    </div>
    <div class="flex flex-col gap-4 w-full items-center">
      <BaseInput
        name="인원 제한"
        label="인원 제한"
        v-model="capacity"
        number
        class="w-1/2 m-auto"
      />
      <span v-if="capacityWarnMessage" class="text-red-700">{{ capacityWarnMessage }}</span>
    </div>
    <div class="flex gap-8">
      <BaseButton @click="moveBack" type="white"> 목록으로 돌아가기 </BaseButton>
      <BaseButton @click="submitToGetReady"> 다음 </BaseButton>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import BaseInput from './BaseInput.vue'
import BaseButton from './BaseButton.vue'
import router from '@/router'

const title = ref()
const capacity = ref()
const titleWarnMessage = ref()
const capacityWarnMessage = ref()
const { roomInfo } = defineProps({
  roomInfo: {
    type: Object,
    required: true,
  },
})
const emit = defineEmits(['getReady'])

onMounted(() => {
  title.value = roomInfo.title
  capacity.value = roomInfo.capacity
})

const moveBack = () => {
  router.go('/rooms')
}

const submitToGetReady = () => {
  const titleValidated = validateTitle(title.value)
  const capacityValidated = validateCapacity(capacity.value)

  if (!titleValidated || !capacityValidated) {
    return
  }

  emit('getReady', title.value, capacity.value)
}

const validateTitle = (title) => {
  if (!title) {
    titleWarnMessage.value = '방 제목이 입력되지 않았습니다!'

    return false
  }

  if (title.length > 20) {
    titleWarnMessage.value = '방 제목은 20자 이내여야 합니다!'

    return false
  }

  titleWarnMessage.value = ''

  return true
}

const validateCapacity = (capacity) => {
  if (!capacity) {
    capacityWarnMessage.value = '인원 제한이 입력되지 않았습니다!'

    return false
  }

  if (capacity <= 0) {
    capacityWarnMessage.value = '인원 제한은 양수여야 합니다!'

    return false
  }

  if (capacity > 5) {
    capacityWarnMessage.value = '인원은 최대 5명까지 가능합니다!'

    return false
  }

  capacityWarnMessage.value = ''

  return true
}
</script>
