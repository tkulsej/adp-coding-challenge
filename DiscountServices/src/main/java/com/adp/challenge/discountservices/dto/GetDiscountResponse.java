package com.adp.challenge.discountservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDiscountResponse {
	
	private String discountTypeCode;
	
	private Double totalCostAfterDiscount;

}
