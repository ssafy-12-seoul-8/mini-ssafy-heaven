import { ref, watchEffect } from 'vue'
import { defineStore } from 'pinia'

export const useRoomGameStore = defineStore('roomGame', () => {
  // state
  const roomGames = ref([])
  const currentGame = ref()

  // getters

  // actions
  const updateRoomGames = (games) => {
    roomGames.value = games
  }

  const updateCurrentGame = (title) => {
    sessionStorage.setItem('currentRoomGameTitle', title)
    currentGame.value = roomGames.value.find((roomGame) => roomGame.title === title)
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
    updateRoomGames,
    updateCurrentGame,
  }
})
