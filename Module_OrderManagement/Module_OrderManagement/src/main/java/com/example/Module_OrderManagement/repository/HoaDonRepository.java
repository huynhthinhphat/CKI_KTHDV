package com.example.Module_OrderManagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Module_OrderManagement.entities.HoaDon;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
	HoaDon findTopByOrderByMaHoaDonDesc();

	@Query("SELECT DATE(hd.thoiGianVao), SUM(hd.tongTien) FROM HoaDon hd WHERE DATE(hd.thoiGianVao) <= CURRENT_DATE GROUP BY DATE(hd.thoiGianVao) ORDER BY DATE(hd.thoiGianVao) DESC")
	List<Object[]> findTotalAmountByDate(Pageable pageable);

	@Query("SELECT EXTRACT(MONTH FROM hd.thoiGianVao) AS month, SUM(hd.tongTien) FROM HoaDon hd "
			+ "WHERE EXTRACT(YEAR FROM hd.thoiGianVao) = EXTRACT(YEAR FROM CURRENT_DATE) "
			+ "GROUP BY EXTRACT(MONTH FROM hd.thoiGianVao) ORDER BY month DESC")
	List<Object[]> findTotalAmountByMonthInCurrentYear();
	List<HoaDon> findBySoDT(String sdt);


}
