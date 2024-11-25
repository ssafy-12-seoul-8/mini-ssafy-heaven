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

  const currentPlayer = computed(() =>
    roomPlayers.value.find(
      (player) => player.nickname === JSON.parse(sessionStorage.getItem('me')).nickname,
    ),
  )
  const manager = computed(() => roomPlayers.value[0])

  const getTagger = (taggerId) => {
    return roomPlayers.value.find((player) => player.memberId === taggerId)
  }

  // actions
  const updatePlayers = (players) => {
    roomPlayers.value = players
  }

  const updateReadyPlayer = (memberId, status) => {
    const player = roomPlayers.value.find((player) => player.memberId === memberId)

    player.status = status
  }

  return {
    roomPlayers,
    sortedByRank,
    currentPlayer,
    manager,
    getTagger,
    updatePlayers,
    updateReadyPlayer,
  }
})
