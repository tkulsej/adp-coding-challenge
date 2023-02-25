package com.adp.challenge.discountservices.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DBT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountByType extends Discount {
	
	private String itemType;

}
