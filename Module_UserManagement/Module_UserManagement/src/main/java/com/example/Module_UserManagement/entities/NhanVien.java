package com.example.Module_UserManagement.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class NhanVien {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int maNhanVien;

	private String matKhau;

	private String tenNhanVien;

	@Column(columnDefinition = "VARCHAR(11)")
	private String soDT;

	private Boolean vaiTro;

	private LocalDateTime thoiGianTao;

	private LocalDateTime thoiGianCapNhat;

	public int getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(int maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public Boolean getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(Boolean vaiTro) {
		this.vaiTro = vaiTro;
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
