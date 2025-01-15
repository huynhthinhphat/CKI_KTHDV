package com.example.CafeShopManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CafeShopManager.entities.NhanVien;
import com.example.CafeShopManager.service.LoginService;

@Controller
@RequestMapping({ "/", "" })
public class LoginController {
	
	@Autowired
	private LoginService service;

	@GetMapping()
	public String view() {
		return "views/login";
	}
	
	@PostMapping()
	public String login(@RequestParam String sodt, @RequestParam String matkhau, Model model) {
		NhanVien nv = new NhanVien();
		nv.setSoDT(sodt);
		nv.setMatKhau(matkhau);
		if(!service.login(nv)) {
			model.addAttribute("msg", "Số điện thoại hoặc mật khẩu sai");
		}else {
			return "redirect:/home";
		}
		return "views/login";
	}
}
