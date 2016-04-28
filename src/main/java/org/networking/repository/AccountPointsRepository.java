
package org.networking.repository;

import java.util.Date;
import java.util.List;

import org.networking.entity.AccountPoints;
import org.networking.enums.PointType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Sonny on 9/26/2015.
 */
public interface AccountPointsRepository extends JpaRepository<AccountPoints, Long> {
	@Query("select a from AccountPoints a where a.account.id = :accountId and date(a.createDate) = date(:date) and a.pointType = :type")
	List<AccountPoints> findAccountPointsByAccountAndDateAndType(@Param(value = "accountId") Long accountId, @Param(value = "date") Date date,
			@Param(value = "type") PointType type);
	
	@Query("select a from AccountPoints a where a.pointType = :type")
	List<AccountPoints> findAccountPointsByType(@Param(value = "type") PointType type);
	
	@Query("select a from AccountPoints a where a.account.id = 1 and a.pointType = 'GROUP' and date(a.createDate) = date(:date)")
	AccountPoints findGroupPointsByDate(@Param(value = "date") Date date);
	
	@Query(value = "select sum(ap.points) from ACCOUNT_POINTS ap "
			+ " join ACCOUNT a on ap.account_id = a.id "
			+ " join USER u on u.id = a.member_id where a.member_id = :memberId "
			+ " and ap.point_type <> 'MATURITY'",
			nativeQuery = true)
	Long getTotalAccountPointsByMember(@Param(value = "memberId") Long memberId);
	
	@Query(value = "select sum(ap.points) from ACCOUNT_POINTS ap "
			+ " join ACCOUNT a on ap.account_id = a.id "
			+ " join USER u on u.id = a.member_id where a.id = :accountId "
			+ " and date(ap.createdate) = date(:date) "
			+ " and ap.point_type <> 'MATURITY' ",
			nativeQuery = true)
	Long getTotalAccountPointsByAccountByDate(@Param(value = "accountId") Long accountId, @Param(value = "date") Date date);
}
