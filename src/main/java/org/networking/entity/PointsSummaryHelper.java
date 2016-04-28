package org.networking.entity;

/**
 * Helper class for Points Summary Table
 * Created by Sonny on 9/26/2015.
 */
public class PointsSummaryHelper {

    private String accountName;
    
    // Sum of referral and product points
    private Long personalPoints;
    
    private Long referralPoints;
    
    private Long productPoints;
    
    private Long groupPoints;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getReferralPoints() {
		return referralPoints;
	}

	public void setReferralPoints(Long referralPoints) {
		this.referralPoints = referralPoints;
	}

	public Long getProductPoints() {
		return productPoints;
	}

	public void setProductPoints(Long productPoints) {
		this.productPoints = productPoints;
	}

	public Long getGroupPoints() {
		return groupPoints;
	}

	public void setGroupPoints(Long groupPoints) {
		this.groupPoints = groupPoints;
	}

	public Long getPersonalPoints() {
		return personalPoints;
	}

	public void setPersonalPoints(Long personalPoints) {
		this.personalPoints = personalPoints;
	}
	
}
