package com.adp.challenge.discountservices.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adp.challenge.discountservices.dto.GetDiscountResponse;
import com.adp.challenge.discountservices.entity.Discount;
import com.adp.challenge.discountservices.entity.Item;
import com.adp.challenge.discountservices.repo.DiscountServiceRepo;
import com.adp.challenge.discountservices.service.DiscountService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/discountservice")
@Log4j2
public class DiscountServiceController {
	
	@Autowired
	private DiscountServiceRepo repo;
	
	@Autowired
	private DiscountService service;
	
	@PostMapping("/addDiscount")
	public ResponseEntity<Discount> saveDiscount(@RequestBody Discount discount) {
		log.info("DiscountServiceController.saveDiscount: Enter");
		Discount savedObj = service.saveDiscount(discount);
		log.info("DiscountServiceController.saveDiscount: Exit");
		return new ResponseEntity<Discount>(savedObj,HttpStatus.OK);
	}
	
	@DeleteMapping("/removeDiscount/{id}")
	public ResponseEntity<Object> removeDiscount(@PathVariable Integer id) {
		log.info("DiscountServiceController.removeDiscount: Enter");
		service.removeDiscount(id);
		log.info("DiscountServiceController.removeDiscount: Exit");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/getDiscount")
	public ResponseEntity<GetDiscountResponse> getBestDiscount(@RequestBody Item[] items){
		log.info("DiscountServiceController.getBestDiscount: Enter");
		GetDiscountResponse response = service.getBestDiscount(items);
		log.info("DiscountServiceController.getBestDiscount: Exit");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
