package com.example.Module_MenuManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Module_MenuManagement.entities.DanhMuc;
import com.example.Module_MenuManagement.entities.ThucDon;

@Repository
public interface ThucDonRepository extends JpaRepository<ThucDon, Integer>{

//	@Query("SELECT td FROM ThucDon td WHERE td.TenMonAn = :tenmonan")
//	ThucDon checkMenuExist(@Param("tenmonan") String tenmonan);
	
	Optional<ThucDon> findByTenMonAn(String tenmonan);
	List<ThucDon> findByDanhMuc(DanhMuc danhmuc);
	
	List<ThucDon> findByTenMonAnContaining(String tenmonan);

	
	@Query("SELECT td FROM ThucDon td WHERE td.danhMuc.maDanhMuc = :maDanhMuc")
	List<ThucDon> findMenuByMaDM(@Param("maDanhMuc") int maDanhMuc);
	
	@Query("SELECT td FROM ThucDon td WHERE td.tenMonAn LIKE LOWER(CONCAT('%', :tenMonAn, '%'))")
	List<ThucDon> findMenuByName(@Param("tenMonAn") String tenMonAn);
}
