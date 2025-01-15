package com.example.Module_CustomerManagement.Service;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Module_CustomerManagement.Repository.KhachHangRepository;
import com.example.Module_CustomerManagement.entities.KhachHang;

@Service
public class KhachHangService {
	@Autowired
	private KhachHangRepository repo;

	public ResponseEntity<Object> themKhachHang(String SDT, String tenKhachHang) {
		Optional<KhachHang> khachHangSearch = repo.findById(SDT);

		if (khachHangSearch.isPresent()) {
			return new ResponseEntity<>("Số điện thoại này đã có trong hệ thống", HttpStatus.CONFLICT);
		}
		KhachHang khachHang = new KhachHang();

		khachHang.setSoDT(SDT);
		khachHang.setTenKhachHang(tenKhachHang);
		khachHang.setTongTienTichLuy(BigDecimal.valueOf(0));
		khachHang.setTongDiemTichLuy(0);
		khachHang.setThoiGianTao();

		return new ResponseEntity<>(repo.save(khachHang), HttpStatus.OK);

	}

	public ResponseEntity<Object> findBySoDT(String sdt) {
		Optional<KhachHang> khachHang = repo.findById(sdt);

		if (khachHang.isEmpty()) {
			return new ResponseEntity<>("Khách hàng không tồn tại", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(khachHang.get(), HttpStatus.OK);
	}

	public ResponseEntity<Object> findByTenKhachHang(String tenKhachHang) {
		Optional<KhachHang> khachHang = repo.findByTenKhachHang(tenKhachHang);

		if (khachHang.isEmpty()) {
			return new ResponseEntity<>("Khách hàng không tồn tại", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(khachHang.get(), HttpStatus.OK);
	}

	public ResponseEntity<Object> updateKhachHang(KhachHang khachHang) {
		Optional<KhachHang> khachHangSearch = repo.findById(khachHang.getSoDT());
		if (khachHangSearch.isEmpty()) {
			return new ResponseEntity<>("Khách hàng không tồn tại", HttpStatus.NOT_FOUND);
		}
		khachHangSearch.get().setTongTienTichLuy(khachHang.getTongTienTichLuy());
		khachHangSearch.get().setTongDiemTichLuy(khachHang.getTongDiemTichLuy());
		khachHangSearch.get().setThoiGianCapNhat();
		return new ResponseEntity<>(repo.save(khachHangSearch.get()), HttpStatus.OK);
	}

	public Optional<KhachHang> findCustomerByNumberphone(String numberPhone) {
		return repo.findById(numberPhone);
	}

	public KhachHang addCustomer(KhachHang khachHang) {
		khachHang.setTongDiemTichLuy(0);
		khachHang.setTongTienTichLuy(BigDecimal.valueOf(0));
		return repo.save(khachHang);
	}

	public KhachHang updateCustomer(KhachHang khachHang) {
		khachHang.setThoiGianCapNhat();
		return repo.save(khachHang);
	}

	@Transactional
	public boolean reset() {
		if (repo.reset() > 0) {
			return true;
		}
		return false;
	}
}
