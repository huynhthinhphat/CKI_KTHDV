package com.example.Module_ReportingManagement.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReportDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sTT;
	private Date time;
	private BigDecimal tongTien;

	public int getsTT() {
		return sTT;
	}

	public void setsTT(int sTT) {
		this.sTT = sTT;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public BigDecimal getTongTien() {
		return tongTien;
	}

	public void setTongTien(BigDecimal tongTien) {
		this.tongTien = tongTien;
	}
}
