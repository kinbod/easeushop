package org.networking.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;

/**
 * Helper class for Member Earning page.
 * Created by Gino on 9/25/2015.
 */
public class MemberEarning {

    private Long memberId;

    private String firstName;

    private String lastName;

    private String middleName;

    private Long totalPoints;

    private Double totalEarnings;

    private Boolean isClaimed;
    
    private Date startDate;
    
    private Date endDate;
    
    private Boolean hasMaturity;
    
    private Long maturityPoints;
    
    private String startDateDisplay;
    
    private String endDateDisplay;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public Boolean getIsClaimed() {
        return isClaimed;
    }

    public void setIsClaimed(Boolean isClaimed) {
        this.isClaimed = isClaimed;
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
