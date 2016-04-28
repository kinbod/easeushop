package org.networking.repository;

import java.util.Date;
import java.util.List;

import org.networking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Sonny on 9/18/2015.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
	@Query(value="select * from ACCOUNT a where a.CREATEDATE < date(:date)",nativeQuery = true)
	List<Account> getActiveAccountsToday(@Param(value = "date")Date date);
	
	@Query(value="select * from ACCOUNT a where a.IS_NEXT_FOR_GROUP = 1",nativeQuery = true)
	List<Account> getNextAccountForGroupPoints();
	
	@Query(value="select sum(ap.points) from ACCOUNT_POINTS ap "
			+ " where ap.account_id in (select a.id from ACCOUNT a where a.member_id = 1) "
			+ " and date(ap.createdate) <= date(:date) and (ap.IS_CLAIMED IS NULL OR ap.IS_CLAIMED = 0)",nativeQuery = true)
	Long totalPointsForDistribution(@Param(value = "date")Date date);
	
	@Modifying
	@Query(value="update ACCOUNT_POINTS ap set ap.is_claimed = true "
			+ " where ap.account_id in (select a.id from ACCOUNT a where a.member_id = 1) "
			+ " and date(ap.createdate) <= date(:date)",nativeQuery = true)
	void markGroupPointsAsDistributed(@Param(value = "date")Date date);
}