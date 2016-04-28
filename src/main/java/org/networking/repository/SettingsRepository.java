package org.networking.repository;

import org.networking.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gino on 9/26/2015.
 */
public interface SettingsRepository extends JpaRepository<Settings, Long> {

    Settings findByKeyEquals(String key);

}
