package com.example.Module_ReportingManagement.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Module_ReportingManagement.entities.ReportDate;
import com.example.Module_ReportingManagement.entities.ReportMonth;
import com.example.Module_ReportingManagement.entities.ReportProduct;
import com.example.Module_ReportingManagement.service.ReportService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService service;
	
	@GetMapping("/map/quantity")
	public List<ReportProduct> getReportPorduct(){
		service.saveReportPorduct();
		return service.getReportProduct();
	}
	
	@GetMapping("/map/date")
	public List<ReportDate> getReportDate() throws ParseException{
		service.saveOrder();
		return service.getAllReportDate();
	}
	
	@GetMapping("/map/month")
	public List<ReportMonth> getReportMonth(){
		service.saveReportMonth();
		return service.getReportMonth();
	}
}
