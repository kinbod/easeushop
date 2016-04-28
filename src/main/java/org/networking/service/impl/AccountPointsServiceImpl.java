package org.networking.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.networking.entity.Account;
import org.networking.entity.AccountPoints;
import org.networking.entity.Member;
import org.networking.entity.MemberEarningsPoints;
import org.networking.entity.SalesOrder;
import org.networking.entity.Settings;
import org.networking.enums.PointType;
import org.networking.repository.AccountPointsRepository;
import org.networking.service.AccountPointsService;
import org.networking.service.AccountService;
import org.networking.service.MemberEarningsPointsService;
import org.networking.service.SalesOrderService;
import org.networking.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AccountPointsServiceImpl extends BaseServiceImpl<AccountPoints> implements AccountPointsService {

	private AccountPointsRepository accountPointsRepository;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private MemberEarningsPointsService memberEarningsPointsService;
	
	@Override
	public AccountPoints create(AccountPoints accountPoints) {
		return accountPointsRepository.save(accountPoints);
	}

	@Override
	public List<AccountPoints> findAccountPointsByAccountAndDateAndType(Long accountId, Date date, PointType point) {
		return accountPointsRepository.findAccountPointsByAccountAndDateAndType(accountId, date, point);
	}
	
	@Override
	public List<AccountPoints> findAccountPointsByType(PointType type) {
		return accountPointsRepository.findAccountPointsByType(type);
	}

	@Override
	public AccountPoints findGroupPointsByDate(Date date) {
		return accountPointsRepository.findGroupPointsByDate(date);
	}
	
	@Override
	public List<AccountPoints> findAll() {
		return accountPointsRepository.findAll();
	}

	@Override
	public void createForReferral(Member member, Integer newAcctCount, Date date){
		// Update referrers points
		Long referralPoints = settingsService.findByKey(Settings.SETTINGS_TOTAL_POINTS_PER_REFERRAL).getNumberValue();
		Long memberPercentage = settingsService.findByKey(Settings.SETTINGS_PERCENTAGE_PER_REFERRAL).getNumberValue();

		//Points to be distributed depends on Settings
		Long totalPointsForDistribution = ((newAcctCount * referralPoints * memberPercentage)/100);
		Long groupPoints = ((newAcctCount * referralPoints) -  totalPointsForDistribution);
		Double totalGroupPoints = groupPoints.doubleValue();
		
		MemberEarningsPoints mep = new MemberEarningsPoints();
		mep.setCreateDate(date);
		mep.setUpdateDate(date);
		mep.setPoints(newAcctCount.doubleValue());
		mep.setPointType(PointType.REFERRAL);
		mep.setMember(member.getReferrer());
		memberEarningsPointsService.save(mep);

		this.distributePoints(PointType.REFERRAL, member, totalPointsForDistribution, totalGroupPoints, date);

	}

	@Override
	public void createForProduct(SalesOrder order, Date date){
		salesOrderService.setPoints(order);
		
		this.distributePoints(PointType.PRODUCT, order.getSeller(),
				order.getTotalMemberPoints(), order.getTotalGroupPoints(), date);
	}

	public void distributePoints(PointType type, Member member, Long totalPointsForDistribution, Double totalGroupPoints, Date date){
		List<Account> accountList = new ArrayList<Account>();
		if(type.toString().equals(PointType.REFERRAL.toString())) {
			accountList = member.getReferrer().getAccounts();
		} else {
			accountList = member.getAccounts();
		}

		int accountSize = accountList.size();
		int currentAccount = 0;
		for(Account acct : accountList) {
			// get next account
			if(acct.getIsNext()) {
				break;
			}
			currentAccount++;
		}

		if(accountList != null &&  accountSize >= 1) {
			for(int i = 1; i <= totalPointsForDistribution; i++) {
				Account account = accountList.get(currentAccount);
				
				// If divisible by maturity incentive required points, add AccountPoint with type MATURITY
				if(( (account.getTotalPoints()+1) % settingsService.findByKey(Settings.SETTINGS_MATURITY_INCENTIVE_REQUIRED_POINTS).getNumberValue() ) == 0) {
					List<AccountPoints> accountPointsList = this.findAccountPointsByAccountAndDateAndType(account.getId(), date, PointType.MATURITY);
					AccountPoints points = new AccountPoints();
					if(accountPointsList != null && accountPointsList.size() > 0) {
						points = accountPointsList.get(0);
						points.setUpdateDate(date);
						points.setPoints((points.getPoints() + 1));
						this.save(points);
					} else {
						points.setCreateDate(date);
						points.setUpdateDate(date);
						points.setPoints(1D);
						points.setPointType(PointType.MATURITY);
						points.setAccount(account);
						this.create(points);
						
						MemberEarningsPoints mep = new MemberEarningsPoints();
						mep.setCreateDate(date);
						mep.setUpdateDate(date);
						mep.setPoints(1D);
						mep.setPointType(PointType.MATURITY);
						mep.setMember(member);
						memberEarningsPointsService.save(mep);
					}
				}

				// If currentAccount is already equal to the index of the last account in the list, go back to zero
				// else continue iterating currentAccount
				if(currentAccount ==  accountSize-1) {
					currentAccount = 0;
				} else {
					currentAccount++;
				}

				if(accountSize > 1){
					if(i == totalPointsForDistribution) {
						Account next = accountList.get(currentAccount);
						next.setIsNext(true);
						accountService.save(next);
					}
					account.setIsNext(false);
				}

				// Create/Update account points
				Long accountId = account.getId();
				Long pointsValue= accountPointsRepository.getTotalAccountPointsByAccountByDate(account.getId(), date);
				Long pointsForTheDay = pointsValue==null?0:pointsValue;
				if(pointsForTheDay >= settingsService.findByKey(Settings.SETTINGS_MAXIMUM_POINTS_PER_DAY).getNumberValue()) {
					accountId = 1l;
					account = accountService.load(1l);
				} else {
					account.setTotalPoints(account.getTotalPoints() + 1);
				}
				
				accountService.save(account);
				
				PointType typeValue = ((accountId==1l)?PointType.GROUP:type);
				
				List<AccountPoints> accountPointsList = this.findAccountPointsByAccountAndDateAndType(accountId, date, typeValue);
				AccountPoints points = new AccountPoints();
				if(accountPointsList != null && accountPointsList.size() > 0) {
					points = accountPointsList.get(0);
					points.setUpdateDate(date);
					points.setPoints((points.getPoints() + 1));
					this.save(points);
				} else {
					points.setCreateDate(date);
					points.setUpdateDate(date);
					points.setPoints(1D);
					points.setPointType(typeValue);
					points.setAccount(account);
					this.create(points);
				}
			}

			// Create/Update group points
			List<AccountPoints> groupPointsList = this.findAccountPointsByAccountAndDateAndType(1l, date, PointType.GROUP);
			AccountPoints group = new AccountPoints();
			if(groupPointsList != null && groupPointsList.size() > 0) {
				group = groupPointsList.get(0);
				group.setUpdateDate(date);
				group.setPoints(group.getPoints() + totalGroupPoints);
				this.save(group);
			} else {
				group.setCreateDate(date);
				group.setUpdateDate(date);
				group.setPoints(totalGroupPoints);
				group.setPointType(PointType.GROUP);
				group.setAccount(accountService.load(1l));
				this.create(group);
			}
		}
	}
	
	@Override
	public Long getTotalAccountPointsByMember(Long memberId) {
		return accountPointsRepository.getTotalAccountPointsByMember(memberId);
	}

	@Autowired
	@Override
	protected void setRepository(JpaRepository<AccountPoints, Long> repository) {
		this.repository = repository;
		accountPointsRepository = (AccountPointsRepository) repository;
	}
	
}