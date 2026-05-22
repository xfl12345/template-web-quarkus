import type { Plugin } from 'vite'

import { readFileSync, writeFileSync } from 'node:fs'
import { resolve } from 'node:path'

/**
 * Vite 插件：在 vue-router/vite 生成路由后，自动同步路由名到 TheConst.ts。
 * 必须放在 VueRouter() 之后、vue() 之前。
 */
export function VueRouterConstPlugin(): Plugin {
  let root: string
  let theConstPath: string
  let typedRouterPath: string

  return {
    name: 'router-const-sync',
    enforce: 'pre',

    configResolved(config) {
      root = config.root
      theConstPath = resolve(root, 'src/router/TheConst.ts')
      typedRouterPath = resolve(root, 'typed-router.d.ts')
    },

    buildStart() {
      syncConst()
    },

    handleHotUpdate({ file }) {
      if (file === typedRouterPath) {
        syncConst()
      }
    },
  }

  function syncConst(): void {
    const routeNames = extractRouteNames()
    if (routeNames.length === 0) return

    const content = generateConstContent(routeNames)
    writeFileSync(theConstPath, content, 'utf-8')
  }

  function extractRouteNames(): string[] {
    let content: string
    try {
      content = readFileSync(typedRouterPath, 'utf-8')
    } catch {
      return []
    }

    const names: string[] = []
    const regex = /^\s*'([^']+)':\s*RouteRecordInfo/gm
    let match: RegExpExecArray | null
    while ((match = regex.exec(content)) !== null) {
      names.push(match[1]!)
    }
    return names
  }

  function generateConstContent(routeNames: string[]): string {
    const entries = routeNames.map((name) => `  ${routePathToConstKey(name)}: '${name}'`).join(',\n')
    const ending = entries === '' ? '' : ','
    return `import type { RouteNamedMap } from 'vue-router/auto-routes'

export const ROUTER_NAMES = {
${entries}${ending}
} as const satisfies Record<string, keyof RouteNamedMap>
`
  }

  /** 将路由路径转为全大写下划线常量名，如 '/first-time-loading-page' → 'FIRST_TIME_LOADING_PAGE' */
  function routePathToConstKey(path: string): string {
    if (path === '/') return 'INDEX'
    const isIndex = path.endsWith('/')
    const key = path
      .replace(/\/+$/, '') // 去掉尾随 /
      .slice(1) // 去掉开头的 /
      .replace(/\/-/g, '_') // 中间的 / 和 - 统一转为下划线分隔
      .replace(/[/-]/g, '_')
      .toUpperCase()
    return isIndex ? `${key}_INDEX` : key
  }
}
