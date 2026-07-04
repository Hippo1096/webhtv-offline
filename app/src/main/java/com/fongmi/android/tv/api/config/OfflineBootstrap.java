package com.fongmi.android.tv.api.config;

import android.text.TextUtils;

import com.fongmi.android.tv.bean.Config;
import com.fongmi.android.tv.event.RefreshEvent;
import com.fongmi.android.tv.server.Server;
import com.fongmi.android.tv.utils.Task;
import com.github.catvod.utils.Prefers;

public class OfflineBootstrap {

    private static final String CONFIG_URL = "assets://config.json";
    private static final String PREF_KEY = "offline_bootstrap_version";
    private static final String VERSION = "20260705-main-embedded-v2";
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
        if (shouldSelectEmbedded) target.update();
        VodConfig.get().clear().config(target).ensureLoaded();
        if (shouldSelectEmbedded && !VodConfig.get().getSites().isEmpty()) Prefers.put(PREF_KEY, VERSION);
        RefreshEvent.home();
    }
}
