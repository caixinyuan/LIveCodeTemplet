package com.cxy.livecodetemplet.storage;

import com.cxy.livecodetemplet.model.PluginSettingModel;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.openapi.application.ApplicationManager.*;

@State(name = "LiveCodeTempletStorageSetting", storages = {@Storage(value = "LiveCodeTempletStorageSetting.xml")})
public class LiveCodeTempletStorageSetting implements PersistentStateComponent<PluginSettingModel> {

    public LiveCodeTempletStorageSetting() {
    }

    private PluginSettingModel pluginSetting = new PluginSettingModel();

    @Override
    public @Nullable PluginSettingModel getState() {
        return pluginSetting;
    }

    @Override
    public void loadState(@NotNull PluginSettingModel state) {
        this.pluginSetting = state;
    }

    public static LiveCodeTempletStorageSetting getInstance() {
        return ApplicationManager.getApplication().getService(LiveCodeTempletStorageSetting.class);
    }
}

