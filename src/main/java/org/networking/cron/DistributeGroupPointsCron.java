package org.networking.cron;

import java.util.Date;

import org.networking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DistributeGroupPointsCron {
	
	@Autowired
	private AccountService accountService;
	
	// Run CRON everyday at 11 pm
	@Scheduled(cron = "0 59 23 * * ?")
    public void distributeGroupPointsCron() {
		accountService.distributeGroupPoints(new Date());
    }
}