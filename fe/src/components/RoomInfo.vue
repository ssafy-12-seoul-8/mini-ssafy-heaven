<template>
  <div class="flex flex-col justify-center items-center text-xl">
    <span>Room Leader Board</span>
    <span>{{ room.id }} - {{ room.title }}</span>
  </div>
</template>

<script setup>
const { room } = defineProps({
  room: {
    type: Object,
    required: true,
  },
})
</script>
