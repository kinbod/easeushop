package org.networking.cron;

import java.util.Date;

import org.networking.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CreateEarningsForTheWeekCron {
	
	@Autowired
	private MemberService memberService;
	
	// Run CRON every Monday 10 PM
	// @Scheduled(cron = "15 * * * * ?")
	@Scheduled(cron = "0 0 22 ? * MON")
    public void createEarningsForTheWeek() {
		memberService.saveEarningsHistoryByDate(new Date());
    }
}