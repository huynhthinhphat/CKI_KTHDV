package com.example.Module_UserManagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Module_UserManagement.entities.NhanVien;
import com.example.Module_UserManagement.service.NhanVienService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/nhanvien")
public class NhanVienController {

	@Autowired
	private NhanVienService service;

	@GetMapping
	public List<NhanVien> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable int id) {
		try {
			return service.findById(id);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/search/{keyword}")
	public List<NhanVien> findByNumberPhoneOrName(@PathVariable String keyword) {
		return service.findByNumberPhoneOrName(keyword);
	}

	@PostMapping("/login")
	public ResponseEntity<NhanVien> login(@RequestBody NhanVien nhanVienReq) {
		try {
			return service.login(nhanVienReq);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping()
	public ResponseEntity<Object> addEmployee(@RequestBody NhanVien nhanVienReq) {
		try {
			return service.addEmployee(nhanVienReq);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable int id, @RequestBody NhanVien nhanVienReq) {
		try {
			System.out.println(nhanVienReq.getSoDT());
			return service.updateEmployee(id, nhanVienReq);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
		try {
			return service.deleteEmployee(id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
