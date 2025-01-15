package com.example.Module_InventoryManagement.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class LichSuXuatNhapKho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sTT;

	@ManyToOne
	@JoinColumn(name = "MaSanPham")
	private Kho kho;

	private double soLuongTruoc;
	
	private double soLuongSau;

	private LocalDateTime thoiGianTao;

	public LichSuXuatNhapKho() {
		super();
	}

	public int getSTT() {
		return sTT;
	}

	public void setSTT(int sTT) {
		this.sTT = sTT;
	}

	public Kho getKho() {
		return kho;
	}

	public void setKho(Kho kho) {
		this.kho = kho;
	}

	public double getSoLuongTruoc() {
		return soLuongTruoc;
	}

	public void setSoLuongTruoc(double soLuongTruoc) {
		this.soLuongTruoc = soLuongTruoc;
	}

	public double getSoLuongSau() {
		return soLuongSau;
	}

	public void setSoLuongSau(double soLuongSau) {
		this.soLuongSau = soLuongSau;
	}

	public LocalDateTime getThoiGianTao() {
		return thoiGianTao;
	}

	public void setThoiGianTao() {
		this.thoiGianTao = LocalDateTime.now();
	}
}
