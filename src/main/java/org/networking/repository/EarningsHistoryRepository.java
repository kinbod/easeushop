package org.networking.repository;

import org.networking.entity.EarningsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gino on 9/26/2015.
 */
public interface EarningsHistoryRepository extends JpaRepository<EarningsHistory, Long> {
}
