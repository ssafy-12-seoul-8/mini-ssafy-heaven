import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useChatStore = defineStore('chat', () => {
  // state
  const chats = ref([])

  // getters

  // actions
  const addChat = (message) => {
    chats.value = [...chats.value, message]
  }

  return {
    chats,
    addChat,
  }
})
