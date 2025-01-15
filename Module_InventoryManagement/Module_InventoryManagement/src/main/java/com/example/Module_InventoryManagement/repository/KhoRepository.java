package com.example.Module_InventoryManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Module_InventoryManagement.entities.Kho;

@Repository
public interface KhoRepository extends JpaRepository<Kho, Integer>{
	List<Kho> findByTenSanPhamContaining(String tensanpham);
	Optional<Kho> findByTenSanPham(String tensanpham);
	List<Kho> findBySoLuongLessThan(int soluong);
}
