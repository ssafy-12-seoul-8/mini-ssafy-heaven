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
const isTagger = computed(() => {
  if (!tagger.value) {
    return false
  }

  return currentPlayer.value.memberId === tagger.value.memberId
})
const game = ref({})
const timerInit = ref(0)
const nextGameFlag = ref(false)
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
  initialClose,
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
  if (!normalText.value) {
    content.value = '게임을 시작합니다!'

    return
  }

  content.value = isTagger.value ? taggerText.value : normalText.value
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

watchEffect(() => {
  if (isAnswer.value && scorerId.value === currentPlayer.value.memberId) {
    const request = {
      memberId: scorerId.value,
      earn: 35,
    }

    roomSocket.score(currentRoom.value.id, request)
  }

  isAnswer.value = false
  nextGameFlag.value = true
})

watchEffect(() => {
  if (isOver.value && isTagger.value) {
    const request = {
      memberId: tagger.value.memberId,
      earn: 35,
    }

    roomSocket.score(currentRoom.value.id, request)
  }

  isOver.value = false
  nextGameFlag.value = true
})

const handleMiss = async () => {
  if (nextGameFlag.value) {
    nextRound()

    if (allRoundOver.value) {
      roomSocket.allOver(currentRoom.value.id)

      return
    }

    clearAnswer()
    gameStart()

    return
  }

  if (hasTried.value) {
    timerInit.value = 3

    proceedToNextTurn()
    clearAnswer()

    return
  }

  missAttempt()

  timerInit.value = 0
  initialClose.value = true

  setTimeout(async () => {
    if (nextGameFlag.value) {
      nextRound()

      if (allRoundOver.value) {
        roomSocket.allOver(currentRoom.value.id)

        return
      }

      initialClose.value = false

      gameStart()

      return
    }

    initialClose.value = false

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
