package com.example.Module_InventoryManagement.service;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Module_InventoryManagement.entities.Kho;
import com.example.Module_InventoryManagement.entities.LichSuXuatNhapKho;
import com.example.Module_InventoryManagement.repository.KhoRepository;
import com.example.Module_InventoryManagement.repository.LichSuXuatNhapKhoRepository;

@Service
public class LichSuXuatNhapKhoService {
	@Autowired
	private LichSuXuatNhapKhoRepository repo;
	
	@Autowired
	private KhoRepository khoRepo;
		
	public ResponseEntity<Object> findAll(){
		List<LichSuXuatNhapKho> list = repo.findAll();
		if(list.size()<=0) {
			return new ResponseEntity<>("Không có lịch sử", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> findById(int id){
		Optional<LichSuXuatNhapKho> lichSu =  repo.findById(id);
		if(lichSu.isEmpty()) {
			return new ResponseEntity<>("Không có lịch sử", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(lichSu.get(), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> findByKho(String tensp) {
		Optional<Kho> kho = khoRepo.findByTenSanPham(tensp);
		if(kho.isEmpty()) {
			return new ResponseEntity<>("Không tìm thấy tên sản phẩm", HttpStatus.NOT_FOUND);
		}
		
		List<LichSuXuatNhapKho> list = repo.findByKho(kho.get());
		
		if(list.size()<=0) {
			return new ResponseEntity<>("Không có lịch sử nào", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> findByThoiGianTao(LocalDateTime time){
		List<LichSuXuatNhapKho> list = repo.findByThoiGianTao(time);
		if(list.size()<=0) {
			return new ResponseEntity<>("Không có lịch sử nào", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	public ResponseEntity<Object> findByThoiGianTaoTruocTime(LocalDateTime time){
		List<LichSuXuatNhapKho> list = repo.findByThoiGianTaoLessThan(time);
		if(list.size()<=0) {
			return new ResponseEntity<>("Không có lịch sử nào trước ngày: "+ time, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	public ResponseEntity<Object> findByThoiGianTaoSauTime(LocalDateTime time){
		List<LichSuXuatNhapKho> list = repo.findByThoiGianTaoGreaterThan(time);
		if(list.size()<=0) {
			return new ResponseEntity<>("Không có lịch sử nào sau ngày: "+time, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
