<template>
  <div>{{ player.rank }}. {{ player.nickname }} {{ player.roomScore }}</div>
</template>

<script setup>
const { player } = defineProps({
  player: {
    type: Object,
    required: true,
  },
})
</script>
