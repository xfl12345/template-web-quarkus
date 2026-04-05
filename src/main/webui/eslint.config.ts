import pluginVitest from '@vitest/eslint-plugin'
import { defineConfigWithVueTs, vueTsConfigs } from '@vue/eslint-config-typescript'
import { configureVueProject } from '@vue/eslint-config-typescript'
// import pluginOxlint from 'eslint-plugin-oxlint'
import skipFormatting from 'eslint-config-prettier/flat'
import pluginVue from 'eslint-plugin-vue'
import { globalIgnores } from 'eslint/config'

// More info at https://github.com/vuejs/eslint-config-typescript/#advanced-setup
configureVueProject({ scriptLangs: ['ts', 'tsx'] })

export default defineConfigWithVueTs(
  {
    name: 'app/files-to-lint',
    files: ['**/*.{vue,ts,mts,tsx}'],
  },

  globalIgnores(['**/dist/**', '**/dist-ssr/**', '**/coverage/**']),

  ...pluginVue.configs['flat/essential'],
  vueTsConfigs.recommended,

  {
    ...pluginVitest.configs.recommended,
    files: ['src/**/__tests__/*'],
  },

  // ...pluginOxlint.buildFromOxlintConfigFile('.oxlintrc.json'),

  skipFormatting,
  {
    rules: {
      'vue/multi-word-component-names': 'off',
    },
  }
)
