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
  <div :class="$style.root">
    <div>
      <a href="https://vitejs.dev" target="_blank">
        <img :src="viteLogo" :class="$style.logo" alt="Vite logo" />
      </a>
      <a href="https://docs.quarkiverse.io/quarkus-quinoa/dev/web-frameworks.html" target="_blank">
        <img :src="quarkusLogo" :class="[$style.logo, $style.vanilla]" alt="Quarkus logo" />
      </a>
    </div>
    <h1 :class="$style.title">Hello Quinoa!</h1>
    <div :class="$style.card">
      <button type="button" @click="increment">count is {{ count }}</button>
      <button type="button" @click="jumpRouteInspector">Inspect Routes</button>
    </div>
    <p :class="$style.readTheDocs">
      Quinoa is a Quarkus extension which eases the development, the build and serving of single page apps
      or web components (built with npm : React, Angular, Vue, Lit, Svelte, Astro, SolidJS …) alongside
      Quarkus. It is possible to use it with a Quarkus backend in a single project.
    </p>
  </div>
</template>

<style module>
.root {
  font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
  line-height: 1.5;
  font-weight: 400;

  color-scheme: light dark;
  color: rgba(255, 255, 255, 0.87);
  background-color: #242424;

  font-synthesis: none;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;

  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0;
  place-items: center;
  min-width: 320px;
  min-height: 100vh;
  padding: 2rem;
  text-align: center;
}

.root a {
  font-weight: 500;
  color: #646cff;
  text-decoration: inherit;
}
.root a:hover {
  color: #535bf2;
}

.root h1 {
  font-size: 3.2em;
  line-height: 1.1;
}

.root .logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.root .logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.root .vanilla:hover {
  filter: drop-shadow(0 0 2em #f7df1eaa);
}

.root .card {
  padding: 2em;
}

.root .readTheDocs {
  color: #888;
  width: 61vw;
  max-width: 1280px;
}

.root button {
  border-radius: 8px;
  border: 1px solid transparent;
  padding: 0.6em 1.2em;
  font-size: 1em;
  font-weight: 500;
  font-family: inherit;
  background-color: #1a1a1a;
  cursor: pointer;
  transition: border-color 0.25s;
}
.root button:hover {
  border-color: #646cff;
}
.root button:focus,
.root button:focus-visible {
  outline: 4px auto -webkit-focus-ring-color;
}

@media (prefers-color-scheme: light) {
  :root {
    color: #213547;
    background-color: #ffffff;
  }

  a:hover {
    color: #747bff;
  }

  button {
    background-color: #f9f9f9;
  }
}
</style>
