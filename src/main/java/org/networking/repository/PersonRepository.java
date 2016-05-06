/**
 * 
 */
package org.networking.repository;

import org.networking.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author carlquan
 *
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

}
