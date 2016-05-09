/**
 * 
 */
package org.networking.service.impl;

import org.networking.entity.Address;
import org.networking.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author carlquan
 *
 */
@Transactional
@Service
public class AddressServiceImpl extends BaseServiceImpl<Address> implements AddressService {

	@Autowired
	@Override
	protected void setRepository(JpaRepository<Address, Long> repository) {
		this.repository = repository;
	}

}
