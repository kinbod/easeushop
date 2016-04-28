package org.networking.service.impl;

import java.util.Date;
import java.util.List;

import org.networking.entity.Product;
import org.networking.entity.SalesItem;
import org.networking.entity.SalesOrder;
import org.networking.repository.MemberRepository;
import org.networking.repository.SalesOrderRepository;
import org.networking.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gino on 9/21/2015.
 */
@Service
@Transactional
public class SalesOrderServiceImpl extends BaseServiceImpl<SalesOrder> implements SalesOrderService {

	private SalesOrderRepository salesOrderRepository;
	
	@Autowired
	@Override
	protected void setRepository(JpaRepository<SalesOrder, Long> repository) {
		this.repository = repository;
		salesOrderRepository = (SalesOrderRepository) repository;
	}

    @Override
    public void setPoints(SalesOrder order){
    	long overallPoints = 0l;
    	long overallGroupPoints = 0l;
        for (SalesItem item : order.getItems()){
            Product.MemberPointsType type = item.getProduct().getMemberPointsType();
            Double totalPoints = item.getProduct().getPoints();
            Double memberPoints = item.getProduct().getMemberPoints();

            if(type.equals(Product.MemberPointsType.PERCENTAGE)){
                Double points  = totalPoints * (memberPoints / 100);
                overallPoints += (points.longValue() * item.getQuantity());
                overallGroupPoints += (totalPoints*item.getQuantity());
                order.setTotalMemberPoints(overallPoints);
            }
            else{
                Double points  = totalPoints - memberPoints;
                overallPoints += (points.longValue() * item.getQuantity());
                overallGroupPoints += (totalPoints*item.getQuantity());
                order.setTotalMemberPoints(overallPoints);
            }
            
            order.setTotalGroupPoints((double) (overallGroupPoints - overallPoints));
            order.setCreateDate(new Date());
            order.setUpdateDate(new Date());
        }
    }

	@Override
	public List<SalesOrder> getAllOrdersOrderByDate() {
		return salesOrderRepository.getAllOrdersOrderByDate();
	}

	@Override
	public List<SalesOrder> findAll() {
		return salesOrderRepository.findAll();
	}
}
