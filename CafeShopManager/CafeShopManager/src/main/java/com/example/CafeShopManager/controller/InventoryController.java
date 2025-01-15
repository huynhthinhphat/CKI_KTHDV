package com.example.CafeShopManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
	
	@GetMapping()
	public String inventory() {
		return "views/inventory";
	}
}
