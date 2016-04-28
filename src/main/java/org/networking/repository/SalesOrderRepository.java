package org.networking.repository;

import java.util.List;

import org.networking.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Gino on 9/21/2015.
 */
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
	@Query(value = "select sum(total_price) from SALES_ORDER", nativeQuery = true)
	Long getTotalSales();
	
	@Query("select so from SalesOrder so order by so.orderDate desc")
	List<SalesOrder> getAllOrdersOrderByDate();
	
	@Query(value= "select sum(so.total_price) from SALES_ORDER so, "
			+ " (SELECT start_tuesday + INTERVAL 0 second tuesday, "
			+ " start_tuesday + INTERVAL 604799 second monday "
			+ " FROM (SELECT (date(now()) - INTERVAL daysbacktotuesday DAY) start_tuesday "
			+ " FROM (SELECT SUBSTR('5601234',wkndx,1) daysbacktotuesday "
			+ " FROM (SELECT DAYOFWEEK(dt) wkndx FROM (SELECT date(now()) dt) AAAA) AAA) AA) A) M "
			+ " WHERE so.createdate >= tuesday "
			+ " AND so.createdate <= monday", nativeQuery = true)
	Long getWeekTotalSales();
}
