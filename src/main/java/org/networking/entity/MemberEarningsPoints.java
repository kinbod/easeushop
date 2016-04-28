package org.networking.entity;

import org.networking.enums.PointType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Sonny
 */
@Entity
@Table(name = "MEMBER_EARNINGS_POINTS")
public class MemberEarningsPoints extends BaseEntity{

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID")
	private Member member;

	@Column(name="MEMBER_ID", insertable = false, updatable = false)
	private Long memberId;
	
	@Column(name="POINTS")
	private Double points;

	@Column(name="POINT_TYPE")
	@Enumerated(EnumType.STRING)
	private PointType pointType;

	@Column(name = "IS_CLAIMED")
	private Boolean isClaimed = Boolean.FALSE;

	@Column(name = "DATE_CLAIMED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateClaimed;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public PointType getPointType() {
		return pointType;
	}

	public void setPointType(PointType pointType) {
		this.pointType = pointType;
	}

	public Boolean getIsClaimed() {
		return isClaimed;
	}

	public void setIsClaimed(Boolean isClaimed) {
		this.isClaimed = isClaimed;
	}

	public Date getDateClaimed() {
		return dateClaimed;
	}

	public void setDateClaimed(Date dateClaimed) {
		this.dateClaimed = dateClaimed;
	}
	
}
