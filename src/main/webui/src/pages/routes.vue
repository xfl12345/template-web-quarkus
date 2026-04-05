<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()
const routes = router.getRoutes()
</script>

<template>
  <div class="routes">
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
    <div class="card">
      <button type="button" @click="router.push('/')">← Back</button>
    </div>
  </div>
</template>

<style scoped>
.routes {
  max-width: 640px;
  margin: 0 auto;
  text-align: center;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin: 1.5rem 0;
  text-align: left;
}

th,
td {
  padding: 0.5rem 0.75rem;
  border-bottom: 1px solid #444;
}

code {
  font-size: 0.85em;
  color: #646cff;
}
</style>
