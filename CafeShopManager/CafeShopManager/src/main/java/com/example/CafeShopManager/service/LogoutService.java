package com.example.CafeShopManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class LogoutService {

	@Autowired
	private HttpSession session;
	
	public void logout() {
		session.removeAttribute("employee");
	}
}
