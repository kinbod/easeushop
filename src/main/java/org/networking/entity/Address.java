/**
 * 
 */
package org.networking.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 * @author carlquan
 *
 */
@Entity(name = "Address")
public class Address extends BaseEntity {

	private String street;

	private String number;

	@ManyToMany(mappedBy = "addresses")
	private List<Person> owners = new ArrayList<>();

	public Address() {
	}

	public Address(String street, String number) {
		this.street = street;
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public String getNumber() {
		return number;
	}

	public List<Person> getOwners() {
		return owners;
	}

}
