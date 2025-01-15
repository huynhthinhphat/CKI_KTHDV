//package com.example.Module_MenuManagement.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import com.example.Module_MenuManagement.entities.ThucDon;
//import com.example.Module_MenuManagement.repository.ThucDonRepository;
//
//@Service
//public class ThucDonService {
//
//	@Autowired
//	private ThucDonRepository repo;
//
//	@Autowired
//	private RestTemplate rest;
//
//	public List<ThucDon> findAll() {
//		return repo.findAll();
//	}
//
//	public Optional<ThucDon> findById(int id) {
//		return repo.findById(id);
//	}
//
//	public ResponseEntity<Object> addMenu(ThucDon thucDon) {
//
//		if (!checkMenuExist(thucDon.getTenMonAn())) {
//			return new ResponseEntity<>("Tên món ăn đã tồn tại", HttpStatus.CONFLICT);
//		}
//
//		ResponseEntity<Object> response = checkCategoryId(thucDon.getMaDanhMuc());
//		if (!response.getStatusCode().is2xxSuccessful()) {
//			return new ResponseEntity<>("Mã danh mục không tồn tại", HttpStatus.NOT_FOUND);
//		}
//
//		thucDon.setThoiGianTao();
//
//		return new ResponseEntity<>(repo.save(thucDon), HttpStatus.NOT_FOUND);
//	}
//
//	public ResponseEntity<Object> updateMenu(int id, ThucDon thucDon) {
//
//		Optional<ThucDon> thucDonSearch = repo.findById(id);
//
//		if (!thucDonSearch.isPresent()) {
//			return new ResponseEntity<>("Món ăn không tồn tại", HttpStatus.NOT_FOUND);
//		}
//
//		ResponseEntity<Object> response = checkCategoryId(thucDon.getMaDanhMuc());
//		if (!response.getStatusCode().is2xxSuccessful()) {
//			return new ResponseEntity<>("Mã danh mục không tồn tại", HttpStatus.NOT_FOUND);
//		}
//
//		if (!checkMenuExist(thucDon.getTenMonAn())) {
//			return new ResponseEntity<>("Tên món ăn đã tồn tại", HttpStatus.CONFLICT);
//		}
//
//		ThucDon td = thucDonSearch.get();
//
//		String tenmonan = thucDon.getTenMonAn() != null && !thucDon.getTenMonAn().isEmpty() ? thucDon.getTenMonAn()
//				: td.getTenMonAn();
//		int gia = thucDon.getGiaMonAn() > 0 ? thucDon.getGiaMonAn() : td.getGiaMonAn();
//
//		td.setTenMonAn(tenmonan);
//		td.setGiaMonAn(gia);
//		td.setThoiGianCapNhat();
//
//		return new ResponseEntity<>(repo.save(td), HttpStatus.CREATED);
//	}
//
//	public ResponseEntity<Object> deleteMenu(int id) {
//
//		Optional<ThucDon> thucDonSearch = repo.findById(id);
//
//		if (!thucDonSearch.isPresent()) {
//			return new ResponseEntity<>("Món ăn không tồn tại", HttpStatus.NOT_FOUND);
//		}
//
//		repo.deleteById(id);
//
//		Optional<ThucDon> thucDonDelete = repo.findById(id);
//
//		if (thucDonDelete.isPresent()) {
//			return new ResponseEntity<>("Xóa thất bại", HttpStatus.NOT_FOUND);
//		}
//
//		return new ResponseEntity<>("Xóa thành công", HttpStatus.CREATED);
//	}
//
//	private Boolean checkMenuExist(String tenmonan) {
//
//		ThucDon thucDon = repo.checkMenuExist(tenmonan);
//
//		if (thucDon != null) {
//			return false;
//		}
//		return true;
//	}
//
//	private ResponseEntity<Object> checkCategoryId(int id) {
//		String url = "http://localhost:8082/danhmuc/" + id;
//
//		try {
//			return rest.exchange(url, HttpMethod.GET, null, Object.class);
//		} catch (HttpClientErrorException | HttpServerErrorException e) {
//			return ResponseEntity.status(e.getStatusCode()).body("Error: " + e.getMessage());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//		}
//	}
//}

