package org.networking.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "MEMBER")
public class Member extends User{

	public Member(User user) {
		super(user);
		// TODO Auto-generated constructor stub
	}

	@JsonView
	@OneToMany(mappedBy = "member", cascade=CascadeType.ALL)
	private List<Account> accounts;
	
	@Column(name="DATE_JOINED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateJoined;
	
	@Transient
	private String dateJoinedString;

	@ManyToOne(cascade = CascadeType.ALL)
	private Member referrer;
	
	@Column(name="AGE")
	private Integer age;
	
	@Column(name="BIRTH_DAY")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;
	
	@Transient
	private String birthdayString;
	
	@Column(name="TIN")
	private String tinNumber;
	
	@Column(name="CONTACT_NO")
	private String contactNo;
	
	@Column(name="OCCUPATION")
	private String occupation;
	
	@Column(name="CIVIL_STATUS")
	private String civilStatus;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="numOfAccounts")
	private Integer numOfAccounts;
	
	@Transient
	private String tempRole;

	@Transient
	private String fullName;
	
	@Transient
	private String dateJoinedValue;
	
	@Transient
	private String birthdayValue;

	public List<Account> getAccounts() {
		return accounts;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public Member getReferrer() {
		return referrer;
	}

	public Integer getAge() {
		return age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getTinNumber() {
		return tinNumber;
	}

	public String getContactNo() {
		return contactNo;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getCivilStatus() {
		return civilStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public void setReferrer(Member referrer) {
		this.referrer = referrer;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setCivilStatus(String civilStatus) {
		this.civilStatus = civilStatus;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getNumOfAccounts() {
		return numOfAccounts;
	}

	public void setNumOfAccounts(Integer numOfAccounts) {
		this.numOfAccounts = numOfAccounts;
	}

	public String getBirthdayString() {
		return birthdayString;
	}

	public void setBirthdayString(String birthdayString) {
		this.birthdayString = birthdayString;
	}

	public String getTempRole() {
		return tempRole;
	}

	public void setTempRole(String tempRole) {
		this.tempRole = tempRole;
	}
	
	public String getCompleteName() {
		return this.getFirstName() + " " 
				+ (this.getMiddleName()==null?"":(this.getMiddleName() + " "))
				+ this.getLastName();
	}
	
	public Long getReferrerId() {
		return this.getReferrer()!=null?this.getReferrer().getId():null;
	}
	
	public String getReferrerCompleteName() {
		return this.getReferrer()!=null?this.getReferrer().getCompleteName():null;
	}

	public String getDateJoinedString() {
		return dateJoinedString;
	}

	public void setDateJoinedString(String dateJoinedString) {
		this.dateJoinedString = dateJoinedString;
	}

	public String getFullName() {
		return this.getLastName() + " " + this.getFirstName() ;
	}
	
	public String getDateJoinedValue() {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		return this.getDateJoined()!=null?(dateTimeFormat.format(this.getDateJoined())):null;
	}
	
	public String getBirthdayValue() {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy");
		return this.getBirthday()!=null?(dateTimeFormat.format(this.getBirthday())):null;
	}

}
