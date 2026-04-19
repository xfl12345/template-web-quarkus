<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
const routes = router.getRoutes()
</script>

<template>
  <div :class="$style.root">
    <h1>Vue Router Routes</h1>
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Path</th>
          <th>Component</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="route in routes" :key="route.name ?? route.path">
          <td>
            <code>{{ route.name ?? '—' }}</code>
          </td>
          <td>
            <code>{{ route.path }}</code>
          </td>
          <td>
            <code>{{ route.components?.default?.name ?? 'Anonymous' }}</code>
          </td>
          <td>
            <button v-if="route.name" type="button" @click="router.push({ path: route.path })">Go</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div :class="$style.card">
      <button type="button" @click="router.push('/')">← Back</button>
    </div>
  </div>
</template>

<style module>
.root {
  height: 100vh;
  width: 100vw;
  margin: 0 auto;
  text-align: center;

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
  align-items: center;
  box-sizing: border-box;
  padding: 2rem;
}

.root h1 {
  font-size: 2.4em;
  line-height: 1.1;
  margin: 0 0 0.5em;
}

.root table {
  width: 61%;
  border-collapse: collapse;
  margin: 1.5rem 0;
  text-align: left;
}

.root th,
.root td {
  padding: 0.5rem 0.75rem;
  border-bottom: 1px solid #444;
}

.root code {
  font-size: 0.85em;
  color: #646cff;
}

.card {
  padding: 2em;
}

.root button {
  border-radius: 8px;
  border: 1px solid transparent;
  padding: 0.6em 1.2em;
  font-size: 1em;
  font-weight: 500;
  font-family: inherit;
  background-color: #1a1a1a;
  color: rgba(255, 255, 255, 0.87);
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
  .root {
    color: #213547;
    background-color: #ffffff;
  }

  .root th,
  .root td {
    border-bottom-color: #ddd;
  }

  .root button {
    background-color: #f9f9f9;
    color: #213547;
  }
}
</style>
