package com.example.Module_InventoryManagement.repository;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Module_InventoryManagement.entities.Kho;
import com.example.Module_InventoryManagement.entities.LichSuXuatNhapKho;

@Repository
public interface LichSuXuatNhapKhoRepository extends JpaRepository<LichSuXuatNhapKho, Integer>{
		List<LichSuXuatNhapKho> findByKho(Kho kho);
		List<LichSuXuatNhapKho> findByThoiGianTao(LocalDateTime thoigiantao);
		List<LichSuXuatNhapKho> findByThoiGianTaoLessThan(LocalDateTime thoigiantao);
		List<LichSuXuatNhapKho> findByThoiGianTaoGreaterThan(LocalDateTime thoigiantao);
}
