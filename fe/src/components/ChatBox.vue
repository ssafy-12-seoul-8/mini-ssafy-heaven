<template>
  <div id="chat-box-container" class="h-1/3 flex flex-col-reverse">
    <div
      id="chat-box-wrapper"
      class="flex flex-col justify-center h-full items-center bg-slate-400 mx-auto mb-4 rounded-lg gap-2"
    >
      <div id="chat-contents-container" class="h-5/6 gap-1 w-11/12">
        <div
          id="chat-contents"
          class="flex flex-col-reverse h-full"
          v-for="(chat, index) in chats"
          :key="index"
        >
          {{ chat }}
        </div>
      </div>
      <div class="z-50 w-11/12 flex justify-center gap-4">
        <input
          class="z-50 w-11/12 pl-4 rounded-xl caret-main-skyblue focus:outline-none border-main-skyblue border"
          :value="text"
          @change="text = event.target.value"
        />
        <button>보내기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useChatStore } from '@/stores/chats'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'

const text = ref('')
const chatStore = useChatStore()
const { chats } = storeToRefs(chatStore)
</script>

<style>
#chat-box-wrapper {
  width: 95%;
}
</style>
