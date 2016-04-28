package org.networking.web.controller;

import java.security.Principal;
import java.util.Date;

import org.networking.entity.Member;
import org.networking.entity.Settings;
import org.networking.service.AccountPointsService;
import org.networking.service.MemberService;
import org.networking.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/my-earnings")
public class DashboardController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SettingsService settingsService;
	
	@Autowired
	private AccountPointsService accountPointsService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view(Model model, Principal principal) {
		if(principal != null) {
			model.addAttribute("pointsSummary", memberService.findAccountPointsByMember(principal.getName()));
			Member active = memberService.findMemberByUsername(principal.getName());
			model.addAttribute("fullName", active.getCompleteName());
			model.addAttribute("numOfAccounts", active.getNumOfAccounts());
			model.addAttribute("currentEarning", memberService.findMemberEarningsByDateByUser(new Date(), active.getId()));
			model.addAttribute("multiplier", settingsService.findByKey(Settings.SETTINGS_EARNINGS_PER_POINT).getNumberValue());
			model.addAttribute("maturityValue", settingsService.findByKey(Settings.SETTINGS_MATURITY_INCENTIVE_VALUE).getNumberValue());
			model.addAttribute("totalPoints", accountPointsService.getTotalAccountPointsByMember(active.getId()));
			model.addAttribute("memberEarnings", memberService.findEarningsHistoryPerMember(active.getId()));
		}
        return "my-earnings";
	}
    
}