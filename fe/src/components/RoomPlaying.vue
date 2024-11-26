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
      <RoundStartTimer v-if="isRoundStart" @next="setNextTurnMember" />
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
import RoundStartTimer from './RoundStartTimer.vue'

const isDescriptionOpen = ref(false)
const hasRead = ref(false)
const content = ref()
const isTagger = computed(() => currentPlayer.value.memberId === tagger.value.memberId)
const game = ref({})
const turn = ref()
const roomStore = useRoomStore()
const roomGameStore = useRoomGameStore()
const roomPlayerStore = useRoomPlayerStore()
const baseballStore = useBaseballStore()
const { currentRoom } = storeToRefs(roomStore)
const { currentGame } = storeToRefs(roomGameStore)
const { currentPlayer, roomPlayers, manager } = storeToRefs(roomPlayerStore)
const {
  isStart,
  isBeforeTrial,
  isConfirm,
  isRoundStart,
  condition,
  tagger,
  normalText,
  taggerText,
  nextTurn,
} = storeToRefs(baseballStore)
const { getTaggerIndex } = roomPlayerStore
const { setNextTurn } = baseballStore
const isManager = computed(() => manager.value.memberId === currentPlayer.value.memberId)

watchEffect(() => {
  if (isConfirm.value && isManager.value) {
    initRound()
  }
})

watchEffect(() => {
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

watchEffect(() => {
  if (isRoundStart.value) {
    content.value = '라운드를 시작합니다!'

    return
  }

  if (!nextTurn.value) {
    return
  }

  turn.value = roomPlayers.value[nextTurn.value]
  content.value = `${turn.value.nickname}님의 차례입니다. 채팅으로 답을 입력해주세요.`
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
    totalCount: currentRoom.value.totalCount,
  }

  roomSocket.gameConfirm(currentRoom.value.id, request)

  hasRead.value = true
  isDescriptionOpen.value = false
  content.value = '다른 플레이어들을 기다리는 중입니다...'
}

const initRound = () => {
  const request = {
    gameType: currentGame.value.title,
  }

  roomSocket.gameRoundStart(currentRoom.value.id, request)
}

const setNextTurnMember = () => {
  const taggerIndex = getTaggerIndex(tagger.value.memberId)
  const tempIndex = (taggerIndex + 1) % roomPlayers.value.length
  const turnIndex =
    tempIndex === taggerIndex ? (tempIndex + 1) % roomPlayers.value.length : tempIndex

  setNextTurn(turnIndex)
}
</script>
