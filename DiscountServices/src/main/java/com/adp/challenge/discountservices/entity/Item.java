package com.adp.challenge.discountservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	
	private Integer itemID;
	
	private String type;
	
	private Double cost;
	
	private Integer qty;
	
	private Double discountedAmount;

}
