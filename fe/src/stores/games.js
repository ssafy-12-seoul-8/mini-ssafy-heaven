import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { gameApi } from '@/apis/games'

export const useGameStore = defineStore('game', () => {
  // state
  const games = ref([])

  // getters
  const unSelectedGames = computed(() => games.value?.filter((game) => !game.isSelected) ?? [])
  const selectedGames = computed(() => games.value?.filter((game) => game.isSelected) ?? [])

  // action
  const fetchAllGames = () => {
    gameApi
      .getAll()
      .then((res) => (games.value = res.data))
      .catch((error) => console.error(error))
  }

  const select = (id, round) => {
    if (selectedGames.value.length >= 3) {
      return
    }

    const selectedGame = games.value.find((game) => game.id === id)
    selectedGame.isSelected = true
    selectedGame.round = round
  }

  const unselect = (id) => {
    const toUnselect = games.value.find((game) => game.id === id)
    toUnselect.isSelected = false
    toUnselect.round = undefined
  }

  return {
    games,
    unSelectedGames,
    selectedGames,
    fetchAllGames,
    select,
    unselect,
  }
})
