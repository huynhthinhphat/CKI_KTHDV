//package com.example.Module_UserManagement.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.example.Module_UserManagement.entities.NhanVien;
//import com.example.Module_UserManagement.repository.NhanVienRepository;
//
//@Service
//public class NhanVienService {
//
//	@Autowired
//	private NhanVienRepository repo;
//
//	public List<NhanVien> findAll() {
//		return repo.findAll();
//	}
//
//	public ResponseEntity<Object> findById(int id) {
//		Optional<NhanVien> nhanVien = repo.findById(id);
//
//		if (nhanVien == null) {
//			return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.NOT_FOUND);
//		}
//
//		return new ResponseEntity<>(nhanVien.get(), HttpStatus.OK);
//	}
//
//	public ResponseEntity<Object> addEmployee(NhanVien nhanVien) {
//
//		if (checkExistAccount(nhanVien.getTaiKhoan())) {
//			nhanVien.setThoiGianTao();
//			return new ResponseEntity<>(repo.save(nhanVien), HttpStatus.CREATED);
//		}
//
//		return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.NOT_FOUND);
//	}
//
//	public ResponseEntity<Object> login(NhanVien nhanVienReq) {
//		NhanVien nhanVien = repo.login(nhanVienReq.getTaiKhoan(), nhanVienReq.getMatKhau());
//
//		if (nhanVien == null) {
//			return new ResponseEntity<>("Tài khoản hoặc mật khẩu không đúng", HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<>(nhanVien, HttpStatus.OK);
//	}
//
//	private Boolean checkExistAccount(String taikhoan) {
//		NhanVien checkExistAccount = repo.checkExistAccount(taikhoan);
//
//		if (checkExistAccount != null) {
//			return false;
//		}
//		return true;
//	}
//
//	public ResponseEntity<Object> updateEmployee(int id, NhanVien nhanVien) {
//
//		Optional<NhanVien> nhanVienUpdate = repo.findById(id);
//
//		if (!nhanVienUpdate.isPresent()) {
//			return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.NOT_FOUND);
//		}
//
//		NhanVien nv = nhanVienUpdate.get();
//
//		String SoDT = nhanVien.getSoDT() != null && !nhanVien.getSoDT().isEmpty() ? nhanVien.getSoDT() : nv.getSoDT();
//		String MatKhau = nhanVien.getMatKhau() != null && !nhanVien.getMatKhau().isEmpty() ? nhanVien.getMatKhau()
//				: nv.getMatKhau();
//		String TenNV = nhanVien.getTenNhanVien() != null && !nhanVien.getTenNhanVien().isEmpty()
//				? nhanVien.getTenNhanVien()
//				: nv.getTenNhanVien();
//		Boolean VaiTro = nhanVien.getVaiTro() != null ? nhanVien.getVaiTro() : nv.getVaiTro();
//
//		nv.setMatKhau(MatKhau);
//		nv.setSoDT(SoDT);
//		nv.setTenNhanVien(TenNV);
//		nv.setVaiTro(VaiTro);
//		nv.setThoiGianCapNhat();
//
//		return new ResponseEntity<>(repo.save(nv), HttpStatus.CREATED);
//	}
//
//	public ResponseEntity<Object> deleteEmployee(int id) {
//		Optional<NhanVien> nhanVienSearch = repo.findById(id);
//
//		if (!nhanVienSearch.isPresent()) {
//			return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.NOT_FOUND);
//		}
//
//		repo.deleteById(id);
//
//		Optional<NhanVien> nhanVienDelete = repo.findById(id);
//
//		if (nhanVienDelete.isPresent()) {
//			return new ResponseEntity<>("Xóa thất bại", HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
//	}
//}

package com.example.Module_UserManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Module_UserManagement.entities.NhanVien;
import com.example.Module_UserManagement.repository.NhanVienRepository;

@Service
public class NhanVienService {
	@Autowired
	private NhanVienRepository repo;

	public List<NhanVien> findAll() {
		return repo.findAllByRole();
	}

	public ResponseEntity<Object> findById(int id) {
		Optional<NhanVien> nhanVien = repo.findById(id);

		if (nhanVien.isEmpty()) {
			return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(nhanVien.get(), HttpStatus.OK);
	}

	public List<NhanVien> findByNumberPhoneOrName(String keyword) {
		return repo.findByNumberPhoneOrName(keyword);
	}

	public ResponseEntity<Object> addEmployee(NhanVien nhanVien) {

		if (repo.findBySoDT(nhanVien.getSoDT()).isEmpty()) {
			nhanVien.setThoiGianTao();
			nhanVien.setVaiTro(false);
			return new ResponseEntity<>(repo.save(nhanVien), HttpStatus.CREATED);
		}

		return new ResponseEntity<>("Số điện thoại đã tồn tại", HttpStatus.CONFLICT);
	}

	public ResponseEntity<NhanVien> login(NhanVien nhanVienReq) {
		Optional<NhanVien> nhanVien = repo.findBySoDTAndMatKhau(nhanVienReq.getSoDT(), nhanVienReq.getMatKhau());

		if (nhanVien.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(nhanVien.get(), HttpStatus.OK);
	}

	public ResponseEntity<Object> updateEmployee(int id, NhanVien nhanVien) {

		Optional<NhanVien> nhanVienUpdate = repo.findById(id);

		if (!nhanVienUpdate.isPresent()) {
			return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.NOT_FOUND);
		}

		if (repo.findBySoDT(nhanVien.getSoDT()).isPresent()) {
			return new ResponseEntity<>("Số điện thoại đã tồn tại", HttpStatus.CONFLICT);
		}

		NhanVien nv = nhanVienUpdate.get();

		String SoDT = nhanVien.getSoDT() != null && !nhanVien.getSoDT().isEmpty() ? nhanVien.getSoDT() : nv.getSoDT();
		String MatKhau = nhanVien.getMatKhau() != null && !nhanVien.getMatKhau().isEmpty() ? nhanVien.getMatKhau()
				: nv.getMatKhau();
		String TenNV = nhanVien.getTenNhanVien() != null && !nhanVien.getTenNhanVien().isEmpty()
				? nhanVien.getTenNhanVien()
				: nv.getTenNhanVien();
		Boolean VaiTro = nhanVien.getVaiTro() != null ? nhanVien.getVaiTro() : nv.getVaiTro();

		nv.setMatKhau(MatKhau);
		nv.setSoDT(SoDT);
		nv.setTenNhanVien(TenNV);
		nv.setVaiTro(VaiTro);
		nv.setThoiGianCapNhat();

		return new ResponseEntity<>(repo.save(nv), HttpStatus.CREATED);
	}

	public ResponseEntity<Object> deleteEmployee(int id) {
		Optional<NhanVien> nhanVienSearch = repo.findById(id);

		if (!nhanVienSearch.isPresent()) {
			return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.NOT_FOUND);
		}

		repo.deleteById(id);

		Optional<NhanVien> nhanVienDelete = repo.findById(id);

		if (nhanVienDelete.isPresent()) {
			return new ResponseEntity<>("Xóa thất bại", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
	}

}
