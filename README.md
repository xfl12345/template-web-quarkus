# Quarkus Web Template

Quarkus Web 项目模板，开箱即用。

## ✨亮点

|          关键点           | 好处                                                     |
|:----------------------:|:-------------------------------------------------------|
|     Gradle Wrapper     | 和环境 Gradle 版本解耦的项目级 Gradle 版本。真正做到谁都能跑起来。              |
|         foojay         | 统一项目的 JDK 版本。foojay 会自动判断JDK环境，当环境不符合要求时会自动下载安装匹配的 JDK |
| Vite Plus + Vue3 Vapor | 🔥Web前端最前沿的脚手架！⚡闪电般的 IDE 响应！⚡闪电般的构建速度！🚀极致的浏览器UI响应式性能！ |
|    Quarkus + Quinoa    | 💥云原生！前后端无缝集成！⚡️闪电般的热重载！一个项目包揽前后端，方便 AI 直接掌控全局，一键梭哈💥  |

## 🚀快速开始

### 🖥️环境要求

- 因为需要能跑起 Gradle 9.4+ ，所以要求环境有 JDK 17+
- 因为前端绑定了 Vite Plus 命令，所以要求环境有 [Vite Plus](https://viteplus.dev/)

### 🔧常用命令

```bash
# 开发模式（热重载）
./gradlew quarkusDev

# 构建
./gradlew build

# 运行测试
./gradlew test

# 构建 Docker 镜像
./gradlew build -Dquarkus.container-image.build=true
```

## ✅️许可证

[MIT](LICENSE) © Flynn Xu
