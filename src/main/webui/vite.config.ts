import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import { fileURLToPath, URL } from 'node:url'
import vueDevTools from 'vite-plugin-vue-devtools'
import { defineConfig, type UserConfig } from 'vite-plus'
import VueRouter from 'vue-router/vite'

// oxfmt 0.48.0 暂不支持解析省略文件后缀的导入
// @ts-expect-error TS5097
import { VueRouterConstPlugin } from './vite/vite-plugin-router-const.ts'

// prettier
const fmt: UserConfig['fmt'] = {
  ignorePatterns: ['typed-router.d.ts'],
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
  base: '/ui',
  staged: {
    '*': 'vp check --fix',
  },
  fmt,
  lint,
  plugins: [VueRouter(), VueRouterConstPlugin(), vue(), vueJsx(), vueDevTools()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
})
