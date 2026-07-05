# AGENT_HANDOVER_LOG

- 创建时间：2026-07-05
- 项目：070404-TVbox-webhtv离线封装
- 分支：main
- 远端：https://github.com/Hippo1096/webhtv-offline.git

## 当前认知

- 这是一个基于 FongMi/TV 魔改的 Android TV 离线封装项目。
- 离线封装核心入口在 `app/src/main/java/com/fongmi/android/tv/api/config/OfflineBootstrap.java`。
- 内置默认配置入口在 `app/src/main/java/com/fongmi/android/tv/api/config/VodConfig.java`，使用 `assets://config.json`。
- TV/mobile 两套 UI，都包含双配置切换相关代码。

## 待继续

- [ ] 继续审查 `app/src` 只读，不修改。
- [ ] 确认当前未验证项和构建可执行性。
