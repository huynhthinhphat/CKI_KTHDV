package com.example.Module_InventoryManagement.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.Module_InventoryManagement.entities.Kho;
import com.example.Module_InventoryManagement.entities.LichSuXuatNhapKho;
import com.example.Module_InventoryManagement.repository.KhoRepository;
import com.example.Module_InventoryManagement.repository.LichSuXuatNhapKhoRepository;

@Service
public class KhoService {

	@Autowired
	private KhoRepository repo;
	
	@Autowired
	private LichSuXuatNhapKhoRepository lichSuRepo;
	
//	@Autowired
//	private RestTemplate rest;

	public List<Kho> findAll() {
		return repo.findAll();
	}

	public ResponseEntity<Object> findById(int id) {

		Optional<Kho> kho = repo.findById(id);

		if (!kho.isPresent()) {
			return new ResponseEntity<>("Mã nguyên liệu không tồn tại", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(kho.get(), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> findByName(String name) {

		List<Kho> kho = repo.findByTenSanPhamContaining(name);

		if (kho.size()<=0) {
			return new ResponseEntity<>("Tên nguyên liệu không tồn tại", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(kho, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> findBySoLuongNhoHon(int soLuong){
		List<Kho> list = repo.findBySoLuongLessThan(soLuong);
		if(list.size()<=0) {
			return new ResponseEntity<>("Không có sản phẩm nào", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list,HttpStatus.OK);
	}

	public ResponseEntity<Object> addInventory(Kho khoReq) {

		Optional<Kho> kho = repo.findByTenSanPham(khoReq.getTenSanPham());

		if (kho.isPresent()) {
			return new ResponseEntity<>("Tên nguyên liệu đã tồn tại", HttpStatus.CONFLICT);
		}
		if(khoReq.getSoLuong()<0) {
			khoReq.setSoLuong(0);
		}

		khoReq.setThoiGianTao();
		return new ResponseEntity<>(repo.save(khoReq), HttpStatus.CREATED);
	}

	public ResponseEntity<Object> updateInventorySL(int id, int quantityChange) {

		Optional<Kho> khoSearch = repo.findById(id);

		if (!khoSearch.isPresent()) {
			return new ResponseEntity<>("Mã nguyên liệu không tồn tại", HttpStatus.NOT_FOUND);
		}

//		Optional<Kho> tenNguyenLieu = repo.findByTenSanPham(khoReq.getTenSanPham());
//
//		if (tenNguyenLieu.isPresent()) {
//			return new ResponseEntity<>("Tên nguyên liệu đã tồn tại", HttpStatus.CONFLICT);
//		}

		Kho khoUpdate = khoSearch.get();
		//Tạo lịch sử kho, lấy số lượng trước khi sửa
		LichSuXuatNhapKho lichSuXNKho = new LichSuXuatNhapKho();
		
		lichSuXNKho.setKho(khoUpdate);
		lichSuXNKho.setSoLuongTruoc(khoUpdate.getSoLuong());
		
		
		if(khoUpdate.getSoLuong()+quantityChange<0) {
			return new ResponseEntity<>("Lỗi", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		double soLuong = quantityChange+khoUpdate.getSoLuong();
		khoUpdate.setSoLuong(soLuong);
		
		//update kho
		Kho kho = repo.save(khoUpdate);
		
		//lấy lịch sử kho sau update
		
		lichSuXNKho.setSoLuongSau(kho.getSoLuong());
		lichSuXNKho.setThoiGianTao();
		lichSuRepo.save(lichSuXNKho);
		
		return new ResponseEntity<>(kho, HttpStatus.OK);

	
	}
	
	public ResponseEntity<Object> updateInventory(int id, Kho khoReq) {

		Optional<Kho> khoSearch = repo.findById(id);

		if (!khoSearch.isPresent()) {
			return new ResponseEntity<>("Mã nguyên liệu không tồn tại", HttpStatus.NOT_FOUND);
		}

		Optional<Kho> tenNguyenLieu = repo.findByTenSanPham(khoReq.getTenSanPham());

		if (tenNguyenLieu.isPresent()) {
			return new ResponseEntity<>("Tên nguyên liệu đã tồn tại", HttpStatus.CONFLICT);
		}

		Kho khoUpdate = khoSearch.get();
		
		
		String tenSP = khoReq.getTenSanPham().isEmpty() ? khoUpdate.getTenSanPham() : khoReq.getTenSanPham();
		double soLuong = khoReq.getSoLuong() < 0  ? khoUpdate.getSoLuong() : khoReq.getSoLuong();
		
		khoUpdate.setTenSanPham(tenSP);
		khoUpdate.setSoLuong(soLuong);
			
		return new ResponseEntity<>(repo.save(khoUpdate), HttpStatus.OK);

	
	}
	
	public ResponseEntity<Object> deleteInventory(int id){
		Optional<Kho> khoSearch = repo.findById(id);
		
		if (khoSearch.isEmpty()) {
			return new ResponseEntity<>("Nguyên liệu không tồn tại", HttpStatus.NOT_FOUND);
		}

		repo.deleteById(id);

		Optional<Kho> khoDelete = repo.findById(id);

		if (khoDelete.isPresent()) {
			return new ResponseEntity<>("Xóa thất bại", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
	
	}

//	public ResponseEntity<Object> findMenuById(int id) {
//		String url = "http://localhost:8083/thucdon/" + id;
//		
//		return rest.exchange(url, HttpMethod.GET, null, Object.class);
//	}
}
