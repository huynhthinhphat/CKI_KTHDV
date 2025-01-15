package com.example.Module_OrderManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Module_OrderManagement.entities.HoaDon;
import com.example.Module_OrderManagement.service.HoaDonService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/hoadon")
public class HoaDonController {

	@Autowired
	private HoaDonService service;

	@GetMapping("/maxid")
	public HoaDon findBillMaxId() {
		return service.findBillMaxId();
	}

	@PostMapping()
	public HoaDon saveBill(@RequestBody HoaDon hoaDon) {
		return service.saveBill(hoaDon);
	}

	@GetMapping("/date")
	public List<Object[]> findTotalAmountByDate() {
		return service.findTotalAmountByDate();
	}

	@GetMapping("/month")
	public List<Object[]> findTotalAmountByMonthInCurrentYear() {
		return service.findTotalAmountByMonthInCurrentYear();
	}
	@GetMapping()
	public List<HoaDon> getAll(){
		return service.getAll();
	}
	
	@GetMapping("/search")
	public ResponseEntity<Object> findAllBySDT(@RequestParam("sdt") String sdt){
		try {
			return service.findAllBySDT(sdt);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
