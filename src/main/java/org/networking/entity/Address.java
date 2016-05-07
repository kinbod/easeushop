/**
 * 
 */
package org.networking.entity;

import javax.persistence.Entity;

/**
 * @author carlquan
 *
 */
@Entity(name = "Address")
public class Address extends BaseEntity {

	private String street;

	private String number;

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

}
