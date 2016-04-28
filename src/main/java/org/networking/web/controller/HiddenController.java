package org.networking.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.networking.entity.Account;
import org.networking.entity.Member;
import org.networking.entity.SalesOrder;
import org.networking.repository.AccountRepository;
import org.networking.repository.MemberRepository;
import org.networking.service.AccountPointsService;
import org.networking.service.AccountService;
import org.networking.service.MemberService;
import org.networking.service.SalesItemService;
import org.networking.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/*memberService.saveEarningsHistoryByDate(new Date());
*/

/**
 * Used for manually running Cron for (1) Distributing Daily Group Points & (2) Saving Weekly Earnings
 * @author sony
 *
 */
@Controller
@RequestMapping("/hidden")
public class HiddenController {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AccountPointsService accountPointsService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private SalesItemService salesItemService;
	
	@Autowired
	private SalesOrderService salesOrderService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view() {
        return "hidden";
	}
	
	@RequestMapping(value = "/distributeGroupPoints", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> distributeGroupPoints(@RequestParam String date) {
		Map<String, Object> model = new HashMap<>();
		try {
			accountService.distributeGroupPoints(dateFormat.parse(date));
		} catch (ParseException e) {
			model.put("success", false);
			return model;
		}
		model.put("success", true);
		return model;
	}
	
	@RequestMapping(value = "/distributeAllGroupPoints", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> distributeAllGroupPoints(@RequestParam String date) {
		Map<String, Object> model = new HashMap<>();
		try {
			Calendar start = Calendar.getInstance();
			start.setTime(dateFormat.parse(date));
			Calendar end = Calendar.getInstance();
			end.setTime(new Date());

			for (Date newDate = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), newDate = start.getTime()) {
				accountService.distributeGroupPoints(newDate);
			}
		} catch (ParseException e) {
			model.put("success", false);
			return model;
		}
		model.put("success", true);
		return model;
	}
	
	/*@RequestMapping(value = "/updateReferralPoints", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateReferralPoints() {
		Map<String, Object> model = new HashMap<>();
		try {
			List<AccountPoints> referrals = accountPointsService.findAccountPointsByType(PointType.REFERRAL);
			if(referrals != null && referrals.size() > 1) {
				for(AccountPoints referral : referrals) {
					referral.setPoints(referral.getPoints() + 4);
					accountPointsService.save(referral);
					
					AccountPoints a = accountPointsService.findGroupPointsByDate(referral.getCreateDate());
					if(a != null) {
						a.setPoints(a.getPoints() + 4);
						accountPointsService.save(a);
					}
				}
			}
		} catch (Exception e) {
			model.put("success", false);
			return model;
		}
		model.put("success", true);
		return model;
	}*/
	
	@RequestMapping(value = "/saveEarningsHistoryByDate", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveEarningsHistoryByDate(@RequestParam String date) {
		
		/*Map<String, Object> model = new HashMap<>();
		try {
			Calendar start = Calendar.getInstance();
			start.setTime(dateFormat.parse(date));
			Calendar end = Calendar.getInstance();
			end.setTime(new Date());

			for (Date newDate = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), newDate = start.getTime()) {
				if(start.DAY_OF_WEEK == Calendar.MONDAY) {
					memberService.saveEarningsHistoryByDate(newDate);
				}
			}
		} catch (ParseException e) {
			model.put("success", false);
			return model;
		}
		model.put("success", true);
		return model;*/
		
		Map<String, Object> model = new HashMap<>();
		try {
			memberService.saveEarningsHistoryByDate(dateFormat.parse(date));
		} catch (ParseException e) {
			model.put("success", false);
			return model;
		}
		model.put("success", true);
		return model;
	}
	
	@RequestMapping(value = "/createAccounts", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createAccounts() {
		Map<String, Object> model = new HashMap<>();
		
		List<Member> members = memberService.findAll();
		
		for(Member member : members) {
			// Add accounts
			Integer accounts = member.getNumOfAccounts();

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
		}
		
		model.put("success", true);
		return model;
	}
	
	@RequestMapping(value = "/createProductPoints", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createProductPoints() {
		Map<String, Object> model = new HashMap<>();
		try {
			List<SalesOrder> list = salesOrderService.findAll();
			for(SalesOrder order : list) {
				accountPointsService.createForProduct(order, order.getOrderDate());
			}
		} catch (Exception e) {
			model.put("success", false);
			return model;
		}
		model.put("success", true);
		return model;
	}
	
	/*@RequestMapping(value = "/updateReferralPoints", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateReferralPoints() {
		Map<String, Object> model = new HashMap<>();
		try {
			List<AccountPoints> referrals = accountPointsService.findAccountPointsByType(PointType.REFERRAL);
			if(referrals != null && referrals.size() > 1) {
				for(AccountPoints referral : referrals) {
					referral.setPoints(referral.getPoints() * 5);
					accountPointsService.save(referral);
					
					AccountPoints a = accountPointsService.findGroupPointsByDate(referral.getCreateDate());
					if(a != null) {
						a.setPoints(a.getPoints() + (referral.getPoints() * 4));
						accountPointsService.save(a);
					}
				}
			}
		} catch (Exception e) {
			model.put("success", false);
			return model;
		}
		model.put("success", true);
		return model;
	}*/
}
	