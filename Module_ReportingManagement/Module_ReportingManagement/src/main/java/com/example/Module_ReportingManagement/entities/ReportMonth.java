package com.example.Module_ReportingManagement.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReportMonth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sTT;
	private int month;
	private BigDecimal tongTien;

	public int getsTT() {
		return sTT;
	}

	public void setsTT(int sTT) {
		this.sTT = sTT;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public BigDecimal getTongTien() {
		return tongTien;
	}

	public void setTongTien(BigDecimal tongTien) {
		this.tongTien = tongTien;
	}
}
