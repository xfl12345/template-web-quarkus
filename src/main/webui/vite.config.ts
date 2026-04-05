import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import { fileURLToPath, URL } from 'node:url'
import vueDevTools from 'vite-plugin-vue-devtools'
import { defineConfig, type UserConfig } from 'vite-plus'
import VueRouter from 'vue-router/vite'

// prettier
const fmt: UserConfig['fmt'] = {
  semi: false,
  singleQuote: true,
  jsxSingleQuote: true,
  trailingComma: 'es5',
  sortImports: {
    groups: [
      'type-import',
      ['value-builtin', 'value-external'],
      'type-internal',
      'value-internal',
      ['type-parent', 'type-sibling', 'type-index'],
      ['value-parent', 'value-sibling', 'value-index'],
      'unknown',
    ],
  },
}

// eslint
const lint: UserConfig['lint'] = {
  plugins: ['eslint', 'typescript', 'unicorn', 'oxc', 'vue', 'vitest'],
  categories: {
    correctness: 'error',
  },
  env: {
    browser: true,
    builtin: true,
  },
  options: {
    typeAware: true,
    // typeCheck: true, // oxlint 暂不支持 vue3 + ts6 解析 import vue 模块
  },
}

// https://vite.dev/config/
export default defineConfig({
  base: '/quinoa',
  staged: {
    '*': 'vp check --fix',
  },
  fmt,
  lint,
  plugins: [VueRouter(), vue(), vueJsx(), vueDevTools()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
})
