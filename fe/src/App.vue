<template>
  <Container>
      <router-view />
    </Container>
</template>

<script setup>
import Container from './components/BaseContainer.vue'
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poor+Story&display=swap');

#app {
  font-family: 'Poor Story', system-ui;
  font-weight: 400;
  font-style: normal;
}

body {
  background-image: url("/src/assets/BackGroundImg.png");
  background-size: cover;
}
</style>
