package com.fongmi.android.tv.api.config;

import android.text.TextUtils;

import com.fongmi.android.tv.bean.Config;
import com.fongmi.android.tv.event.RefreshEvent;
import com.fongmi.android.tv.server.Server;
import com.fongmi.android.tv.utils.Task;
import com.github.catvod.utils.Prefers;

public class OfflineBootstrap {

    private static final String CONFIG_URL = VodConfig.DEFAULT_URL;
    /** 进阶配置在配置列表中的显示名称 */
    private static final String ADVANCED_CONFIG_NAME = "桃子源·进阶（含网盘）";
    private static final String PREF_KEY = "offline_bootstrap_version";
    private static final String VERSION = "20260705-dual-config-v4";
    private static volatile boolean running;

    public static void ensureAsync() {
        if (running) return;
        running = true;
        Task.execute(() -> {
            try {
                ensureNow();
            } finally {
                running = false;
            }
        });
    }

    public static synchronized void ensureNow() {
        Config embedded = Config.find(CONFIG_URL, BaseConfig.VOD);
        Config current = Config.vod();
        boolean freshInstall = current == null || current.isEmpty();
        boolean bootstrapChanged = !TextUtils.equals(VERSION, Prefers.getString(PREF_KEY));
        boolean usingEmbedded = current != null && TextUtils.equals(CONFIG_URL, current.getUrl());
        boolean shouldSelectEmbedded = freshInstall || bootstrapChanged || usingEmbedded;
        Config target = shouldSelectEmbedded ? embedded : current;
        if (target == null || target.isEmpty()) return;
        Server.get().start();
        if (shouldSelectEmbedded) {
            target.update();
            // 注册进阶配置进数据库，使其出现在"设置→配置"列表中
            registerAdvancedConfig();
        }
        VodConfig.get().clear().config(target).ensureLoaded();
        if (shouldSelectEmbedded && !VodConfig.get().getSites().isEmpty()) Prefers.put(PREF_KEY, VERSION);
        RefreshEvent.home();
    }

    /** 将进阶配置注册为可选配置项，用户可在设置→配置中切换 */
    private static void registerAdvancedConfig() {
        Config.find(VodConfig.ADVANCED_URL, ADVANCED_CONFIG_NAME, BaseConfig.VOD);
    }
}
