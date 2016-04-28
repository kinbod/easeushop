package org.networking.service;

import org.networking.entity.Settings;

import java.util.List;

/**
 * Created by Gino on 9/26/2015.
 */
public interface SettingsService extends BaseService<Settings> {

    void init();

    List<Settings> getAllSettings();

    void updateSettingsCached(Settings settings);

    Settings findByKey(String key);

}
