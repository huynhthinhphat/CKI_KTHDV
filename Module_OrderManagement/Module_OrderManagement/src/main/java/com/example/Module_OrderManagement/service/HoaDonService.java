package com.example.Module_OrderManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class HoaDonService {

	@Autowired
	private ChiTietHoaDonRepository chiTietHDRepo;
	
	@Autowired
	private HoaDonRepository hoaDonRepo;
	
	//lấy đơn có số thứ tự lớn nhất
	public HoaDon findBillMaxId() {
		return hoaDonRepo.findTopByOrderByMaHoaDonDesc();
	}
	
	//lưu hóa đơn
	public HoaDon saveBill(HoaDon hoaDon) {
		return hoaDonRepo.save(hoaDon);
	}
	
	public List<Object[]> findTotalAmountByDate(){
		Pageable pageable = PageRequest.of(0, 15);
		return hoaDonRepo.findTotalAmountByDate(pageable);
	}
	
	public List<Object[]> findTotalAmountByMonthInCurrentYear(){
		return hoaDonRepo.findTotalAmountByMonthInCurrentYear();
	}

	public List<HoaDon> getAll(){

		return hoaDonRepo.findAll();
	}
	
	public ResponseEntity<Object> findAllBySDT(String sdt){
		List<HoaDon> list = hoaDonRepo.findBySoDT(sdt);
		if(list.size()<=0) {
			return new ResponseEntity<>("Không có hóa đơn nào", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK); 
	}
	
}
