package com.example.Module_OrderManagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChiTietHoaDon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sTT;

	@ManyToOne
	@JoinColumn(name = "MaHoaDon")
	private HoaDon hoaDon;

	private int maMonAn;
	private int soLuong;
	private int tongTien;

	public int getSTT() {
		return sTT;
	}

	public void setSTT(int sTT) {
		this.sTT = sTT;
	}

	public HoaDon getMaHoaDon() {
		return hoaDon;
	}

	public void setMaHoaDon(HoaDon HoaDon) {
		this.hoaDon = HoaDon;
	}

	public int getMaMonAn() {
		return maMonAn;
	}

	public void setMaMonAn(int maMonAn) {
		this.maMonAn = maMonAn;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public int getTongTien() {
		return this.tongTien;
	}

	public void setTongTien(int tongTien) {
		this.tongTien = tongTien;
	}
}
