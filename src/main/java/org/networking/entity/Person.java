/**
 * 
 */
package org.networking.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 * @author carlquan
 *
 */
@Entity(name = "Person")
public class Person extends BaseEntity {

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Address> addresses = new ArrayList<>();

	private String person_name;
	private int person_age;

	public Person() {
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public int getPerson_age() {
		return person_age;
	}

	public void setPerson_age(int person_age) {
		this.person_age = person_age;
	}

}
