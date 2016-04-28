package org.networking.entity;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gino on 9/26/2015.
 */
@Entity
@Table(name = "EARNINGS_HISTORY")
public class EarningsHistory extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "MEMBER_ID", insertable = false, updatable = false)
    private Long memberId;

    @Column(name = "DATE_CLAIMED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateClaimed;

    @Column(name = "TOTAL_POINTS")
    private Long totalPoints;

    @Column(name = "TOTAL_EARNINGS")
    private Double totalEarnings;
    
    @Column(name = "START_DATE")
    private Date startDate;
    
    @Column(name= "END_DATE")
    private Date endDate;
    
    @Column(name = "IS_CLAIMED")
	private Boolean isClaimed = Boolean.FALSE;
    
    @Column(name = "HAS_MATURITY")
    private Boolean hasMaturity = Boolean.FALSE;
    
    @Column(name = "MATURITY_POINTS")
    private Long maturityPoints;
    
    @Transient
    private String startDateDisplay;
    
    @Transient
    private String endDateDisplay;

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

    public Date getDateClaimed() {
        return dateClaimed;
    }

    public void setDateClaimed(Date dateClaimed) {
        this.dateClaimed = dateClaimed;
    }

    public Long getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Long totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(Double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getStartDateDisplay() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		return startDate==null?"":dateFormat.format(startDate);
	}

	public void setStartDateDisplay(String startDateDisplay) {
		this.startDateDisplay = startDateDisplay;
	}

	public String getEndDateDisplay() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		return endDate==null?"":dateFormat.format(endDate);
	}

	public void setEndDateDisplay(String endDateDisplay) {
		this.endDateDisplay = endDateDisplay;
	}

	public Boolean getIsClaimed() {
		return isClaimed==null?false:isClaimed;
	}

	public void setIsClaimed(Boolean isClaimed) {
		this.isClaimed = isClaimed;
	}

	public Boolean getHasMaturity() {
		return hasMaturity;
	}

	public void setHasMaturity(Boolean hasMaturity) {
		this.hasMaturity = hasMaturity;
	}

	public Long getMaturityPoints() {
		return maturityPoints;
	}

	public void setMaturityPoints(Long maturityPoints) {
		this.maturityPoints = maturityPoints;
	}
}
