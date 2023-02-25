package com.adp.challenge.discountservices.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DBCI")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountByCountOfItem extends Discount {

	private Integer count;
	
	private Integer itemID;
}
