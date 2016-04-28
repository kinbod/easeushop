package org.networking.service.impl;

import org.networking.entity.Settings;
import org.networking.repository.SettingsRepository;
import org.networking.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gino on 9/26/2015.
 */
@Service
@Transactional
public class SettingsServiceImpl extends BaseServiceImpl<Settings> implements SettingsService {

    private static Map<String, Settings> settingsCache;

    static {
        settingsCache = new HashMap<>();
    }

    @PostConstruct
    @Override
    public void init() {
        settingsCache = new HashMap<>();
        List<Settings> all = repository.findAll();
        for(Settings settings : all) {
            settingsCache.put(settings.getKey(), settings);
        }
    }

    @Autowired
    @Override
    protected void setRepository(JpaRepository<Settings, Long> repository) {
        this.repository = repository;
    }

    @Override
    public List<Settings> getAllSettings() {
        return new ArrayList<>(settingsCache.values());
    }

    @Override
    public void updateSettingsCached(Settings settings) {
        settingsCache.put(settings.getKey(), settings);
    }

    @Override
    public Settings findByKey(String key) {
        return settingsCache.get(key);
    }
}
