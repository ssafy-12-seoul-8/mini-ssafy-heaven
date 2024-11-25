import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useBaseballStore = defineStore('baseball', () => {
  // state
  const condition = ref()
  const tagger = ref()
  const normalText = ref()
  const taggerText = ref()

  // getters

  // action
  const updateCondition = (data, member) => {
    condition.value = data
    tagger.value = member
    normalText.value = `술래는 ${member.nickname}입니다! 술래가 숫자를 선정하고 있습니다.`
    taggerText.value = '당신은 술래입니다! 숫자를 선정해주세요.'
  }

  return {
    condition,
    tagger,
    normalText,
    taggerText,
    updateCondition,
  }
})
