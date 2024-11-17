<template>
  <div class="fixed left-0 top-0 w-full h-full bg-black/80 z-50">
    <div class="terminal-loader left-1/2 top-1/2">
      <div class="terminal-header">
        <div class="terminal-title">{{ title }}</div>
        <div class="terminal-controls">
          <div class="control close"></div>
          <div class="control minimize"></div>
          <div class="control maximize"></div>
        </div>
      </div>
      <div class="text">{{ message }}</div>
    </div>
  </div>
</template>

<script setup>
const { title, message } = defineProps({
  title: {
    type: String,
    required: true,
  },
  message: {
    type: String,
    required: true,
  },
})
</script>

<style scoped>
@keyframes blinkCursor {
  50% {
    border-right-color: transparent;
  }
}

@keyframes typeAndDelete {
  0%,
  10% {
    width: 0;
  }
  45%,
  55% {
    width: 6.2em;
  } /* adjust width based on content */
  90%,
  100% {
    width: 0;
  }
}

.terminal-loader {
  border: 0.1em solid #333;
  background-color: #1a1a1a;
  color: #0f0;
  font-size: 1.5rem;
  padding: 3rem 1rem;
  width: 20rem;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border-radius: 4px;
  transform: translate(-50%, -50%);
  position: relative;
  overflow: hidden;
}

.terminal-header {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1.5em;
  background-color: #333;
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
  padding: 0 0.4em;
  box-sizing: border-box;
  font-size: 1.1rem;
}

.terminal-controls {
  float: right;
}

.control {
  display: inline-block;
  width: 0.6em;
  height: 0.6em;
  margin-left: 0.4em;
  border-radius: 50%;
  background-color: #777;
}

.control.close {
  background-color: #e33;
}

.control.minimize {
  background-color: #ee0;
}

.control.maximize {
  background-color: #0b0;
}

.terminal-title {
  float: left;
  line-height: 1.5em;
  color: #eee;
}

.text {
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  border-right: 0.2em solid green; /* Cursor */
  animation:
    typeAndDelete 4s steps(11) infinite,
    blinkCursor 0.5s step-end infinite alternate;
  margin-top: 1.5em;
}
</style>
