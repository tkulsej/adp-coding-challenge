package com.adp.challenge.DiscountServices;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.adp.challenge.discountservices.dto.GetDiscountResponse;
import com.adp.challenge.discountservices.entity.Discount;
import com.adp.challenge.discountservices.entity.DiscountByType;
import com.adp.challenge.discountservices.entity.Item;
import com.adp.challenge.discountservices.service.DiscountService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DiscountServicesApplicationTests {

	@Autowired
	DiscountService service;
	
	static Discount discount;
	
	@BeforeAll
	public static void initDiscountObject() {
		discount = new DiscountByType();
		discount.setDiscountTypeCode("DBT");
		discount.setDiscountValue(10);
		((DiscountByType)discount).setItemType("CLOTHES");
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test
	@Order(1)
	public void testSaveDiscount() {
		Discount savedObj = service.saveDiscount(discount);
		assertThat(discount).usingRecursiveComparison().isEqualTo(savedObj);
	}
	
	@Test
	@Order(2)
	public void testGetBestDiscount() {
		Item[] items = {new Item(123,"CLOTHES",50.00,1,null)};
		GetDiscountResponse response = service.getBestDiscount(items);
		assertTrue(response.getDiscountTypeCode().equals("DBT"));
	}

}
