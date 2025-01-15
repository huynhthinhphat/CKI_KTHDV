package com.example.Module_OrderManagement.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Module_OrderManagement.entities.HoaDon;
import com.example.Module_OrderManagement.entities.ThucDon;
import com.example.Module_OrderManagement.service.ChiTietHoaDonService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/chitiethoadon")
public class ChiTietHoaDonController {

	@Autowired
	private ChiTietHoaDonService service;
	
	@PostMapping()
	public boolean saveDetailBill(@RequestBody List<ThucDon> thucDon) {
		return service.saveDetailBill(thucDon);
	}
	
	@GetMapping("/map/quantity")
	public List<Object[]> findQuantityProductSell(){
		return service.findQuantityProductSell();
	}
	@PostMapping("/getcthd")
	public ResponseEntity<Object> getCTHDByHD(@RequestBody HoaDon hoaDon){
		try {
			return service.getChiTietHoaDon(hoaDon);
		} catch (Exception e) {
			return new ResponseEntity<>("Lỗi", HttpStatus.BAD_REQUEST);
		}
	}
}
