package org.networking.web.controller;

import javax.validation.Valid;

import org.networking.entity.Member;
import org.networking.entity.Product;
import org.networking.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/product")
public class ProductController  extends BaseController<Product> {
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view() {
        return "admin-product-list";
	}

    @RequestMapping(value="/add-product", method=RequestMethod.POST)
    public String createProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        return "add-product";
    }

    @RequestMapping(value = "/search")
    public @ResponseBody Map<String, Object> search(@RequestParam(value = "key") String search) {
        Map<String, Object> map = new HashMap<>();
        List<Product> results = ((ProductService)baseService).findByNameLike(search);
        map.put("results", ((ProductService)baseService).findByNameLike(search));
        map.put("length", results.size());
        return map;
    }
}