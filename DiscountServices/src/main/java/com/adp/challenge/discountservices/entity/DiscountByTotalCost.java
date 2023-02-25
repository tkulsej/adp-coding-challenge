package com.adp.challenge.discountservices.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DBTC")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountByTotalCost extends Discount {

	private Double totalCost;
}
