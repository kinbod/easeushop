package org.networking.entity;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "SALES_ORDER")
public class SalesOrder extends BaseEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SELLER_ID")
	private Member seller;

	@Column(name = "SELLER_ID", insertable = false, updatable = false)
	private Long sellerId;

	@Column(name = "TOTAL_PRICE")
	private Double totalPrice;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SalesItem> items;
	
	@Column(name = "ORDER_DATE")
    private Date orderDate;
	
	@Column(name = "ORDER_DATE_STR")
	private String orderDateString;

	@Transient
	private Long totalMemberPoints;

	@Transient
	private Double totalGroupPoints;

	public Member getSeller() {
		return seller;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public List<SalesItem> getItems() {
		return items;
	}

	public void setSeller(Member seller) {
		this.seller = seller;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setItems(List<SalesItem> items) {
		this.items = items;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getTotalMemberPoints() { return totalMemberPoints; }

	public void setTotalMemberPoints(Long totalMemberPoints) {
		this.totalMemberPoints = totalMemberPoints;
	}

	public Double getTotalGroupPoints() {
		return totalGroupPoints;
	}

	public void setTotalGroupPoints(Double totalGroupPoints) {
		this.totalGroupPoints = totalGroupPoints;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDateString() {
		return orderDateString;
	}

	public void setOrderDateString(String orderDateString) {
		this.orderDateString = orderDateString;
	}
	
	public String getTotalPriceDisplay() {
		if(this.getTotalPrice() != null) {
			NumberFormat n = NumberFormat.getCurrencyInstance(); 
			return n.format(this.getTotalPrice()).replace("$", "Php ");
		}
		return null;
	}

}

