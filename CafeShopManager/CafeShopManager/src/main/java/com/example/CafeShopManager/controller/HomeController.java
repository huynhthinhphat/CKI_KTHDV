package com.example.CafeShopManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.CafeShopManager.entities.DanhMuc;
import com.example.CafeShopManager.entities.ThucDon;
import com.example.CafeShopManager.entities.HoaDon;
import com.example.CafeShopManager.service.HomeService;

@Controller
public class HomeController {

	@Autowired
	private HomeService homeService;

	@GetMapping("/home")
	public String home(Model model) {
		// danh sách danh mục
		List<DanhMuc> category = homeService.findAllCategory();
		model.addAttribute("category", category);

		// danh sách thưc đơn
		List<ThucDon> menu = homeService.findAllMenu(1);
		model.addAttribute("menu", menu);

		// lấy số thứ tự của hóa đơn có id lớn nhất
		HoaDon hoaDon = homeService.findBillIdMax();
		// nếu hóa đơn null thì set no == 0, ngược lại thì lấy mã hóa đơn hiện tại
		int no = hoaDon == null ? 0 : hoaDon.getMaHoaDon();
		model.addAttribute("no", no + 1);

		// trả về view trang chủ
		return "views/home";
	}
}
