package com.example.Module_OrderManagement.respronse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ResponseHoaDon {
	private String soDT; 
	private String tenKhachHang; 
	private int tongDiemTichLuy; 
	private int diemTichLuyDaDung;
	private String tenNhanVien;
	private String tenMonAn;
	private int giaMonAn;
	private int tongTien;
	private int soLuong;
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
	public int getTongDiemTichLuy() {
		return tongDiemTichLuy;
	}
	public void setTongDiemTichLuy(int tongDiemTichLuy) {
		this.tongDiemTichLuy = tongDiemTichLuy;
	}
	public int getDiemTichLuyDaDung() {
		return diemTichLuyDaDung;
	}
	public void setDiemTichLuyDaDung(int diemTichLuyDaDung) {
		this.diemTichLuyDaDung = diemTichLuyDaDung;
	}
	public String getTenNhanVien() {
		return tenNhanVien;
	}
	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
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
	public int getTongTien() {
		return tongTien;
	}
	public void setTongTien(int tongTien) {
		this.tongTien = tongTien;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

}
