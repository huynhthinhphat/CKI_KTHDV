package com.example.CafeShopManager.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HoaDon {
	private int maHoaDon;
	private int maNhanVien;
	private String soDT;
	private BigDecimal tongTien;
	private LocalDateTime thoiGianVao;
	private LocalDateTime thoiGianRa;
	private int diemTichLuyDaDung;

	public int getDiemTichLuyDaDung() {
		return diemTichLuyDaDung;
	}

	public void setDiemTichLuyDaDung(int diemTichLuyDaDung) {
		this.diemTichLuyDaDung = diemTichLuyDaDung;
	}

	public int getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(int maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public int getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(int maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public BigDecimal getTongTien() {
		return tongTien;
	}

	public void setTongTien(BigDecimal tongTien) {
		this.tongTien = tongTien;
	}

	public LocalDateTime getThoiGianVao() {
		return thoiGianVao;
	}

	public void setThoiGianVao() {
		this.thoiGianVao = LocalDateTime.now();
	}

	public LocalDateTime getThoiGianRa() {
		return thoiGianRa;
	}

	public void setThoiGianRa() {
		this.thoiGianRa = LocalDateTime.now();
	}
}
