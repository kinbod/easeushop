package org.networking.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.networking.entity.EarningsHistory;
import org.networking.entity.Member;
import org.networking.entity.Settings;
import org.networking.service.EarningsHistoryService;
import org.networking.service.MemberService;
import org.networking.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/members-earnings")
public class MembersEarningsController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SettingsService settingsService;
	
	@Autowired
	private EarningsHistoryService earningsHistoryService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view(Model model) {
		model.addAttribute("memberList", memberService.findAll());
        return "admin-members-earnings";
	}
	
	@RequestMapping("/view/{id}")
	public String loadEarningsHistoryPerMember(@PathVariable Long id, Model model){
		Member selected = memberService.load(id);
		model.addAttribute("memberList", memberService.findAll());
	    model.addAttribute("memberEarnings", memberService.findEarningsHistoryPerMember(id));
	    model.addAttribute("multiplier", settingsService.findByKey(Settings.SETTINGS_EARNINGS_PER_POINT).getNumberValue());
	    model.addAttribute("memberName", selected.getCompleteName());
	    model.addAttribute("currentEarning", memberService.findMemberEarningsByDateByUser(new Date(),id));
	    model.addAttribute("maturityValue", settingsService.findByKey(Settings.SETTINGS_MATURITY_INCENTIVE_VALUE).getNumberValue());
	    return "admin-members-earnings";
	}
	
	@RequestMapping(value = "/markClaimed",produces = "application/json", method = RequestMethod.POST)
	private @ResponseBody Map<String, Object> markEarningsAsClaimed(@RequestParam Long id) {
		Map<String, Object> model = new HashMap<>();
		EarningsHistory eh = earningsHistoryService.load(id);
		
		// Update Member Earnings Points isClaimed = true
		memberService.markEarningsAsClaimed(eh.getMemberId(), eh.getTotalPoints(), eh.getTotalEarnings(), eh.getStartDate(), eh.getEndDate());
		
		// Update EarningsHistory isClaimed = true
		eh.setIsClaimed(Boolean.TRUE);
		eh.setDateClaimed(new Date());
		earningsHistoryService.save(eh);
		model.put("success", true);
		return model;
	}

	/*@RequestMapping(value = "/members")
	public @ResponseBody Map<String, Object> findMembersWithUnclaimedEarnings() {
		Map<String, Object> model = new HashMap<>();
		model.put("members", memberService.findMemberEarningsByDate(new Date()));
		return model;
	}*/

	/*@RequestMapping(value = "/markClaimed", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	private @ResponseBody Map<String, Object> markEarningsAsClaimed(@RequestBody EarningsHistory eh) {
		Map<String, Object> model = new HashMap<>();
		memberService.markEarningsAsClaimed(eh.getMemberId(), eh.getTotalPoints(), eh.getTotalEarnings(), new Date());
		model.put("success", true);
		return model;
	}*/


}