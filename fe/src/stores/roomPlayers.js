import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useRoomPlayerStore = defineStore('roomPlayer', () => {
  // state
  const roomPlayers = ref([])

  // getters
  const sortedByRank = computed(() => {
    const sorted = roomPlayers.value.toSorted((p1, p2) => p2.rank - p1.rank)

    return sorted.reverse()
  })

  // actions
  const updatePlayers = (players) => {
    roomPlayers.value = players
  }

  return {
    roomPlayers,
    sortedByRank,
    updatePlayers,
  }
})
