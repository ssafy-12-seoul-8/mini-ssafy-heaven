<template>
  <div>{{ roomId }}번 방 입장</div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const { params } = useRoute()
const roomId = ref(params.id)
</script>
