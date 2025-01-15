package com.example.Module_OrderManagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.Module_OrderManagement.entities.ChiTietHoaDon;
import com.example.Module_OrderManagement.entities.HoaDon;
import com.example.Module_OrderManagement.entities.KhachHang;
import com.example.Module_OrderManagement.entities.NhanVien;
import com.example.Module_OrderManagement.entities.ThucDon;
import com.example.Module_OrderManagement.repository.ChiTietHoaDonRepository;
import com.example.Module_OrderManagement.repository.HoaDonRepository;
import com.example.Module_OrderManagement.respronse.ResponseHoaDon;

@Service
public class ChiTietHoaDonService {

	@Autowired
	private ChiTietHoaDonRepository chiTietHDRepo;

	@Autowired
	private HoaDonRepository hoaDonRepo;

	@Autowired
	private ChiTietHoaDonRepository chiTietHoaDonRepo;

	// lưu các món ăn vào hóa đơn chi tiết
	public boolean saveDetailBill(List<ThucDon> thucDon) {

		// tìm hóa đơn có mã hóa đơn cao nhất hiện tại
		HoaDon hoaDon = hoaDonRepo.findTopByOrderByMaHoaDonDesc();

		// mỗi lần lặp sẽ lưu vào dtb
		for (ThucDon td : thucDon) {

			// khởi tạo đối tượng chi tiết hóa đơn mới
			ChiTietHoaDon detailBill = new ChiTietHoaDon();

			// set các thuộc tính
			detailBill.setMaHoaDon(hoaDon);
			detailBill.setMaMonAn(td.getMaMonAn());
			detailBill.setSoLuong(td.getSoLuong());
			detailBill.setTongTien(td.getTongTien());

			// đặt biến status để kiểm tra thêm thành công không
			ChiTietHoaDon status = chiTietHDRepo.save(detailBill);

			if (status == null) {
				return false;
			}
		}

		return true;
	}

	public List<Object[]> findQuantityProductSell() {
		Pageable pageable = PageRequest.of(0, 10);
		return chiTietHoaDonRepo.findQuantityProductSell(pageable);
	}

	public ResponseEntity<Object> getChiTietHoaDon(HoaDon hoaDon) {
		RestTemplate rest = new RestTemplate();
		List<ResponseHoaDon> list = new ArrayList<ResponseHoaDon>();
		// get khách hàng
		String urlkh = "http://localhost:8081/khachhang/sdt/" + hoaDon.getSoDT();
		KhachHang khachHang = rest.exchange(urlkh, HttpMethod.GET, null, KhachHang.class).getBody();
		if (khachHang == null) {
			return new ResponseEntity<>("Khách hàng không tồn tại", HttpStatus.NOT_FOUND);
		}
		// get nhân viên
		String urlnv = "http://localhost:8082/nhanvien/" + hoaDon.getMaNhanVien();
		NhanVien nhanVien = rest.exchange(urlnv, HttpMethod.GET, null, NhanVien.class).getBody();
		if (nhanVien == null) {
			return new ResponseEntity<>("Nhân viên không tồn tại", HttpStatus.NOT_FOUND);
		}

		// lấy list chi tiết hóa đơn theo hóa đơn
		List<ChiTietHoaDon> listCTHD = chiTietHDRepo.findByHoaDon(hoaDon);
		// get món ăn theo cthd
		for (ChiTietHoaDon cthd : listCTHD) {
			String urltd = "http://localhost:8083/thucdon/monans/" + cthd.getMaMonAn();
			ThucDon thucDon = rest.exchange(urltd, HttpMethod.GET, null, ThucDon.class).getBody();
			ResponseHoaDon rspHoaDon = new ResponseHoaDon();
			rspHoaDon.setGiaMonAn(thucDon.getGiaMonAn());
			rspHoaDon.setTenKhachHang(khachHang.getTenKhachHang());
			rspHoaDon.setTongDiemTichLuy(khachHang.getTongDiemTichLuy());
			rspHoaDon.setTenNhanVien(nhanVien.getTenNhanVien());
			rspHoaDon.setTenMonAn(thucDon.getTenMonAn());
			rspHoaDon.setSoLuong(cthd.getSoLuong());
			rspHoaDon.setTongTien(cthd.getTongTien());
			rspHoaDon.setSoDT(khachHang.getSoDT());
			list.add(rspHoaDon);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
