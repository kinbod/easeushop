package org.networking.service;

import java.util.Date;
import java.util.List;

import org.networking.entity.EarningsHistory;
import org.networking.entity.Member;
import org.networking.entity.MemberEarning;
import org.networking.entity.PointsSummaryHelper;

/**
 * Created by Sonny on 9/6/2015.
 */
public interface MemberService extends BaseService<Member> {
	Member create(Member member);

	List<Member> findAll();
	
	Member getMemberById(Long id);

	Member findMemberByUsername(String username);

	List<Member> findByLastnameOrFirstnameLike(String keyString);

	//List<Member> findWithUnclaimed(Date date);

	void saveEarningsHistoryByDate(Date date);
	
	MemberEarning findMemberEarningsByDateByUser(Date date, Long id);

	void markEarningsAsClaimed(Long memberId, Long totalPoints, Double totalEarnings, Date startDate, Date endDate);
	
	List<PointsSummaryHelper> findAccountPointsByMember(String username);
	
	List<EarningsHistory> findEarningsHistoryPerMember(Long memberId);
	
	List<Member> getAllMembersOrderByDate();
}
