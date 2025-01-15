package com.example.Module_CustomerManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Module_CustomerManagement.entities.KhachHang;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, String> {

	Optional<KhachHang> findByTenKhachHang(String tenkhachhang);

	@Modifying
	@Query("UPDATE KhachHang kh SET kh.tongTienTichLuy = 0, kh.tongDiemTichLuy = 0")
	int reset();
}
