package com.example.CafeShopManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CafeShopManager.entities.KhachHang;
import com.example.CafeShopManager.service.HomeService;
import com.example.CafeShopManager.service.KhachHangService;

@RestController
@RequestMapping("/khachhang")
public class CustomerRestController {

	@Autowired
	private HomeService homeService;

	@Autowired
	private KhachHangService khachHangService;

	@GetMapping("/{numberPhone}")
	public KhachHang findCustomerByNumberrPhone(@PathVariable String numberPhone) {
		KhachHang khachHang = homeService.findCustomerByNumberPhone(numberPhone);
		khachHangService.addCustomerBill(khachHang);
		return khachHangService.getCustomerBill();
	}

	@GetMapping()
	public KhachHang findCustomer() {
		return khachHangService.getCustomerBill();
	}

	@PostMapping()
	public KhachHang addCustomer(@RequestParam String soDT, @RequestParam String tenKhachHang) {
		//gửi yêu cầu để thêm khách hàng mới
		KhachHang kh = homeService.addCustomer(soDT, tenKhachHang);
		
		//nếu khách hàng tạo thành công thì set session
		if(kh != null) {
			khachHangService.addCustomerBill(kh);
		}
		return khachHangService.getCustomerBill();
	}
}
