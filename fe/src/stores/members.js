import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member', () => {
  // state
  const me = ref({
    id: null,
    nickname: null,
  })

  // getters
  const myNickname = computed(() => me.value.nickname)

  // actions
  const updateMe = (id, nickname) => {
    me.value.id = id
    me.value.nickname = nickname
  }

  return {
    me,
    myNickname,
    updateMe,
  }
})
