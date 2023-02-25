package com.adp.challenge.discountservices.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.challenge.discountservices.dto.GetDiscountResponse;
import com.adp.challenge.discountservices.entity.Discount;
import com.adp.challenge.discountservices.entity.Item;
import com.adp.challenge.discountservices.repo.DiscountServiceRepo;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DiscountService {

	@Autowired
	private DiscountServiceRepo repo;

	public Discount saveDiscount(Discount discount) {
		// TODO Auto-generated method stub
		log.info("DiscountService.saveDiscount: Enter");
		Discount savedDiscount = repo.save(discount);
		log.info("DiscountService.saveDiscount: Exit");
		return savedDiscount;
	}

	public void removeDiscount(Integer id) {
		// TODO Auto-generated method stub
		log.info("DiscountService.removeDiscount: Enter");
		repo.deleteById(id);		
		log.info("DiscountService.removeDiscount: Exit");
	}

	public GetDiscountResponse getBestDiscount(Item[] items) {
		// TODO Auto-generated method stub
		log.info("DiscountService.getBestDiscount: Enter");

		Map<Discount, List<Item>> discountItemsMap = new HashMap<Discount, List<Item>>();

		//aggregate data on discount type
		for(int i =0; i < items.length; i++) {
			Item item = items[i]; 
			List<Discount> discountAvailable = new ArrayList<Discount>();

			discountAvailable = repo.findByDiscountAvailable(item.getType(), item.getCost()* item.getQty(), item.getItemID(), item.getQty());
			if(!discountAvailable.isEmpty()) {
				discountAvailable.forEach(discount -> {
					if(discountItemsMap.containsKey(discount)) { 
						discountItemsMap.get(discount).add(new Item(item.getItemID(),item.getType(),item.getCost(),item.getQty(),null));
					}
					else {
						discountItemsMap.put(discount, new ArrayList<Item>(Arrays.asList(new Item(item.getItemID(),item.getType(),item.getCost(),item.getQty(),null))));
					}
				});
			}
		}

		//calculate cost for the requested set of items
		Double totalCostAfterDiscount = Arrays.asList(items).stream().mapToDouble(item -> item.getCost()*item.getQty()).sum();

		GetDiscountResponse response = new GetDiscountResponse();
		response.setDiscountTypeCode("NA");
		response.setTotalCostAfterDiscount(totalCostAfterDiscount);

		if(!discountItemsMap.isEmpty()) {
			//calculate total discount per discount type
			discountItemsMap.entrySet().stream().forEach(entry -> entry.getValue()
					.forEach(itemObj -> itemObj.setDiscountedAmount(itemObj.getCost()*itemObj.getQty()*entry.getKey().getDiscountValue()/100)));

			//fetch max discount per discount type
			Map.Entry<Discount, List<Item>> finalMap =
					Collections.max(discountItemsMap.entrySet(), (entry1, entry2) ->
					Double.valueOf(entry1.getValue().stream().mapToDouble(itemobj -> itemobj.getDiscountedAmount()).sum())
					.compareTo(Double.valueOf(entry2.getValue().stream().mapToDouble(itemobj -> itemobj.getDiscountedAmount()).sum())));

			log.info("Total cost before discount: " + totalCostAfterDiscount);
			totalCostAfterDiscount -= (finalMap.getValue().stream().mapToDouble(itemobj -> itemobj.getDiscountedAmount()).sum());
			String discountAppliedType = finalMap.getKey().getDiscountTypeCode();
			log.info("Total cost after discount: " + totalCostAfterDiscount);
			
			response.setDiscountTypeCode(discountAppliedType);
			response.setTotalCostAfterDiscount(totalCostAfterDiscount);
		}

		log.info("DiscountService.getBestDiscount: Exit");
		return response;
	}
	
}
