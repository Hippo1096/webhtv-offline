"""精确修复：基于已知 key 只移除真正的网盘搜索/认证源"""
import json
from pathlib import Path

BASE = Path(r"D:\00HippoD\agent_workspace\070404-TVbox-webhtv离线封装")
ASSETS = BASE / "app/src/main/assets"

# ====== 精确的网盘源 key 列表（手动审核过的） ======
# 这些是需要网盘账号、或做跨盘搜索的站点
PAN_KEYS = {
    "YpanSo",   # 🐟盘她┃三盘 — 网盘搜索聚合
    "xzso",     # 👻盘它┃三盘 — 网盘搜索
    "Aliso",    # 🙀盘搜┃阿狸 — 阿里云盘搜索
    "YiSo",     # 😹易搜┃阿狸 — 阿里云盘搜索
    "米搜",     # 🦋米搜┃夸父 — 夸克网盘搜索
    "夸搜",    # 😻夸搜┃夸父 — 夸克网盘搜索
    "csp_AList",# AList┃网盘 — 需要网盘认证
}

# ====== 读取当前 config.json ======
with open(ASSETS / "config.json", "r", encoding="utf-8") as fh:
    config = json.load(fh)

sites = config.get("sites", [])
print(f"总站点: {len(sites)}")

# ====== 分类 ======
pan_sites = []
normal_sites = []
for s in sites:
    key = s.get("key", "")
    if key in PAN_KEYS:
        pan_sites.append(s)
        print(f"  网盘: {s.get('name')} (key={key})")
    else:
        normal_sites.append(s)

print(f"正常源: {len(normal_sites)} | 网盘源: {len(pan_sites)}")

# ====== 生成默认配置 ======
default_cfg = {k: v for k, v in config.items() if k != "sites"}
default_cfg["sites"] = normal_sites

# ====== 生成进阶配置 ======
adv_cfg = {k: v for k, v in config.items() if k != "sites"}
adv_cfg["sites"] = normal_sites + pan_sites

# ====== 写入 ======
# 默认配置 → assets/config.json
default_json = json.dumps(default_cfg, ensure_ascii=False, indent=2)
with open(ASSETS / "config.json", "w", encoding="utf-8") as fh:
    fh.write(default_json)
print(f"✅ config.json: {len(default_cfg['sites'])} 站 ({len(default_json)} 字节)")

# 进阶配置 → assets/桃子源-进阶配置.json
adv_json = json.dumps(adv_cfg, ensure_ascii=False, indent=2)
with open(ASSETS / "桃子源-进阶配置.json", "w", encoding="utf-8") as fh:
    fh.write(adv_json)
print(f"✅ 桃子源-进阶配置.json: {len(adv_cfg['sites'])} 站 ({len(adv_json)} 字节)")

# ====== 同步 002过程/ ======
old_dir = BASE / "002过程/桃子源整理/001原始/在线源"
with open(old_dir / "桃子源-默认配置.json", "w", encoding="utf-8") as fh:
    fh.write(default_json)
with open(old_dir / "桃子源-进阶配置.json", "w", encoding="utf-8") as fh:
    fh.write(adv_json)
print("✅ 已同步 002过程/ 副本")
