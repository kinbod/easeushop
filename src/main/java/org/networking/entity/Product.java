package org.networking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity{

	public enum MemberPointsType { PERCENTAGE, POINTS }

	@Column(name="NAME")
	private String name;
	
	@Column(name="PRICE")
	private Double price;
	
	@Column(name="MEMBER_PRICE")
	private Double memberPrice;
	
	@Column(name="POINTS")
	private Double points;

	@Column(name="MEMBER_POINTS")
	private Double memberPoints;

	@Column(name="MEMBER_POINTS_TYPE")
	private MemberPointsType memberPointsType;

	@Column(name="DESCRIPTION", length=1000)
	private String description;

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public Double getPoints() {
		return points;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getMemberPoints() {
		return memberPoints;
	}

	public void setMemberPoints(Double memberPoints) {
		this.memberPoints = memberPoints;
	}

	public MemberPointsType getMemberPointsType() {
		return memberPointsType;
	}

	public void setMemberPointsType(MemberPointsType memberPointsType) {
		this.memberPointsType = memberPointsType;
	}

	public Double getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Double memberPrice) {
		this.memberPrice = memberPrice;
	}

}
