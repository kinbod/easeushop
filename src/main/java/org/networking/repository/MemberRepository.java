package org.networking.repository;

import java.util.Date;
import java.util.List;

import org.networking.entity.EarningsHistory;
import org.networking.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Sonny on 9/6/2015.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findMemberByUsername(String username);
	
	@Query("select m from Member m order by m.createDate")
	List<Member> getAllMembersOrderByDate();

	@Query("select m from Member m where m.firstName like :keyString or m.lastName like :keyString")
	List<Member> findByLastnameOrFirstnameLike(@Param(value = "keyString") String keyString);

	/*@Query("select m from Member m where m.id in " +
			"(select distinct a.memberId from Account a where a.id in " +
			"(select ap.accountId from AccountPoints ap where (ap.isClaimed is null or ap.isClaimed = false) and DATE(ap.createDate) = DATE(:date) ))")
	List<Member> findWithUnclaimed(@Param(value = "date")Date date);

	@Query("select m from Member m where m.id in " +
			"(select distinct a.memberId from Account a where a.id in " +
			"(select ap.accountId from AccountPoints ap where DATE(ap.createDate) = DATE(:date) ))")
	List<Member> findWithAccountPointsForDate(@Param(value = "date")Date date);*/

	/*@Query(value = "select u.id as memberId, u.FIRST_NAME as firstName, u.LAST_NAME as lastName, u.MIDDLE_NAME as middleName, t4.total_points as totalPoints, \n" +
			"case when eh.DATE_CLAIMED is null then false else true end as isClaimed\n" +
			"from USER u join ( \n" +
			"select t3.member_id, sum(t3.total) total_points from (\n" +
			"select a.id, a.member_id, t2.total from ACCOUNT a\n" +
			"join (\n" +
			"select ap.ACCOUNT_ID, sum(ap.points) total from ACCOUNT_POINTS ap\n" +
			"where date(ap.CREATEDATE) = date(:date)\n" +
			"group by ap.ACCOUNT_ID\n" +
			") t2 on t2.account_id = a.id) t3\n" +
			"group by t3.member_id) t4 on u.id = t4.member_id\n" +
			"left join EARNINGS_HISTORY eh on eh.MEMBER_ID = u.id", nativeQuery = true)*/
	
	// Returns earnings for the week (cut off is from Tuesday to Monday) of all users
	@Query(value = " SELECT u.ID as memberId, u.FIRST_NAME as firstName, u.LAST_NAME as lastName, u.MIDDLE_NAME as middleName,"
			+ "			IFNULL(sum(mep.POINTS),0) as totalPoints, mep.IS_CLAIMED as isClaimed, tuesday, monday"
			+ "			from MEMBER_EARNINGS_POINTS mep"
			+ "			join USER u on u.ID = mep.MEMBER_ID,"
			+ "			(SELECT start_tuesday + INTERVAL 0 second tuesday,"
			+ "			start_tuesday + INTERVAL 604799 second monday"
			+ "			FROM (SELECT (date(:date) - INTERVAL daysbacktotuesday DAY) start_tuesday"
			+ "			FROM (SELECT SUBSTR('5601234',wkndx,1) daysbacktotuesday"
			+ "			FROM (SELECT DAYOFWEEK(dt) wkndx FROM (SELECT date(:date) dt) AAAA) AAA) AA) A) M"
			+ "			WHERE DATE(mep.createdate) >= tuesday"
			+ "			AND DATE(mep.createdate) <= monday"
			+ "			AND mep.point_type = 'REFERRAL'"
			+ " group by u.id", nativeQuery = true)
	List<Object[]> findMemberEarningsByDate(@Param(value = "date")Date date);
	
	// Returns earnings for the week (cut off is from Tuesday to Monday) of a specific user
	@Query(value = " SELECT u.ID as memberId, u.FIRST_NAME as firstName, u.LAST_NAME as lastName, u.MIDDLE_NAME as middleName,"
			+ "			IFNULL(sum(mep.POINTS),0) as totalPoints, mep.IS_CLAIMED as isClaimed, tuesday, monday"
			+ "			from MEMBER_EARNINGS_POINTS mep"
			+ "			join USER u on u.ID = mep.MEMBER_ID,"
			+ "			(SELECT start_tuesday + INTERVAL 0 second tuesday,"
			+ "			start_tuesday + INTERVAL 604799 second monday"
			+ "			FROM (SELECT (date(:date) - INTERVAL daysbacktotuesday DAY) start_tuesday"
			+ "			FROM (SELECT SUBSTR('5601234',wkndx,1) daysbacktotuesday"
			+ "			FROM (SELECT DAYOFWEEK(dt) wkndx FROM (SELECT date(:date) dt) AAAA) AAA) AA) A) M"
			+ "			WHERE DATE(mep.createdate) >= tuesday"
			+ "			AND DATE(mep.createdate) <= monday"
			+ "			AND u.id = :id AND mep.point_type = 'REFERRAL'"
			+ " group by u.ID", nativeQuery = true)
	List<Object[]> findMemberEarningsByDateByUser(@Param(value = "date")Date date, @Param(value = "id")Long id);

	@Modifying
	@Query(value = "update MEMBER_EARNINGS_POINTS mep \n" +
			"set mep.IS_CLAIMED = true, mep.DATE_CLAIMED = now() \n" +
			"where mep.MEMBER_ID = :memberId \n" +
			"and date(mep.CREATEDATE) >= date(:startDate) and date(mep.CREATEDATE) <= date(:endDate)", nativeQuery = true)
	void updateAccountPointsAsClaimed(@Param(value = "memberId") Long memberId, @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);
	
	@Query(value = "select concat(t.username,'-',@curRow \\:= @curRow + 1) as accountName, "
			+ " sum(t.referralPoints + t.productPoints) as personalPoints, "
			+ " sum(t.referralPoints),sum(t.productPoints),sum(t.groupPoints) from ( "
			+ " select ap.account_id as id, u.username as username, "
			+ " if(ap.point_type = 'REFERRAL', sum(ap.points),0) as referralPoints, "
			+ " if(ap.point_type = 'PRODUCT', sum(ap.points),0) as productPoints, "
			+ " if(ap.point_type = 'GROUP', sum(ap.points),0) as groupPoints "
			+ " from ACCOUNT_POINTS ap "
			+ " join ACCOUNT a on ap.account_id = a.id "
			+ " join USER u on a.member_id = u.id "
			+ " where u.username = :username "
			+ " group by ap.account_id,ap.point_type "
			+ " ) as t join (select @curRow \\:= 0) r group by id", nativeQuery = true)
	List<Object[]> findAccountPointsByMember(@Param(value = "username") String username);
	
	@Query("select e from EarningsHistory e where e.memberId = :memberId order by e.startDate desc")
	List<EarningsHistory> findEarningsHistoryPerMember(@Param(value = "memberId") Long memberId);
	
	@Query(value="SELECT if(sum(mep.POINTS) is null, 0, sum(mep.POINTS)) as totalPoints "
			+ " from MEMBER_EARNINGS_POINTS mep "
			+ " left join USER m on m.ID = mep.MEMBER_ID "
			+ " WHERE m.ID = :memberId "
			+ " AND date(mep.createdate) >= date(:startDate) "
			+ " AND date(mep.createdate) <= date(:endDate) "
			+ " AND mep.POINT_TYPE = 'MATURITY'", nativeQuery = true)
	Long getMaturityPointsByMemberByDate(@Param(value = "memberId") Long memberId, @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate);
}
