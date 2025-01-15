package com.example.Module_ReportingManagement.entities;

import java.text.DecimalFormat;

public class ThucDon {

	private int maMonAn;
	private String tenMonAn;
	private int giaMonAn;
	private String hinhAnh;
	private int soLuong;
	private int tongTien;

	public int getTongTien() {
		return tongTien;
	}

	public void setTongTien() {
		this.tongTien = giaMonAn * soLuong;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public int getMaMonAn() {
		return maMonAn;
	}

	public void setMaMonAn(int maMonAn) {
		this.maMonAn = maMonAn;
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
}
