import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useRoomGameStore = defineStore('roomGame', () => {
  // state
  const roomGames = ref([])
  const currentGameTitle = ref()

  // getters
  const getCurrentGame = computed(() => {
    const storedGameTitle = sessionStorage.getItem('currentRoomGameTitle')

    if (!storedGameTitle) {
      return
    }

    return roomGames.value.find((roomGame) => roomGame.title === storedGameTitle)
  })

  // actions
  const updateRoomGames = (games) => {
    roomGames.value = games
  }

  const updateCurrentGame = (title) => {
    sessionStorage.setItem('currentRoomGameTitle', title)
    currentGameTitle.value = roomGames.value.find((roomGame) => roomGame.title === title)
  }

  return {
    roomGames,
    getCurrentGame,
    updateRoomGames,
    updateCurrentGame,
  }
})
