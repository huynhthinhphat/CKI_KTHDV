package com.example.CafeShopManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CafeShopManager.entities.ThucDon;
import com.example.CafeShopManager.service.HomeService;
import com.example.CafeShopManager.service.ThucDonService;

@RestController
@RequestMapping("/thucdon")
public class MenuRestController {

	@Autowired
	private ThucDonService thucDonService;
	
	@Autowired
	private HomeService homeService;

	@PostMapping()
	public List<ThucDon> addToBill(@RequestBody ThucDon thucDon) {
		thucDonService.addToBill(thucDon);
		return thucDonService.getBill();
	}
	
	@DeleteMapping()
	public List<ThucDon> minusToBill(@RequestBody ThucDon thucDon) {
		thucDonService.minusToBill(thucDon);
		return thucDonService.getBill();
	}
	
	@GetMapping("/clear")
	public List<ThucDon> clearBill() {
		thucDonService.clearBill();
		return thucDonService.getBill();
	}
	
	@GetMapping()
	public List<ThucDon> getBill() {
		return thucDonService.getBill();
	}
	
	@GetMapping("/timkiem/{tenMonAn}")
	public List<ThucDon> searchMenu(@PathVariable String tenMonAn) {
		return homeService.findMenuByName(tenMonAn);
	}
	
}
