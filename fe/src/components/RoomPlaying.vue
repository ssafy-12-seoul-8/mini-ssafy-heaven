<template>
  <div class="h-5/6 flex flex-col">
    <span v-if="currentGame" class="text-center text-xl">{{ currentGame.title }}</span>
    <div class="grow flex flex-col gap-4 justify-center items-center">
      {{ content }}
      <BaseballInput
        v-if="isStart && isTagger"
        :ball-count="condition.ballCount"
        @submit="submitAnswer"
      />
      <BaseButton v-if="isBeforeTrial && !hasRead" class="z-50" @click="openDescribtion"
        >{{ currentGame.title }} 설명 보기</BaseButton
      >
    </div>
    <ModalGameDescribe :is-open="isDescriptionOpen" :game="game" @ready="handleReady" />
  </div>
</template>

<script setup>
import { useBaseballStore } from '@/stores/baseball'
import { useRoomGameStore } from '@/stores/roomGames'
import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { storeToRefs } from 'pinia'
import { ref, computed, watchEffect } from 'vue'
import BaseballInput from './BaseballInput.vue'
import { roomSocket } from '@/apis/rooms'
import { useRoomStore } from '@/stores/rooms'
import BaseButton from './BaseButton.vue'
import ModalGameDescribe from './ModalGameDescribe.vue'
import { gameApi } from '@/apis/games'

const isDescriptionOpen = ref(false)
const hasRead = ref(false)
const content = ref()
const isTagger = computed(() => currentPlayer.value.memberId === tagger.value.memberId)
const game = ref({})
const roomStore = useRoomStore()
const roomGameStore = useRoomGameStore()
const roomPlayerStore = useRoomPlayerStore()
const baseballStore = useBaseballStore()
const { currentRoom } = storeToRefs(roomStore)
const { currentGame } = storeToRefs(roomGameStore)
const { currentPlayer } = storeToRefs(roomPlayerStore)
const { isStart, isBeforeTrial, isConfirm, condition, tagger, normalText, taggerText } =
  storeToRefs(baseballStore)

watchEffect(() => {
  if (isConfirm) {
    console.log('라운드 시작')
  }

  if (!currentGame.value) {
    content.value = '게임을 시작합니다!'

    return
  }

  if (currentGame.value.title === '숫자야구') {
    if (!normalText.value) {
      content.value = '게임을 시작합니다!'

      return
    }

    content.value = isTagger.value ? taggerText.value : normalText.value
  }
})

const submitAnswer = (numbers) => {
  const request = {
    gameType: currentGame.value.title,
    answer: numbers,
  }

  roomSocket.gameSetAnswer(currentRoom.value.id, request)
}

const openDescribtion = () => {
  gameApi
    .getById(currentGame.value.gameId)
    .then((res) => (game.value = res.data))
    .then(() => (isDescriptionOpen.value = true))
    .catch((err) => console.error(err))
}

const handleReady = () => {
  const request = {
    gameType: currentGame.value.title,
  }

  roomSocket.gameConfirm(currentRoom.value.id, request)

  hasRead.value = true
  isDescriptionOpen.value = false
  content.value = '다른 플레이어들을 기다리는 중입니다...'
}
</script>
