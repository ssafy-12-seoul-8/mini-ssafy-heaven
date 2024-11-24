<template>
  <div id="chat-box-container" class="h-1/3 flex flex-col-reverse">
    <div
      id="chat-box-wrapper"
      class="flex flex-col justify-center h-full items-center bg-slate-400 mx-auto mb-4 rounded-lg gap-2"
    >
      <div
        id="chat-contents-container"
        class="z-50 h-5/6 gap-1 w-11/12 flex flex-col-reverse overflow-y-auto overflow-x-hidden [&::-webkit-scrollbar]:w-1 [&::-webkit-scrollbar-track]:rounded-full [&::-webkit-scrollbar-track]:bg-gray-100 [&::-webkit-scrollbar-thumb]:rounded-full [&::-webkit-scrollbar-thumb]:bg-gray-300"
      >
        <div id="chat-contents" v-for="(chat, index) in chats" :key="index">
          {{ chat }}
        </div>
      </div>
      <div class="z-50 w-11/12 flex justify-center gap-4">
        <input
          class="z-50 w-11/12 pl-4 rounded-xl caret-main-skyblue focus:outline-none border-main-skyblue border"
          :value="text"
          @change="text = $event.target.value"
          @keyup.prevent.enter="sendChat"
        />
        <button @click="sendChat">보내기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { roomSocket } from '@/apis/rooms'
import { useChatStore } from '@/stores/chats'
import { useRoomStore } from '@/stores/rooms'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'

const { player } = defineProps({
  player: {
    type: Object,
    required: true,
  },
})
const text = ref('')
const chatStore = useChatStore()
const roomStore = useRoomStore()
const { chats } = storeToRefs(chatStore)

const { currentRoom } = storeToRefs(roomStore)

const sendChat = () => {
  if (!text.value) {
    return
  }

  const request = {
    nickname: player.nickname,
    chat: text.value,
  }

  roomSocket.chat(currentRoom.value.id, request)

  text.value = ''
}
</script>

<style>
#chat-box-wrapper {
  width: 95%;
}
</style>
