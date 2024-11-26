<template>
  <div class="h-5/6 flex flex-col">
    <span v-if="currentGame" class="text-center text-xl">{{ currentGame.title }}</span>
    <div class="grow flex flex-col gap-4 justify-center items-center">
      {{ content }}
      <BaseButton v-if="isBeforeStart && !hasRead" class="z-50" @click="openDescribtion"
        >{{ currentGame.title }} 설명 보기</BaseButton
      >
      <BaseballInput
        v-if="isStart && isTagger"
        :ball-count="condition.ballCount"
        @submit="submitAnswer"
      />
      <RoundStartTimer
        v-if="isRoundStart"
        @start="setStartMember"
        @miss="handleMiss"
        @answer="handleAnswer"
        @try="handleTry"
        :init="timerInit"
      />
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
const timerInit = ref(0)
const roomStore = useRoomStore()
const roomGameStore = useRoomGameStore()
const roomPlayerStore = useRoomPlayerStore()
const baseballStore = useBaseballStore()
const { currentRoom } = storeToRefs(roomStore)
const { currentGame, allRoundOver } = storeToRefs(roomGameStore)
const { currentPlayer, roomPlayers, manager } = storeToRefs(roomPlayerStore)
const {
  isStart,
  isBeforeStart,
  isConfirm,
  isRoundStart,
  condition,
  tagger,
  normalText,
  taggerText,
  nextTurn,
  isAnswer,
  isOver,
  hasTried,
  scorerId,
} = storeToRefs(baseballStore)
const { nextRound } = roomGameStore
const { getTaggerIndex } = roomPlayerStore
const { setNextTurn, missAttempt, clearAnswer } = baseballStore
const isManager = computed(() => manager.value.memberId === currentPlayer.value.memberId)

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

watchEffect(() => {
  if (isConfirm.value && isManager.value) {
    gameStart()
  }
})

watchEffect(() => {
  if (isRoundStart.value && isManager.value) {
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

const gameStart = () => {
  const request = {
    gameType: currentGame.value.title,
  }

  roomSocket.gameStart(currentRoom.value.id, request)
}

const initRound = () => {
  const request = {
    gameType: currentGame.value.title,
  }

  roomSocket.gameRoundStart(currentRoom.value.id, request)

  timerInit.value = 3
}

const setStartMember = () => {
  const taggerIndex = getTaggerIndex(tagger.value.memberId)
  const tempIndex = (taggerIndex + 1) % roomPlayers.value.length
  const turnIndex =
    tempIndex === taggerIndex ? (tempIndex + 1) % roomPlayers.value.length : tempIndex

  setNextTurn(turnIndex, roomPlayers.value[turnIndex].nickname)

  timerInit.value = 8
}

const proceedToNextTurn = () => {
  const taggerIndex = getTaggerIndex(tagger.value.memberId)
  const currentIndex = (nextTurn.value + 1) % roomPlayers.value.length
  const nextTurnIndex =
    currentIndex === taggerIndex ? (currentIndex + 1) % roomPlayers.value.length : currentIndex

  setNextTurn(nextTurnIndex, roomPlayers.value[nextTurnIndex].nickname)

  timerInit.value = 8
}

const handleMiss = () => {
  if (isAnswer.value) {
    const request = {
      memberId: scorerId.value,
      earn: 35,
    }

    roomSocket.score(currentRoom.value.id, request)

    nextRound()

    if (allRoundOver.value) {
      console.log('게임 종료')
      return
    }

    gameStart()
  }

  if (isOver.value) {
    const request = {
      memberId: tagger.value.memberId,
      earn: 35,
    }

    roomSocket.score(currentRoom.value.id, request)

    nextRound()

    if (allRoundOver.value) {
      console.log('게임 종료')
      return
    }

    gameStart()
  }

  if (hasTried.value) {
    timerInit.value = 3

    proceedToNextTurn()
    clearAnswer()

    return
  }

  missAttempt()

  timerInit.value = 0

  setTimeout(() => {
    if (isOver.value) {
      const request = {
        memberId: tagger.value.memberId,
        earn: 35,
      }

      roomSocket.score(currentRoom.value.id, request)

      nextRound()

      if (allRoundOver.value) {
        console.log('게임 종료')
        return
      }

      gameStart()
    }

    proceedToNextTurn()
  }, 1000)
}

const handleAnswer = () => {
  timerInit.value = 3
}

const handleTry = () => {
  timerInit.value = 3
}
</script>
