package com.example.CafeShopManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.CafeShopManager.service.HomeService;
import com.example.CafeShopManager.service.KhachHangService;
import com.example.CafeShopManager.service.ThucDonService;

@RestController
@RequestMapping("/hoadon")
public class OrderRestController {

	@Autowired
	private HomeService homeService;

	@GetMapping("/{diemTichLuyDaTra}/{diemTichLuyNhanDuoc}")
	public boolean saveToBill(@PathVariable int diemTichLuyDaTra, @PathVariable int diemTichLuyNhanDuoc) {
		return homeService.behaviourBill(diemTichLuyDaTra, diemTichLuyNhanDuoc);
	}
}
