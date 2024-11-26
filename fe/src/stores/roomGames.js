import { ref, computed, watchEffect } from 'vue'
import { defineStore } from 'pinia'

export const useRoomGameStore = defineStore('roomGame', () => {
  // state
  const roomGames = ref([])
  const currentGame = ref()
  const currentRound = ref(1)

  // getters
  const allRoundOver = computed(() => {
    if (!currentGame.value) {
      return false
    }

    return currentRound.value - 1 === currentGame.value.roundLimit
  })

  // actions
  const updateRoomGames = (games) => {
    roomGames.value = games
  }

  const updateCurrentGame = (title) => {
    sessionStorage.setItem('currentRoomGameTitle', title)
    currentGame.value = roomGames.value.find((roomGame) => roomGame.title === title)
  }

  const nextRound = () => {
    currentRound.value++
  }

  const clearCurrentGame = () => {
    sessionStorage.removeItem('currentRoomGameTitle')
    currentGame.value = null
    currentRound.value = 0
  }

  watchEffect(() => {
    const storedTitle = sessionStorage.getItem('currentRoomGameTitle')

    if (!currentGame.value && storedTitle) {
      currentGame.value = roomGames.value.find((roomGame) => roomGame.title === storedTitle)
    }
  })

  return {
    roomGames,
    currentGame,
    allRoundOver,
    updateRoomGames,
    updateCurrentGame,
    nextRound,
    clearCurrentGame,
  }
})
