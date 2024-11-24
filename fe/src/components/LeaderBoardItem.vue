<template>
  <div class="text-md">
    {{ player.rank }}. {{ player.nickname }} &nbsp;
    <span class="text-lg text-cyan-950">{{ player.roomScore }}</span>
  </div>
</template>

<script setup>
const { player } = defineProps({
  player: {
    type: Object,
    required: true,
  },
})
</script>
