package com.example.Module_MenuManagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DanhMuc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int maDanhMuc;

	private String tenDanhMuc;

	public int getMaDanhMuc() {
		return maDanhMuc;
	}

	public void setMaDanhMuc(int maDanhMuc) {
		this.maDanhMuc = maDanhMuc;
	}

	public String getTenDanhMuc() {
		return tenDanhMuc;
	}

	public void setTenDanhMuc(String tenDanhMuc) {
		this.tenDanhMuc = tenDanhMuc;
	}
}

