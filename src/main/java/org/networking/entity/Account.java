package org.networking.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "ACCOUNT")
public class Account extends BaseEntity {

	@Column(name = "DATE_ACTIVATED")
	private Date dateActivated;

	@Column(name = "TOTAL_POINTS")
	private Double totalPoints;

	@JsonIgnore
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@Column(name = "MEMBER_ID", insertable = false, updatable = false)
	private Long memberId;
	
	@Column(name = "IS_NEXT")
	private Boolean isNext = Boolean.FALSE;
	
	@Column(name = "IS_NEXT_FOR_GROUP")
	private Boolean isNextForGroup = Boolean.FALSE;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getDateActivated() {
		return dateActivated;
	}

	public void setDateActivated(Date dateActivated) {
		this.dateActivated = dateActivated;
	}

	public Double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Double totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Boolean getIsNext() {
		return isNext;
	}

	public void setIsNext(Boolean isNext) {
		this.isNext = isNext;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Boolean getIsNextForGroup() {
		return isNextForGroup;
	}

	public void setIsNextForGroup(Boolean isNextForGroup) {
		this.isNextForGroup = isNextForGroup;
	}
}
