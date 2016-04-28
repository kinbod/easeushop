package org.networking.web.controller;

import java.security.Principal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Gino on 8/28/2015.
 */
@Controller
public class HomeController {
	
    @RequestMapping("/")
    String index(Principal principal) {
    	/*
    	 * Redirect to "My Earnings" page upon login
    	 */
    	if(principal != null) {
    		return "redirect:my-earnings";
    	}
        return "index";
    }
    
    /*
	 * Public links
	 */
    @RequestMapping(value = {"/login", "/logout"})
    String login() {
        return "login";
    }
    
    @RequestMapping("/contact")
    String homeContact() {
        return "contact";
    }
    
    @RequestMapping("/infinite-business-solutions")
    String homeIbs() {
        return "ibs";
    }
    
    @RequestMapping("/products")
    String product() {
        return "product";
    }
    
    @RequestMapping("/product-1")
    String product1() {
        return "product-1";
    }
    
    @RequestMapping("/product-2")
    String product2() {
        return "product-2";
    }
    
    @RequestMapping("/product-3")
    String product3() {
        return "product-3";
    }
    
    /*
     * Non-public links
     */
    @RequestMapping("/order")
    String order() {
        return "admin-order";
    }
    
    @RequestMapping("/messages")
    String messages() {
        return "messages";
    }
    
    @RequestMapping("/order-products")
    String orderProducts() {
        return "order-products";
    }
}
