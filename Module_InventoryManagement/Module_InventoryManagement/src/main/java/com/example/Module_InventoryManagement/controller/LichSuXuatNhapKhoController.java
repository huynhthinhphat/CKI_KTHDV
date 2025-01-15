package com.example.Module_InventoryManagement.controller;

import java.security.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Module_InventoryManagement.service.LichSuXuatNhapKhoService;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/lichsuxuatnhapkho")
public class LichSuXuatNhapKhoController {
	@Autowired
	private LichSuXuatNhapKhoService service;
	
	@GetMapping()
	public ResponseEntity<Object> findAll(){
		try {
			return service.findAll();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable int id){
		try {
			return service.findById(id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/kho/{tensp}")
	public ResponseEntity<Object> findByKho(@PathVariable String tensp){
		try {
			return service.findByKho(tensp);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/before/{timebefore}")
	public ResponseEntity<Object> findByThoiGianTaoTruocTime(@PathVariable ("timebefore") LocalDateTime time){
		try {
			return service.findByThoiGianTaoTruocTime(time);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/after/{timeafter}")
	public ResponseEntity<Object> findByThoiGianTaoSauTime(@PathVariable ("timeafter") LocalDateTime time){
		try {
			return service.findByThoiGianTaoSauTime(time);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/time/{time}")
	public ResponseEntity<Object> findByThoiGianTao(@PathVariable LocalDateTime time){
		try {
			return service.findByThoiGianTao(time);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
