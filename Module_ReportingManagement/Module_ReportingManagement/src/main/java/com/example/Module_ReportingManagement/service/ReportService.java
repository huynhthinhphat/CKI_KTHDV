package com.example.Module_ReportingManagement.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.Module_ReportingManagement.entities.HoaDon;
import com.example.Module_ReportingManagement.entities.ReportDate;
import com.example.Module_ReportingManagement.entities.ReportMonth;
import com.example.Module_ReportingManagement.entities.ReportProduct;
import com.example.Module_ReportingManagement.entities.ThucDon;
import com.example.Module_ReportingManagement.repository.ReportDateRepository;
import com.example.Module_ReportingManagement.repository.ReportMonthRepository;
import com.example.Module_ReportingManagement.repository.ReportProductRepository;

@Service
public class ReportService {
	@Autowired
	private ReportProductRepository reportProductRepo;

	@Autowired
	private ReportDateRepository reportDateRepo;

	@Autowired
	private ReportMonthRepository reportMonthRepo;

	@Autowired
	private RestTemplate rest;

	public List<Object[]> findQuantityProductSell() {
		String url = "http://localhost:8084/chitiethoadon/map/quantity";

		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Object[]>>() {
		}).getBody();
	}

	public void saveReportPorduct() {
		List<Object[]> list_product = findQuantityProductSell();
		List<ThucDon> list_menu = findNameProduct();

		for (Object[] product : list_product) {
			for (ThucDon td : list_menu) {
				if (Integer.valueOf((Integer) product[0]) == td.getMaMonAn()) {
					Optional<ReportProduct> check = checkReportProduct(td.getTenMonAn());
					if (!check.isPresent()) {
						ReportProduct rp = new ReportProduct();
						rp.setTenSanPham(td.getTenMonAn());
						rp.setTongSoLuongDaBan(new BigDecimal((Integer) product[1]));
						reportProductRepo.save(rp);
					} else {
						check.get().setTongSoLuongDaBan(new BigDecimal((Integer) product[1]));
						reportProductRepo.save(check.get());
					}
				}
			}
		}
	}

	public Optional<ReportProduct> checkReportProduct(String tenSanPham) {
		return reportProductRepo.findByTenSanPham(tenSanPham);
	}

	public List<ReportProduct> getReportProduct() {
		return reportProductRepo.findAll();
	}

	public List<ThucDon> findNameProduct() {
		String url = "http://localhost:8083/thucdon";

		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ThucDon>>() {
		}).getBody();
	}

	public List<Object[]> findAllOrder() {
		String url = "http://localhost:8084/hoadon/date";

		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Object[]>>() {
		}).getBody();
	}

	public void saveOrder() throws ParseException {
		List<Object[]> list = findAllOrder();
		for (Object[] item : list) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse((String) item[0]);

			Optional<ReportDate> rd = checkReportDate(date);
			if (!rd.isPresent()) {
				ReportDate rp = new ReportDate();
				rp.setTime(date);
				rp.setTongTien(new BigDecimal(Double.valueOf((Double) item[1])));

				reportDateRepo.save(rp);
			} else {
				rd.get().setTongTien(new BigDecimal(Double.valueOf((Double) item[1])));
				reportDateRepo.save(rd.get());
			}
		}
	}

	public List<Object[]> getAllOrder() {
		return findAllOrder();
	}

	public Optional<ReportDate> checkReportDate(Date time) {
		return reportDateRepo.findByTime(time);
	}

	public List<ReportDate> getAllReportDate() {
		return reportDateRepo.findAll();
	}

	public List<Object[]> findAllReportMonth() {
		String url = "http://localhost:8084/hoadon/month";

		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Object[]>>() {
		}).getBody();
	}

	public void saveReportMonth() {
		List<Object[]> list = findAllReportMonth();
		for (Object[] item : list) {

			Optional<ReportMonth> check = checkReportMonth(Integer.valueOf((Integer) item[0]));
			if (!check.isPresent()) {
				ReportMonth rm = new ReportMonth();
				rm.setMonth(Integer.valueOf((Integer) item[0]));
				rm.setTongTien(new BigDecimal(Double.valueOf((Double) item[1])));
				reportMonthRepo.save(rm);
			} else {
				check.get().setTongTien(new BigDecimal(Double.valueOf((Double) item[1])));
				reportMonthRepo.save(check.get());
			}
		}
	}

	public List<ReportMonth> getReportMonth() {
		return reportMonthRepo.findAll();
	}

	public Optional<ReportMonth> checkReportMonth(int month) {
		return reportMonthRepo.findByMonth(month);
	}
}
