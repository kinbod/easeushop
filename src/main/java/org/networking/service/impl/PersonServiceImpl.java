/**
 * 
 */
package org.networking.service.impl;

import org.networking.entity.Person;
import org.networking.service.PersonService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author carlquan
 *
 */
@Transactional
@Service
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService {

	@Override
	protected void setRepository(JpaRepository<Person, Long> repository) {
		this.repository = repository;
	}

}
