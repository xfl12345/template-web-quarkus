<script setup lang="ts">
import quarkusLogo from '/quarkus.svg?url&no-inline'
import viteLogo from '/vite.svg?url&no-inline'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const count = ref(0)
function increment() {
  count.value += 1
}

const router = useRouter()
function jumpRouteInspector() {
  router.push('/routes')
}
</script>

<template>
  <div>
    <a href="https://vitejs.dev" target="_blank">
      <img :src="viteLogo" class="logo" alt="Vite logo" />
    </a>
    <a href="https://docs.quarkiverse.io/quarkus-quinoa/dev/web-frameworks.html" target="_blank">
      <img :src="quarkusLogo" class="logo vanilla" alt="Quarkus logo" />
    </a>
  </div>
  <h1>Hello Quinoa!</h1>
  <div class="card">
    <button type="button" @click="increment">count is {{ count }}</button>
    <button type="button" @click="jumpRouteInspector">Inspect Routes</button>
  </div>
  <p class="read-the-docs">
    Quinoa is a Quarkus extension which eases the development, the build and serving of single page apps or
    web components (built with npm : React, Angular, Vue, Lit, Svelte, Astro, SolidJS …) alongside Quarkus.
    It is possible to use it with a Quarkus backend in a single project.
  </p>
</template>
