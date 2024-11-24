import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useRoomStore = defineStore('room', () => {
  // state
  const currentRoom = ref({})
  const rooms = ref([])

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

  const fetchRooms = (roomPages) => {
    rooms.value.push(...roomPages)
  }

  const updateReadyCount = (readyCount, totalCount) => {
    currentRoom.value.currentReadyCount = readyCount
    currentRoom.value.totalCount = totalCount
  }

  const updateTotalCount = (totalCount) => {
    currentRoom.value.totalCount = totalCount
  }

  const clearRooms = () => {
    rooms.value = []
  }

  return {
    currentRoom,
    rooms,
    isPossibleToStart,
    fetchRoomDetail,
    fetchRooms,
    updateReadyCount,
    updateTotalCount,
    clearRooms,
  }
})
