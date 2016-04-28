package org.networking.service.impl;

import org.networking.entity.MemberEarningsPoints;
import org.networking.repository.MemberEarningsPointsRepository;
import org.networking.service.MemberEarningsPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sonny
 */
@Transactional
@Service
public class MemberEarningsPointsServiceImpl extends BaseServiceImpl<MemberEarningsPoints> implements MemberEarningsPointsService {
	
	private MemberEarningsPointsRepository memberEarningsPointsRepository;
	
	@Autowired
	@Override
	protected void setRepository(JpaRepository<MemberEarningsPoints, Long> repository) {
		this.repository = repository;
		this.memberEarningsPointsRepository = (MemberEarningsPointsRepository) repository;
	}

}