package org.networking.service;

import java.util.Date;
import java.util.List;

import org.networking.entity.AccountPoints;
import org.networking.entity.Member;
import org.networking.entity.SalesOrder;
import org.networking.enums.PointType;

/**
 * Created by Sonny on 9/26/2015.
 */
public interface AccountPointsService extends BaseService<AccountPoints> {
	AccountPoints create(AccountPoints accountPoints);

	List<AccountPoints> findAccountPointsByAccountAndDateAndType(Long accountId, Date date, PointType type);
	
	List<AccountPoints> findAccountPointsByType(PointType type);
	
	AccountPoints findGroupPointsByDate(Date date);

	List<AccountPoints> findAll();

	void createForReferral(Member referrer, Integer newAcctCount, Date date);

	void createForProduct(SalesOrder order, Date date);
	
	Long getTotalAccountPointsByMember(Long memberId);
}


