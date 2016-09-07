package org.networking.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.networking.entity.Account;
import org.networking.entity.EarningsHistory;
import org.networking.entity.Member;
import org.networking.entity.MemberEarning;
import org.networking.entity.PointsSummaryHelper;
import org.networking.entity.Settings;
import org.networking.entity.User;
import org.networking.repository.AccountRepository;
import org.networking.repository.MemberRepository;
import org.networking.service.AccountPointsService;
import org.networking.service.EarningsHistoryService;
import org.networking.service.MemberService;
import org.networking.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sonny on 9/6/2015.
 */
@Transactional
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member> implements MemberService {
	
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private MemberRepository memberRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountPointsService accountPointsService;

	@Autowired
	private EarningsHistoryService earningsHistoryService;
	
	@Autowired
	private SettingsService settingsService;

	@Override
	public Member create(Member member) {
		if(member.getNewPassword() != null) {
			member.setPassword(new BCryptPasswordEncoder().encode(member.getNewPassword()));
		}
		if(member.getTempRole() != null) {
			//member.grantRole(User.Role.valueOf(member.getTempRole()));
		}
		
		try {
			if(member.getDateJoinedString() != null &&
					!member.getDateJoinedString().trim().isEmpty()) {
				member.setDateJoined(dateTimeFormat.parse(member.getDateJoinedString()));
			}
			
			if(member.getBirthdayString() == null ||
					member.getBirthdayString().trim().isEmpty()) {
				member.setBirthday(null);
			} else {
				member.setBirthday(dateFormat.parse(member.getBirthdayString()));
			}
		} catch (ParseException e) {
		}

		// Add accounts
		Integer accounts = member.getNumOfAccounts();

		if(member.isNew()) {
			member.setCreateDate(member.getDateJoined());
			member.setUpdateDate(member.getDateJoined());

			if(accounts != null && accounts >= 1) {
				for(int i = 1; i <= accounts; i++) {
					Account account = new Account();
					account.setCreateDate(member.getDateJoined());
					account.setUpdateDate(member.getDateJoined());
					account.setDateActivated(member.getDateJoined());
					account.setMember(member);
					account.setTotalPoints(0d);
					if(i == 1) {
						account.setIsNext(true);
					} else {
						account.setIsNext(false);
					}
					// For group points
					if(i==1 && memberRepository.count() == 1) {
						account.setIsNextForGroup(Boolean.TRUE);
					}
					accountRepository.save(account);
				}
			}
			
			
			if(member.getReferrer() != null && member.getReferrer().getId() != 1) {
				//Add points to referrer
				accountPointsService.createForReferral(member, accounts, member.getDateJoined());
			}
		} else {
			member.setCreateDate(member.getDateJoined());
			member.setUpdateDate(new Date());
		}
		
        return memberRepository.save(member);
	}
	
	@Override
	public List<Member> findAll() {
		return memberRepository.findAll();
	}
	
	@Override
	public Member getMemberById(Long id) {
		return memberRepository.getOne(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Member findMemberByUsername(String username) {
		return memberRepository.findMemberByUsername(username);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Member> findByLastnameOrFirstnameLike(String keyString){
		return memberRepository.findByLastnameOrFirstnameLike("%" + keyString + "%");
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<Member> findWithUnclaimed(Date date) {
		return memberRepository.findWithUnclaimed(date);
	}*/

	@Override
	public void saveEarningsHistoryByDate(Date date) {
		List<Object[]> objs = memberRepository.findMemberEarningsByDate(date);
		for(Object[] obj : objs) {
			Long maturityPoints = memberRepository.getMaturityPointsByMemberByDate(((BigInteger)obj[0]).longValue(),
					(Date)obj[2], (Date)obj[3]);
			earningsHistoryService.createEarningsHistory(this.load(((BigInteger)obj[0]).longValue()),
					((Double)obj[1]).longValue(),
					(((Double)obj[1])/5) * settingsService.findByKey(Settings.SETTINGS_EARNINGS_PER_POINT).getNumberValue(),
					(Date)obj[2],
					(Date)obj[3], maturityPoints);
		}
	}
	
	@Override
	public MemberEarning findMemberEarningsByDateByUser(Date date, Long id) {
		List<Object[]> objs = memberRepository.findMemberEarningsByDateByUser(date, id);
		if(objs != null && objs.size() > 0) {
			Long maturityPoints = memberRepository.getMaturityPointsByMemberByDate(id, (Date)(objs.get(0)[6]), (Date)(objs.get(0)[7]));
			Object[] obj = objs.get(0);
			MemberEarning me = new MemberEarning();
			me.setMemberId(((BigInteger)obj[0]).longValue());
			me.setFirstName((String)obj[1]);
			me.setLastName((String)obj[2]);
			me.setMiddleName((String)obj[3]);
			me.setTotalPoints(((Double)obj[4]).longValue());
			Boolean bg = (Boolean)obj[5];
			if(bg != null && bg) {
				me.setIsClaimed(true);
			} else {
				me.setIsClaimed(false);
			}
			me.setStartDate((Date)obj[6]);
			me.setEndDate((Date)obj[7]);
			me.setHasMaturity((maturityPoints!=null&&maturityPoints>0)?Boolean.TRUE:Boolean.FALSE);
			me.setMaturityPoints(maturityPoints);
			return me;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PointsSummaryHelper> findAccountPointsByMember(String username) {
		List<PointsSummaryHelper> summary = new ArrayList<>();
		List<Object[]> objs = memberRepository.findAccountPointsByMember(username);
		for(Object[] obj : objs) {
			PointsSummaryHelper p = new PointsSummaryHelper();
			p.setAccountName((String)obj[0]);
			p.setPersonalPoints(((Double)obj[1]).longValue());
			p.setReferralPoints(((Double)obj[2]).longValue());
			p.setProductPoints(((Double)obj[3]).longValue());
			p.setGroupPoints(((Double)obj[4]).longValue());
			summary.add(p);
		}
		
		Member member = memberRepository.findMemberByUsername(username);
		int size = summary.size();
		if(member.getNumOfAccounts() != null && member.getNumOfAccounts() > size) {
			int additional = member.getNumOfAccounts() - size;
			for(int i = 1; i <= additional; i++) {
				PointsSummaryHelper p = new PointsSummaryHelper();
				p.setAccountName(username + "-" + (size + i));
				p.setPersonalPoints(0l);
				p.setReferralPoints(0l);
				p.setProductPoints(0l);
				p.setGroupPoints(0l);
				summary.add(p);
			}
		}
		return summary;
	}

	@Override
	public void markEarningsAsClaimed(Long memberId, Long totalPoints, Double totalEarnings, Date startDate, Date endDate) {
		memberRepository.updateAccountPointsAsClaimed(memberId, startDate, endDate);
	}
	
	@Override
	public List<EarningsHistory> findEarningsHistoryPerMember(Long memberId) {
		return memberRepository.findEarningsHistoryPerMember(memberId);
	}
	
	@Override
	public List<Member> getAllMembersOrderByDate() {
		return memberRepository.getAllMembersOrderByDate();
	}

	@Autowired
	@Override
	protected void setRepository(JpaRepository<Member, Long> repository) {
		this.repository = repository;
		memberRepository = (MemberRepository) repository;
	}
	
}