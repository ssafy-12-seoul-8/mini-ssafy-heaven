import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { GameMessageType } from '@/enums/GameType'

export const useBaseballStore = defineStore('baseball', () => {
  // state
  const condition = ref()
  const tagger = ref()
  const normalText = ref()
  const taggerText = ref()
  const stage = ref()

  // getters
  const isStart = computed(() => stage.value === GameMessageType.START)
  const isBeforeTrial = computed(() => stage.value === GameMessageType.SET_ANSWER)

  // action
  const updateCondition = (data, member) => {
    stage.value = GameMessageType.START
    condition.value = data
    tagger.value = member
    normalText.value = `술래는 ${member.nickname}입니다! 술래가 숫자를 선정하고 있습니다.`
    taggerText.value = '당신은 술래입니다! 숫자를 선정해주세요.'
  }

  const setAnswer = (condition) => {
    stage.value = GameMessageType.SET_ANSWER
    condition.value = condition
    normalText.value = '숫자가 정해졌습니다! 게임 설명을 읽고 진행해주세요!'
    taggerText.value = normalText.value
  }

  return {
    condition,
    tagger,
    normalText,
    taggerText,
    isStart,
    isBeforeTrial,
    updateCondition,
    setAnswer,
  }
})
