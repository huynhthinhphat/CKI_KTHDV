package com.example.Module_ReportingManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Module_ReportingManagement.entities.ReportProduct;

@Repository
public interface ReportProductRepository extends JpaRepository<ReportProduct, Integer>{

	Optional<ReportProduct> findByTenSanPham(String tenSanPham);
}
