import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { GameMessageType } from '@/enums/GameType'

export const useBaseballStore = defineStore('baseball', () => {
  // state
  const condition = ref()
  const currentHit = ref(1)
  const tagger = ref()
  const normalText = ref()
  const taggerText = ref()
  const stage = ref(GameMessageType.BEFORE_START)
  const nextTurn = ref()
  const isAnswer = ref(false)
  const isOver = ref(false)
  const hasTried = ref(false)
  const initialClose = ref(true)
  const scorerId = ref()

  // getters
  const isBeforeStart = computed(() => stage.value === GameMessageType.BEFORE_START)
  const isConfirm = computed(() => stage.value === GameMessageType.CONFIRM)
  const isStart = computed(() => stage.value === GameMessageType.START)
  const isRoundStart = computed(() => stage.value === GameMessageType.ROUND_START)

  // action
  const gameJustStart = () => {
    stage.value = GameMessageType.BEFORE_START
    normalText.value = '게임을 시작합니다!'
    taggerText.value = normalText.value
  }

  const updateCondition = (data, member) => {
    currentHit.value = 1
    isAnswer.value = false
    isOver.value = false
    hasTried.value = false
    initialClose.value = true
    scorerId.value = null
    stage.value = GameMessageType.START
    condition.value = data
    tagger.value = member
    normalText.value = `술래는 ${member.nickname}입니다! 술래가 숫자를 선정하고 있습니다.`
    taggerText.value = '당신은 술래입니다! 숫자를 선정해주세요.'
  }

  const setAnswer = (data) => {
    stage.value = GameMessageType.ROUND_START
    condition.value = data
    normalText.value = '숫자를 정했습니다! 게임을 시작합니다!'
    taggerText.value = normalText.value
  }

  const incrementConfirm = (totalCount, count) => {
    if (totalCount === count) {
      stage.value = GameMessageType.CONFIRM
    }
  }

  const stageToRoundStart = () => {
    if (stage.value !== GameMessageType.ROUND_START) {
      stage.value = GameMessageType.ROUND_START
    }
  }

  const openForFirstTrial = () => {
    initialClose.value = false
  }

  const setNextTurn = (index, nickname) => {
    nextTurn.value = index
    normalText.value = `${nickname}님이 입력할 차례입니다.`
    taggerText.value = normalText.value
  }

  const missAttempt = () => {
    if (currentHit.value == condition.value.maxCount) {
      isOver.value = true
    }

    currentHit.value++
    normalText.value = `시간 초과!`
    taggerText.value = normalText.value
  }

  const attempt = (data) => {
    hasTried.value = true
    scorerId.value = data.memberId
    isAnswer.value = data.isAnswer
    isOver.value = data.isOver
    currentHit.value = data.nextCount
    normalText.value = data.message
    taggerText.value = normalText.value
  }

  const clearTry = () => {
    hasTried.value = false
    scorerId.value = null
  }

  const clearAnswer = () => {
    clearTry()

    isAnswer.value = false
  }

  const gameOver = () => {
    normalText.value = '모든 게임이 종료되었습니다!'
    taggerText.value = normalText.value
    stage.value = GameMessageType.GAME_OVER
  }

  return {
    condition,
    tagger,
    normalText,
    taggerText,
    currentHit,
    nextTurn,
    isAnswer,
    isStart,
    isBeforeStart,
    isConfirm,
    isRoundStart,
    isOver,
    hasTried,
    initialClose,
    scorerId,
    gameJustStart,
    updateCondition,
    setAnswer,
    incrementConfirm,
    stageToRoundStart,
    setNextTurn,
    attempt,
    missAttempt,
    clearAnswer,
    openForFirstTrial,
    gameOver,
  }
})
