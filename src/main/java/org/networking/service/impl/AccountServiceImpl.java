package org.networking.service.impl;

import java.util.Date;
import java.util.List;

import org.networking.entity.Account;
import org.networking.entity.AccountPoints;
import org.networking.enums.PointType;
import org.networking.repository.AccountRepository;
import org.networking.service.AccountPointsService;
import org.networking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jona on 9/26/2015.
 */
@Transactional
@Service
public class AccountServiceImpl extends BaseServiceImpl<Account> implements AccountService {
	
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountPointsService accountPointsService;
	
	@Autowired
	@Override
	protected void setRepository(JpaRepository<Account, Long> repository) {
		this.repository = repository;
		this.accountRepository = (AccountRepository) repository;
	}

	@Override
	public void distributeGroupPoints(Date date) {
		List<Account> activeList = accountRepository.getActiveAccountsToday(date);
		if(activeList != null) {
			int accountSize = activeList.size();
			int currentAccount = 0;
			Long totalPoints = accountRepository.totalPointsForDistribution(date);
			
			for(Account acct : activeList) {
				// get next account
				if(acct.getIsNextForGroup() != null && acct.getIsNextForGroup()) {
					break;
				}
				currentAccount++;
			}
			if(totalPoints != null) {
			for(int i = 1; i <= totalPoints; i++) {
				Account account = activeList.get(currentAccount);
				if(account.getId() != 1) {
					account.setTotalPoints(account.getTotalPoints() + 1);
	
					// If currentAccount is already equal to the index of the last account in the list, go back to zero
					// else continue iterating currentAccount
					if(currentAccount ==  accountSize-1) {
						currentAccount = 0;
					} else {
						currentAccount++;
					}
	
					if(i == totalPoints) {
						Account next = activeList.get(currentAccount);
						next.setIsNextForGroup(true);
						this.save(next);
						
						if(totalPoints == 1) {
							account.setIsNextForGroup(false);
						}
					} else{
						if(accountSize > 1){
							account.setIsNextForGroup(false);
						}
					}
					this.save(account);
	
					// Create/Update account points
					List<AccountPoints> accountPointsList = accountPointsService.findAccountPointsByAccountAndDateAndType(account.getId(), date, PointType.GROUP);
					AccountPoints points = new AccountPoints();
					if(accountPointsList != null && accountPointsList.size() > 0) {
						points = accountPointsList.get(0);
						points.setUpdateDate(date);
						points.setPoints((points.getPoints() + 1));
					} else {
						points.setCreateDate(date);
						points.setUpdateDate(date);
						points.setPoints(1D);
						points.setPointType(PointType.GROUP);
						points.setAccount(account);
					}
					accountPointsService.create(points);
				} else {
					i--;
					currentAccount++;
				}
			}
			}
		}
		accountRepository.markGroupPointsAsDistributed(date);
	}

}