package org.networking.web.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.networking.entity.Member;
import org.networking.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account-settings")
public class AccountSettingsController {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view(Model model, Principal principal) {
		if(principal != null) {
			model.addAttribute("member", memberService.findMemberByUsername(principal.getName()));
		}
        return "account-settings";
	}

	@RequestMapping(value = "/changeBasicInfo", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> changeBasicInfo(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String middleName, @RequestParam String birthdayString, @RequestParam Integer age,
			@RequestParam String contactNo, @RequestParam String address, @RequestParam String city,
			@RequestParam String civilStatus, @RequestParam String tinNumber, @RequestParam String occupation,
			Principal principal) {
		Map<String, Object> model = new HashMap<>();
		if(principal != null) {
			Member currentUser =  memberService.findMemberByUsername(principal.getName());
			currentUser.setFirstName(firstName);
			currentUser.setLastName(lastName);
			currentUser.setMiddleName(middleName);
			if(birthdayString == null ||
				birthdayString.trim().isEmpty()) {
				currentUser.setBirthday(null);
			} else {
				try {
					currentUser.setBirthday(dateFormat.parse(birthdayString));
				} catch (ParseException e) {
				}
			}
			currentUser.setAge(age);
			currentUser.setContactNo(contactNo);
			currentUser.setAddress(address);
			currentUser.setCity(city);
			currentUser.setCivilStatus(civilStatus);
			currentUser.setTinNumber(tinNumber);
			currentUser.setOccupation(occupation);
			memberService.save(currentUser);
			model.put("success", true);
		} else {
			model.put("success", false);
		}
		return model;
	}
	
	@RequestMapping(value = "/changePassword", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> changePassword(@RequestParam String newPassword, @RequestParam String confirmPassword,
			Principal principal) {
		Map<String, Object> model = new HashMap<>();
		List<String> errorList = new ArrayList<String>();
		
		if(principal != null) {
			if(newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
				model.put("success", false);
				if(newPassword == null || newPassword.isEmpty()) {
					errorList.add("Password is required. ");
				}
				if(confirmPassword == null || confirmPassword.isEmpty()) {
					errorList.add("Confirm Password is required. ");
				}
				model.put("errorList", errorList);
			} else {
				Member currentUser =  memberService.findMemberByUsername(principal.getName());
				if(newPassword.equals(confirmPassword)) {
					currentUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
					memberService.save(currentUser);
					model.put("success", true);
				} else {
					model.put("success", false);
					errorList.add("Password confirmation did not match. ");
					model.put("errorList", errorList);
				}
			}
		} else {
			model.put("success", false);
		}
		return model;
	}
}