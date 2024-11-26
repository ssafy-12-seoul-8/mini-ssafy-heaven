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

    sessionStorage.setItem('me', JSON.stringify(me.value))
  }

  const clearMe = () => {
    me.value = { nickname: null, score: null };
    sessionStorage.removeItem('me');
  };

  onMounted(() => {
    if (me.value.nickname) return;

    const stored = JSON.parse(sessionStorage.getItem('me'))

    if (!stored) {
      return
    }

    me.value = stored
  })

  return {
    me,
    myNickname,
    updateMe,
    clearMe,
  }
})
