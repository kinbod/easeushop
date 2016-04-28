package org.networking.service.impl;

import java.util.Date;

import org.networking.entity.EarningsHistory;
import org.networking.entity.Member;
import org.networking.service.EarningsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gino on 9/26/2015.
 */
@Transactional
@Service
public class EarningsHistoryServiceImpl extends BaseServiceImpl<EarningsHistory> implements EarningsHistoryService {

    @Autowired
    @Override
    protected void setRepository(JpaRepository<EarningsHistory, Long> repository) {
        this.repository = repository;
    }

    @Override
    public void createEarningsHistory(Member member, Long totalPoints, Double totalEarnings,
    		Date startDate, Date endDate, Long maturityPoints) {
        EarningsHistory eh = new EarningsHistory();
        eh.setMember(member);
        eh.setTotalPoints(totalPoints);
        eh.setTotalEarnings(totalEarnings);
        eh.setStartDate(startDate);
        eh.setEndDate(endDate);
        eh.setMaturityPoints(maturityPoints);
        eh.setHasMaturity((maturityPoints!=null&&maturityPoints>0)?Boolean.FALSE:Boolean.TRUE);
        save(eh);
    }
}
