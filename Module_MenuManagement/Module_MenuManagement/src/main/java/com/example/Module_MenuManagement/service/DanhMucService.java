package com.example.Module_MenuManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Module_MenuManagement.entities.DanhMuc;
import com.example.Module_MenuManagement.repository.DanhMucRepository;

@Service
public class DanhMucService {

	@Autowired
	private DanhMucRepository repo;
	
	public List<DanhMuc> findAllCategory(){
		//trả về danh sách các danh mục
		return repo.findAll();
	}
	
	public Optional<DanhMuc> findById(int id){
		return repo.findById(id);
	}
	
	public ResponseEntity<Object> addCategory(DanhMuc danhMuc){
		if(repo.findByTenDanhMuc(danhMuc.getTenDanhMuc()).isEmpty()) {
			return new ResponseEntity<>(repo.save(danhMuc), HttpStatus.OK);
		}
		return new ResponseEntity<>("Danh mục này đã tồn tại", HttpStatus.CONFLICT);
	}
	
	public ResponseEntity<Object> updateCategory(int id, DanhMuc danhMuc){
		Optional<DanhMuc> danhMucUpdate = repo.findById(id);
		if(danhMucUpdate.isEmpty()) {
			return new ResponseEntity<>("Danh mục cần update không tồn tại", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(repo.save(danhMuc), HttpStatus.OK);
	} 
	
	public ResponseEntity<Object> deleteCategory(int id){
		Optional<DanhMuc> danhMucSearch = repo.findById(id);
		if(danhMucSearch.isEmpty()) {
			return new ResponseEntity<>("Danh mục không tồn tại", HttpStatus.NOT_FOUND);
		}
		else {
			repo.delete(danhMucSearch.get());
		}
		
		Optional<DanhMuc> danhMucDelete = repo.findById(id);
		
		if(danhMucDelete.isEmpty()) {
			return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Xóa thất bại", HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	

}
