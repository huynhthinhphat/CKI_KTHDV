package com.example.Module_MenuManagement.controller;

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

import com.example.Module_MenuManagement.entities.DanhMuc;
import com.example.Module_MenuManagement.service.DanhMucService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/danhmuc")
public class DanhMucController {

	@Autowired
	private DanhMucService danhMucService;

	/*
	 * @GetMapping() public ResponseEntity<List<DanhMuc>> getAllDanhMuc(){ try {
	 * return new ResponseEntity<>(danhMucService.findAll(), HttpStatus.OK); } catch
	 * (Exception e) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); } }
	 */

	@PostMapping()
	public ResponseEntity<Object> addCategory(@RequestBody DanhMuc danhMuc) {
		try {
			return danhMucService.addCategory(danhMuc);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable int id) {
		try {
			Optional<DanhMuc> danhMuc = danhMucService.findById(id);
			if (danhMuc.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(danhMuc.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> updateCategory(@PathVariable int id, @RequestBody DanhMuc danhMuc) {
		try {
			return danhMucService.updateCategory(id, danhMuc);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteCategory(@PathVariable int id) {
		try {
			return danhMucService.deleteCategory(id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// lấy toàn bộ danh mục
	@GetMapping()
	public List<DanhMuc> findAllCategory() {
		return danhMucService.findAllCategory();
	}

}
