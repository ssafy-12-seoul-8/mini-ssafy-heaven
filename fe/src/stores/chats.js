import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useChatStore = defineStore('chat', () => {
  // state
  const chats = ref([])

  // getters

  // actions
  const addChat = (message) => chats.value.unshift(message)
  const clearChats = () => (chats.value = [])

  return {
    chats,
    addChat,
    clearChats,
  }
})
