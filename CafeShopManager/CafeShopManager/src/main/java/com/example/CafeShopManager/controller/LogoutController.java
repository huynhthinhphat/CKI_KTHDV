package com.example.CafeShopManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.CafeShopManager.service.LogoutService;

@Controller
@RequestMapping("/logout")
public class LogoutController {

	@Autowired
	private LogoutService service;
	
	@GetMapping()
	public String logout() {
		service.logout();
		return "views/login";
	}
}