package com.example.Module_MenuManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Module_MenuManagement.entities.DanhMuc;
import com.example.Module_MenuManagement.entities.ThucDon;
import com.example.Module_MenuManagement.repository.ThucDonRepository;

@Service
public class ThucDonService {

	@Autowired
	private ThucDonRepository repo;

	@Autowired
	private DanhMucService danhMucService;

	public Optional<ThucDon> findById(int id) {
		return repo.findById(id);
	}

	public List<ThucDon> findThucDonByCategory(int idDanhMuc) {
		Optional<DanhMuc> danhMuc = danhMucService.findById(idDanhMuc);

		return repo.findByDanhMuc(danhMuc.get());
	}

	public ResponseEntity<Object> findByName(String name){
		List<ThucDon> data = repo.findByTenMonAnContaining(name);
		if(data.size()<=0) {
			return new ResponseEntity<>("Không có món này", HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK); 

	}
	public ResponseEntity<Object> addMenu(ThucDon thucDon) {

		if (!repo.findByTenMonAn(thucDon.getTenMonAn()).isEmpty()) {
			return new ResponseEntity<>("Tên món ăn đã tồn tại", HttpStatus.CONFLICT);
		}

		Optional<DanhMuc> danhMuc = danhMucService.findById(thucDon.getDanhMuc().getMaDanhMuc());
		if (danhMuc.isEmpty()) {
			return new ResponseEntity<>("Mã danh mục không tồn tại", HttpStatus.NOT_FOUND);
		}

		thucDon.setHinhAnh("https://khothietke.net/wp-content/uploads/2021/04/PNGKhothietke.net-02377.png");
		thucDon.setThoiGianTao();

		return new ResponseEntity<>(repo.save(thucDon), HttpStatus.OK);
	}

	public ResponseEntity<Object> updateMenu(int id, ThucDon thucDon) {

		Optional<ThucDon> thucDonSearch = repo.findById(id);

		if (thucDonSearch.isEmpty()) {
			return new ResponseEntity<>("Món ăn không tồn tại", HttpStatus.NOT_FOUND);
		}

		Optional<DanhMuc> danhMuc = danhMucService.findById(thucDon.getDanhMuc().getMaDanhMuc());
		if (danhMuc.isEmpty()) {
			return new ResponseEntity<>("Mã danh mục không tồn tại", HttpStatus.NOT_FOUND);
		}


		ThucDon td = thucDonSearch.get();

		td.setTenMonAn(thucDon.getTenMonAn());
		td.setGiaMonAn(thucDon.getGiaMonAn());
		System.out.println(thucDon.getGiaMonAn());
		td.setDanhMuc(thucDon.getDanhMuc());
		td.setThoiGianCapNhat();
		


		return new ResponseEntity<>(repo.save(td), HttpStatus.OK);
	}

	public ResponseEntity<Object> deleteMenu(int id) {

		Optional<ThucDon> thucDonSearch = repo.findById(id);

		if (thucDonSearch.isEmpty()) {
			return new ResponseEntity<>("Món ăn không tồn tại", HttpStatus.NOT_FOUND);
		}

		repo.deleteById(id);

		Optional<ThucDon> thucDonDelete = repo.findById(id);

		if (thucDonDelete.isPresent()) {
			return new ResponseEntity<>("Xóa thất bại", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
	}

	public List<ThucDon> findAll() {
		return repo.findAll();
	}

	public List<ThucDon> findMenuByMaDM(int maDanhMuc) {
		return repo.findMenuByMaDM(maDanhMuc);
	}

	public List<ThucDon> findMenuByName(String tenMonAn) {
		return repo.findMenuByName(tenMonAn);
	}

}
