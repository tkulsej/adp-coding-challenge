package com.adp.challenge.discountservices.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.adp.challenge.discountservices.entity.Discount;
import com.adp.challenge.discountservices.entity.DiscountByTotalCost;

@Repository
public interface DiscountServiceRepo extends CrudRepository<Discount, Integer> {
    
	@Query(value="SELECT d FROM Discount d WHERE d.itemType = ?1")
	Discount findByItemType(String itemType);

	@Query(value="SELECT d from Discount d WHERE d.totalCost < ?1 ORDER BY discountValue DESC")
	List<DiscountByTotalCost> findByTotalCost(double d, Pageable pg);

	@Query(value="SELECT d from Discount d WHERE d.itemID =?1 and d.count <= ?2 ORDER BY discountValue DESC")
	List<Discount> findByCountOfItems(Integer itemID, Integer qty, Pageable pg);
	
	@Query(value="SELECT * FROM DISCOUNT WHERE ITEM_TYPE = ?1 UNION (SELECT * FROM DISCOUNT WHERE TOTAL_COST < ?2 ORDER BY DISCOUNT_VALUE DESC LIMIT 1) UNION (SELECT * from Discount WHERE ITEMID =?3 and COUNT <= ?4 ORDER BY DISCOUNT_VALUE DESC LIMIT 1)", nativeQuery = true)
	List<Discount> findByDiscountAvailable(String itemType, double d, Integer itemID, Integer qty);
	
}
