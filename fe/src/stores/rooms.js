import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useRoomStore = defineStore('room', () => {
  // state
  const currentRoom = ref({})

  // getters
  const isPossibleToStart = computed(() => {
    if (currentRoom.value.totalCount === 1) {
      return false
    }

    return currentRoom.value.totalCount === currentRoom.value.currentReadyCount
  })

  // actions
  const fetchRoomDetail = (roomDetail) => {
    currentRoom.value = roomDetail
  }

  const updateReadyCount = (readyCount, totalCount) => {
    currentRoom.value.currentReadyCount = readyCount
    currentRoom.value.totalCount = totalCount
  }

  return {
    currentRoom,
    isPossibleToStart,
    fetchRoomDetail,
    updateReadyCount,
  }
})
