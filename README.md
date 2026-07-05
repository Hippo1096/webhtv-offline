# 桃子影视（WebHomeTV 魔改封装版）

基于 [FongMi/TV](https://github.com/FongMi/TV) 二次魔改，主打 **配置即封装**：内置配置随 APK 一起打包，首次启动即可直接使用，无需在线导入接口。

本仓库保留 FongMi 原有点播、直播、Spider、解析、投屏、本地 HTTP 服务等能力，并封装了两份配置：

- `桃子源-默认配置.json`：优质且不需要网盘认证的源，作为默认封装配置
- `桃子源-进阶配置.json`：在默认配置基础上，额外加入需要网盘认证的源，可通过 App 备份恢复功能切换

## 快速开始

### 环境要求

- JDK 21
- Android SDK（compileSdk 37）
- Gradle 使用仓库内置 `gradlew`

### 构建

```bash
git clone -b webhtv-latest-target28 https://github.com/fish2018/webhtv.git
cd webhtv
bash gradlew :app:assembleMobileArm64_v8aRelease
```

APK 输出：

```text
Release/apk/mobile-arm64_v8a.apk
Release/apk/mobile-armeabi_v7a.apk
Release/apk/leanback-arm64_v8a.apk
Release/apk/leanback-armeabi_v7a.apk
```

### 配置切换

1. 首次启动自动加载内置默认配置
2. 需要网盘搜索/多盘聚合时，在 App 内通过 **备份恢复** 功能导入 `桃子源-进阶配置.json`

## 目录结构

```text
app/                    Android 主应用
webhome-devkit/        WebHome 开发套件（文档、模板、示例）
Release/               APK 输出目录
docs/                   开发文档
```

完整开发文档见 [docs/应用完整开发文档.md](webhome-devkit/docs/应用完整开发文档.md)。

## 开源说明

本仓库基于开源生态二次开发，软件本体完全免费，不提供任何付费服务、影视内容、直播源、接口源、资源存储或内容分发能力。

本软件仅供技术学习、研究和个人测试使用，请在下载、安装或试用后 24 小时内自行卸载。继续使用本软件所产生的一切行为及后果，由使用者自行承担。

本软件不内置、不售卖、不传播任何影视资源，不对用户自行添加的接口、站源、插件、脚本、链接、网盘资源或第三方服务内容负责。
