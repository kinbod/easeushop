package org.networking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Sonny on 9/26/2015.
 */
@Entity
@Table(name = "SETTINGS")
public class Settings extends BaseEntity {
	
	public static final String SETTINGS_TOTAL_POINTS_PER_REFERRAL = "SETTINGS_TOTAL_POINTS_PER_REFERRAL";
	public static final String SETTINGS_PERCENTAGE_PER_REFERRAL = "SETTINGS_PERCENTAGE_PER_REFERRAL";
	public static final String SETTINGS_PERCENTAGE_PER_PRODUCT = "SETTINGS_PERCENTAGE_PER_PRODUCT";
	public static final String SETTINGS_EARNINGS_PER_POINT = "SETTINGS_EARNINGS_PER_POINT";
	public static final String SETTINGS_MAXIMUM_POINTS_PER_DAY = "SETTINGS_MAXIMUM_POINTS_PER_DAY";
	public static final String SETTINGS_MATURITY_INCENTIVE_REQUIRED_POINTS = "SETTINGS_MATURITY_INCENTIVE_REQUIRED_POINTS";
	public static final String SETTINGS_MATURITY_INCENTIVE_VALUE = "SETTINGS_MATURITY_INCENTIVE_VALUE";

	@Column(name = "KEY_")
	private String key;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "NUMBER_VALUE")
	private Long numberValue;

	@Column(name = "VALUE_")
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(Long numberValue) {
		this.numberValue = numberValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
