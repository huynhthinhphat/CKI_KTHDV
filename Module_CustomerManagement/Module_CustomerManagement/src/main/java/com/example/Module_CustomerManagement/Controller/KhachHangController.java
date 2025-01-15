package com.example.Module_CustomerManagement.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Module_CustomerManagement.Service.KhachHangService;
import com.example.Module_CustomerManagement.entities.KhachHang;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/khachhang")
public class KhachHangController {
	@Autowired
	private KhachHangService service;
	
	@GetMapping("/sdt/{sdt}")
	public ResponseEntity<Object> findBySDT(@PathVariable String sdt){
		try {
			return service.findBySoDT(sdt);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/ten/{ten}")
	public ResponseEntity<Object> findByTenKhachHang(@PathVariable String ten){
		try {
			return service.findByTenKhachHang(ten);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	/*
	 * @PostMapping() public ResponseEntity<Object> themKhachHang(@RequestBody
	 * KhachHang khachHang){ try { return service.themKhachHang(khachHang.getSoDT(),
	 * khachHang.getTenKhachHang()); } catch (Exception e) { return new
	 * ResponseEntity<>(HttpStatus.BAD_REQUEST); } }
	 * 
	 * @PutMapping() public ResponseEntity<Object> updateKhachHang(@RequestBody
	 * KhachHang khachHang){ try { return service.updateKhachHang(khachHang); }
	 * catch (Exception e) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
	 * }
	 */
	
	@GetMapping("/{numberPhone}")
	public Optional<KhachHang> findCustomerByNumberPhone(@PathVariable String numberPhone){
		return service.findCustomerByNumberphone(numberPhone);
	}
	
	@PostMapping()
	public KhachHang addCustomer(@RequestBody KhachHang khachHang) {
		return service.addCustomer(khachHang);
	}
	
	@PutMapping()
	public KhachHang updateCustomer(@RequestBody KhachHang khachHang) {
		return service.updateCustomer(khachHang);
	}
	
	@GetMapping("/reset")
	public boolean reset(){
		return service.reset();
	}
	
}
