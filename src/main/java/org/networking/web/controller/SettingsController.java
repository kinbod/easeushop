package org.networking.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.networking.entity.Settings;
import org.networking.service.SettingsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/settings")
public class SettingsController  extends BaseController<Settings> {
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return "admin-settings";
	}

    @Override
    @RequestMapping(value = "/list", produces = {"application/json"})
    public @ResponseBody List<Settings> list() {
        return ((SettingsService)baseService).getAllSettings();
    }

    @RequestMapping(value = "/key/{key}", produces = {"application/json"})
    public @ResponseBody Settings getByKey(@PathVariable String key) {
        return ((SettingsService)baseService).findByKey(key);
    }

    @RequestMapping(value = "/refresh", produces = {"application/json"})
    public @ResponseBody Map<String, Object> refresh() {
        Map<String, Object> map = new HashMap<>();
        ((SettingsService)baseService).init();
        map.put("success", true);
        map.put("records", ((SettingsService)baseService).getAllSettings());
        return map;
    }

    @Override
    protected void postUpdate(Settings settings) {
        ((SettingsService)baseService).updateSettingsCached(settings);
    }

    @Override
    protected void postCreate(Settings settings) {
        ((SettingsService)baseService).updateSettingsCached(settings);
    }
}