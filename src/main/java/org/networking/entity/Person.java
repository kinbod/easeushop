/**
 * 
 */
package org.networking.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Phone> phone;

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

	public List<Phone> getPhones() {
		return phone;
	}

	public void addPhone(Phone phone) {
		this.phone.add(phone);
		phone.setPerson(this);
	}

	public void removePhone(Phone phone) {
		this.phone.remove(phone);
		phone.setPerson(null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
		result = prime * result + person_age;
		result = prime * result + ((person_name == null) ? 0 : person_name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (!addresses.equals(other.addresses))
			return false;
		if (person_age != other.person_age)
			return false;
		if (person_name == null) {
			if (other.person_name != null)
				return false;
		} else if (!person_name.equals(other.person_name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	
}
