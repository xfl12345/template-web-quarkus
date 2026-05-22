import type { RouteNamedMap } from 'vue-router/auto-routes'

export const ROUTER_NAMES = {
  INDEX: '/',
  ROUTES: '/routes',
} as const satisfies Record<string, keyof RouteNamedMap>
