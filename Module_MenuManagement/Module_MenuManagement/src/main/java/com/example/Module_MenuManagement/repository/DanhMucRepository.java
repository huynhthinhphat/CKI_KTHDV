package com.example.Module_MenuManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Module_MenuManagement.entities.DanhMuc;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer>{
	Optional<DanhMuc> findByTenDanhMuc(String tendanhmuc);
	
}
