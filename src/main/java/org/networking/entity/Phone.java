/**
 * 
 */
package org.networking.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author carlquan
 *
 */
@Entity
public class Phone extends BaseEntity {

	private String phone_number;
	@ManyToOne
	private Person person;

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
