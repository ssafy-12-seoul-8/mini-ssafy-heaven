import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { RoomStatus } from '@/enums/RoomStatus'

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
  const isPlaying = computed(() => RoomStatus.isPlaying(currentRoom.value.status))

  // actions
  const fetchRoomDetail = (roomDetail) => {
    currentRoom.value = roomDetail
  }

  const storeRoomIdInSession = (id) => {
    sessionStorage.setItem('currentRoomId', id)
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

  const updateStatus = (status) => {
    currentRoom.value.status = status
  }

  const clearRooms = () => {
    rooms.value = []
  }

  const clearCurrentRoom = () => {
    currentRoom.value = {}
    sessionStorage.removeItem('currentRoomId')
  }

  return {
    currentRoom,
    rooms,
    isPossibleToStart,
    isPlaying,
    fetchRoomDetail,
    storeRoomIdInSession,
    fetchRooms,
    updateReadyCount,
    updateTotalCount,
    updateStatus,
    clearRooms,
    clearCurrentRoom,
  }
})
