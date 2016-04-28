package org.networking.service;

import java.util.Date;

import org.networking.entity.EarningsHistory;
import org.networking.entity.Member;

/**
 * Created by Gino on 9/26/2015.
 */
public interface EarningsHistoryService extends BaseService<EarningsHistory> {

    void createEarningsHistory(Member member, Long totalPoints, Double totalEarnings, Date startDate, Date endDate, Long maturityPoints);

}
