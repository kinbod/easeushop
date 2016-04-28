package org.networking.service.impl;

import java.util.List;

import org.networking.entity.SalesItem;
import org.networking.service.SalesItemService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesItemServiceImpl extends BaseServiceImpl<SalesItem> implements SalesItemService {

	@Override
	protected void setRepository(JpaRepository<SalesItem, Long> repository) {
		this.repository = repository;
	}

	@Override
	public List<SalesItem> findAll() {
		return this.repository.findAll();
	}
	
}
