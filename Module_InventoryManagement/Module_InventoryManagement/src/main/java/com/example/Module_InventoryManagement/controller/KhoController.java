package com.example.Module_InventoryManagement.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Module_InventoryManagement.entities.Kho;
import com.example.Module_InventoryManagement.service.KhoService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/kho")
public class KhoController {

	@Autowired
	private KhoService service;

	@GetMapping()
	public List<Kho> findAll() {
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
	
	@GetMapping("/search")
	public ResponseEntity<Object> findByName(@RequestParam("name") String name){
		try {
			return service.findByName(name);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/soluong/{soluong}")
	public ResponseEntity<Object> findBySoLuongNhoHon(@PathVariable int soluong) {
		try {
			return service.findBySoLuongNhoHon(soluong);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateInventorySL(@PathVariable int id,
			@RequestParam("quantityChange") int quantityChange) {
		try {
			return service.updateInventorySL(id, quantityChange);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateInventory(@PathVariable int id, @RequestBody Kho kho) {
		try {
			return service.updateInventory(id, kho);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping()
	public ResponseEntity<Object> addInventory(@RequestBody Kho kho) {
		try {
			return service.addInventory(kho);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteInventory(@PathVariable int id) {
		try {
			return service.deleteInventory(id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
