package com.example.Module_ReportingManagement.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReportProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sTT;
	private String tenSanPham;
	private BigDecimal tongSoLuongDaBan;

	public int getsTT() {
		return sTT;
	}

	public void setsTT(int sTT) {
		this.sTT = sTT;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public BigDecimal getTongSoLuongDaBan() {
		return tongSoLuongDaBan;
	}

	public void setTongSoLuongDaBan(BigDecimal tongSoLuongDaBan) {
		this.tongSoLuongDaBan = tongSoLuongDaBan;
	}
}
