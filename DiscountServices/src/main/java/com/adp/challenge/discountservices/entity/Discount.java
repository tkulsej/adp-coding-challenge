package com.adp.challenge.discountservices.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discountTypeCode")
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "discountTypeCode")
		@JsonSubTypes({ 
		  @Type(value = DiscountByType.class, name = "DBT"), 
		  @Type(value = DiscountByTotalCost.class, name = "DBTC"),
		  @Type(value = DiscountByCountOfItem.class, name = "DBCI")
		})
@JsonInclude(Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Discount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer discountID;
	
	@Column(insertable = false, updatable = false)
	private String discountTypeCode;
	
	private Integer discountValue;

}
