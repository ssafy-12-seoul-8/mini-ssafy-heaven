<template>
  <div
    class="group before:hover:scale-95 before:hover:h-full before:hover:w-full before:hover:h-11/12 before:hover:rounded-b-2xl before:transition-all before:duration-500 before:content-[''] before:w-full before:h-2/5 before:rounded-t-2xl before:bg-gradient-to-bl before:absolute before:top-0 w-full h-full relative bg-slate-50 flex flex-col items-center justify-center gap-2 text-center rounded-2xl overflow-hidden"
    :class="[from, via, to]"
  >
    <img
      class="aspect-square h-1/3 bg-blue-700 mt-8 rounded-full border-4 border-slate-50 z-10 group-hover:scale-150 group-hover:-translate-x-14 group-hover:-translate-y-14 transition-all duration-500"
      :src="profileImage"
    />
    <div class="z-10 group-hover:-translate-y-10 transition-all duration-500 pb-4">
      <span class="text-lg font-semibold">{{ player.nickname }}</span>
    </div>
    <div id="score-container">
      <p>개인 점수: {{ player.score }}</p>
      <p>방 점수: {{ player.roomScore }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import defaultUserImage from '@/assets/defaultUserImg.png'
import { TierColor } from '@/enums/TierColor'

const tier = ref(TierColor.BRONZE)
const profileImage = ref(defaultUserImage)
const from = ref()
const via = ref()
const to = ref()
const { player } = defineProps({
  player: {
    type: Object,
    requied: true,
  },
})

onMounted(() => {
  if (player.tier) {
    tier.value = player.tier
  }

  if (player.profileImage) {
    profileImage.value = player.profileImage
  }

  from.value = tier.value.from
  via.value = tier.value.via
  to.value = tier.value.to
})
</script>

<style scoped>
@keyframes show-up {
  0% {
    transform: translateY(0);
  }

  100% {
    transform: translateY(-1rem);
  }
}

#score-container {
  position: absolute;
  transition: top 0.5s;
  top: 100%;
}

.group:hover #score-container {
  top: 70%;
}
</style>
