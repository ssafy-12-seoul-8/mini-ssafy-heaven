import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useRoomStore = defineStore('room', () => {
  // state
  const currentRoom = ref({})

  // getters

  // actions
  const fetchRoomDetail = (roomDetail) => {
    currentRoom.value = roomDetail
  }

  return {
    currentRoom,
    fetchRoomDetail,
  }
})
