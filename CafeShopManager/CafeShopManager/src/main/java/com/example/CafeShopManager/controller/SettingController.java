package com.example.CafeShopManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setting")
public class SettingController {

	@GetMapping()
	public String setting() {
		return "views/setting";
	}
}
