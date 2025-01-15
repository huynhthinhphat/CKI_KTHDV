package com.example.CafeShopManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CafeShopManager.entities.KhachHang;

import jakarta.servlet.http.HttpSession;

@Service
public class KhachHangService {
	
	@Autowired
	private HttpSession session;
	
	public KhachHang getCustomerBill() {
		KhachHang khachHang = (KhachHang) session.getAttribute("customer");
		return khachHang;
	}
	
	public void addCustomerBill(KhachHang khachHang) {
		session.setAttribute("customer", khachHang);
	}
	
	public void clearCustomer() {
		session.removeAttribute("customer");
	}	
}
