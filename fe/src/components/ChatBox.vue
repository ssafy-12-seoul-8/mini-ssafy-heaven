<template>
  <div id="chat-box-container" class="w-full h-full flex flex-col-reverse">
    <div
      id="chat-box-wrapper"
      class="flex flex-col justify-center items-center h-1/3 bg-slate-400 mx-auto mb-4 rounded-lg gap-2"
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
      <input class="z-50" type="text" :value="text" @change="text = event.target.value" />
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
