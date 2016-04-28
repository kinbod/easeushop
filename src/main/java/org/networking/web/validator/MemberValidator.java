package org.networking.web.validator;
import java.util.List;

import org.networking.entity.Member;
import org.networking.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {
	
	@Autowired
    private MemberService memberService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Member.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", 
				"error.required", new Object[]{"Username"});
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", 
				"error.required", new Object[]{"First Name"});
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", 
				"error.required", new Object[]{"Last Name"});
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numOfAccounts", 
				"error.required", new Object[]{"No. of Accounts"});
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateJoinedString", 
				"error.required", new Object[]{"Date Joined"});
        
        if(memberService.findAll() != null
        		&& memberService.findAll().size() > 0) {
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "referrer", 
    				"error.required", new Object[]{"Referrer"});
        }
        
        if(member.getNewPassword() != null
        		&& !member.getNewPassword().equals(member.getConfirmPassword())) {
        	errors.rejectValue("confirmPassword","error.password-not-match");
        }
        
        if(member.getNumOfAccounts() != null && member.getNumOfAccounts() < 1) {
        	errors.rejectValue("numOfAccounts", "error.should-be-positive", new Object[]{"No. of Accounts"}, "No. of Accounts should be greater than or equal to 1");
        }
        
        if(member.getAge() != null && member.getAge() < 1) {
        	errors.rejectValue("age", "error.should-be-positive", new Object[]{"Age"}, "Age should be greater than or equal to 1");
        }
        
        if(member.isNew()) {
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", 
					"error.required", new Object[]{"Password"});
	        	
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", 
					"error.required", new Object[]{"Confirm Password"});
	        
	        if(idDuplicateUsername(member.getUsername())) {
	        	errors.rejectValue("username", "error.un-already-exists", new Object[]{member.getUsername()}, "Username already exists");
	        }
        } else {
        	if(member.getNewPassword() != null && !member.getNewPassword().isEmpty()) {
        		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", 
        				"error.required", new Object[]{"Confirm Password"});
        	}
        }
    }
    
    private boolean idDuplicateUsername(String username) {
    	Member members = memberService.findMemberByUsername(username);
    	if(members != null) {
    		return true;
    	}
    	return false;
    }
}