package com.example.Module_OrderManagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Module_OrderManagement.entities.ChiTietHoaDon;
import com.example.Module_OrderManagement.entities.HoaDon;

@Repository
public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, Integer> {

	@Query("SELECT c.maMonAn, SUM(c.soLuong) FROM ChiTietHoaDon c GROUP BY c.maMonAn ORDER BY SUM(c.soLuong) DESC")
	List<Object[]> findQuantityProductSell(Pageable pageable);
	
	List<ChiTietHoaDon> findByHoaDon(HoaDon hoadon);

}
