package com.example.Module_MenuManagement.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ThucDon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int maMonAn;

	@ManyToOne
	@JoinColumn(name = "maDanhMuc")
	private DanhMuc danhMuc;
	private String tenMonAn;
	private int giaMonAn;
	private String hinhAnh;
	private LocalDateTime thoiGianTao;
	private LocalDateTime thoiGianCapNhat;

	public int getMaMonAn() {
		return maMonAn;
	}

	public void setMaMonAn(int maMonAn) {
		this.maMonAn = maMonAn;
	}

	public DanhMuc getDanhMuc() {
		return danhMuc;
	}

	public void setDanhMuc(DanhMuc danhMuc) {
		this.danhMuc = danhMuc;
	}

	public String getTenMonAn() {
		return tenMonAn;
	}

	public void setTenMonAn(String tenMonAn) {
		this.tenMonAn = tenMonAn;
	}

	public int getGiaMonAn() {
		return giaMonAn;
	}

	public void setGiaMonAn(int giaMonAn) {
		this.giaMonAn = giaMonAn;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
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
