<template>
  <div
    id="root-container-wrapper"
    class="flex justify-center items-center h-screen w-7/12 max-w-[1024px] min-w-[384px] m-auto"
  >
    <div
      id="root-container"
      class="h-5/6 flex justify-center items-center w-full rounded-lg shadow-2xl bg-white"
    >
      <slot></slot>
    </div>
  </div>
</template>
