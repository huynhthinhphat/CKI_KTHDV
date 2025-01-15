package com.example.CafeShopManager.entities;

import java.time.LocalDateTime;

public class NhanVien {
	private int maNhanVien;
	private String taiKhoan;
	private String matKhau;
	private String tenNhanVien;
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

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
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
