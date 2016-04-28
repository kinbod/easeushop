package org.networking.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by Jona on 9/05/2015.
 */
@Entity
@Table(name = "SALES_ITEM")
public class SalesItem extends BaseEntity{

	@OneToOne(cascade = CascadeType.ALL)
	private Product product;

	@Column(name="product_id", insertable = false, updatable = false)
	private Long productId;
	
	@Column(name="QTY")
	private Long quantity;
	
	@Column(name="TOTAL_PRICE")
	private Double totalPrice;

	public Product getProduct() {
		return product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getSalesItemDisplay() {
		return quantity + " " + product.getName();
	}
}
