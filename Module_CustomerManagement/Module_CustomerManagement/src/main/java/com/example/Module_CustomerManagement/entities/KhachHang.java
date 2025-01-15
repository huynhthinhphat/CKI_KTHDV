package com.example.Module_CustomerManagement.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class KhachHang {

	@Id
	@Column(columnDefinition = "VARCHAR(11)")
	private String soDT; 
	
	@Column(columnDefinition = "VARCHAR(100)")
	private String tenKhachHang; 
	
	@Column(precision = 10, scale = 2)
	private BigDecimal tongTienTichLuy; 
	
	@Column(precision = 10, scale = 2)
	private int tongDiemTichLuy; 
	
	private LocalDateTime thoiGianTao; 
	
	private LocalDateTime thoiGianCapNhat;

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public BigDecimal getTongTienTichLuy() {
		return tongTienTichLuy;
	}

	public void setTongTienTichLuy(BigDecimal tongTienTichLuy) {
		this.tongTienTichLuy = tongTienTichLuy;
	}

	public int getTongDiemTichLuy() {
		return tongDiemTichLuy;
	}

	public void setTongDiemTichLuy(int tongDiemTichLuy) {
		this.tongDiemTichLuy = tongDiemTichLuy;
	}

	public LocalDateTime getThoiGianTao() {
		return thoiGianTao;
	}

	public void setThoiGianTao() {
		this.thoiGianTao = LocalDateTime.now();
	}

	public LocalDateTime getThoiGianCapNhat() {
		return thoiGianCapNhat;
	}

	public void setThoiGianCapNhat() {
		this.thoiGianCapNhat = LocalDateTime.now();
	} 
}
