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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Module_MenuManagement.entities.DanhMuc;
import com.example.Module_MenuManagement.entities.ThucDon;
import com.example.Module_MenuManagement.service.ThucDonService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/thucdon")
public class ThucDonController {

	@Autowired
	private ThucDonService service;
	
	@GetMapping("danhmuc/{id}")
	public ResponseEntity<List<ThucDon>> findByDanhMuc(@PathVariable int id){
		try {
			List<ThucDon> listThucDon = service.findThucDonByCategory(id);
			if(listThucDon.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else {
				return new ResponseEntity<>(listThucDon,HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * @GetMapping("/{id}") public ResponseEntity<ThucDon> findById(@PathVariable
	 * int id) { try { Optional<ThucDon> thucDon = service.findById(id);
	 * 
	 * if (thucDon.isPresent()) { return new ResponseEntity<>(thucDon.get(),
	 * HttpStatus.OK); } return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 * 
	 * } catch (Exception e) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	 * } }
	 */
	@GetMapping("/monans/{id}")
	public ResponseEntity<ThucDon> findById(@PathVariable int id) {
		try {
			Optional<ThucDon> thucDon = service.findById(id);

			if (thucDon.isPresent()) {
				return new ResponseEntity<>(thucDon.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<Object> search(@RequestParam String name) {
		try {
			return service.findByName(name);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping()
	public ResponseEntity<Object> addMenu(@RequestBody ThucDon thucDonReq) {
		try {
			return service.addMenu(thucDonReq);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateMenu(@PathVariable int id, @RequestBody ThucDon thucDonReq) {
		try {
			return service.updateMenu(id, thucDonReq);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMenu(@PathVariable int id) {
		try {
			return service.deleteMenu(id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping()
	public List<ThucDon> findAll() {
		return service.findAll();
	}
	

	@GetMapping("/{maDanhMuc}")
	public List<ThucDon> findMenuByMaDM(@PathVariable int maDanhMuc) {
		return service.findMenuByMaDM(maDanhMuc);
	}
	
	@GetMapping("/monan/{tenMonAn}")
	public List<ThucDon> findMenuByName(@PathVariable String tenMonAn) {
		return service.findMenuByName(tenMonAn);
	}
}
