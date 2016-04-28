package org.networking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "MEMBER_TYPE")
public class MemberType extends BaseEntity{

	@Column(name="NAME")
	private String name;
	
	@Column(name="ACCOUNT_NO")
	private Integer accountNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(Integer accountNo) {
		this.accountNo = accountNo;
	}
	
}
