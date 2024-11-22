import { ref, computed, onMounted } from 'vue'
import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member', () => {
  // state
  const me = ref({
    nickname: null,
    score: null,
  })

  // getters
  const myNickname = computed(() => me.value.nickname)

  // actions
  const updateMe = (nickname, score) => {
    me.value.nickname = nickname
    me.value.score = score ?? 0

    localStorage.setItem('me', JSON.stringify(me.value))
  }

  onMounted(() => {
    me.value = JSON.parse(localStorage.getItem('me'))
  })

  return {
    me,
    myNickname,
    updateMe,
  }
})
