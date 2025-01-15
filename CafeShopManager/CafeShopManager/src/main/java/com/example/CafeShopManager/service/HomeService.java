package com.example.CafeShopManager.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.CafeShopManager.entities.DanhMuc;
import com.example.CafeShopManager.entities.HoaDon;
import com.example.CafeShopManager.entities.KhachHang;
import com.example.CafeShopManager.entities.NhanVien;
import com.example.CafeShopManager.entities.ThucDon;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class HomeService {

	@Autowired
	private RestTemplate rest;

	@Autowired
	private KhachHangService khachHangService;

	@Autowired
	private ThucDonService thucDonService;
	
	@Autowired
	private HttpServletRequest request;

	public List<DanhMuc> findAllCategory() {
		String url = "http://localhost:8083/danhmuc";

		// Thực hiện GET request và trả về ResponseEntity chứa danh sách danh mục từ
		// port 8083
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DanhMuc>>() {
		}).getBody();
	}

	public List<ThucDon> findAllMenu(int maDanhMuc) {
		String url = "http://localhost:8083/thucdon/" + maDanhMuc;

		// Thực hiện GET request và trả về ResponseEntity chứa danh sách danh mục từ
		// port 8083
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ThucDon>>() {
		}).getBody();
	}

	public HoaDon findBillIdMax() {
		String url = "http://localhost:8084/hoadon/maxid";

		// Thực hiện GET request và trả về ResponseEntity chứa danh sách danh mục từ
		// port 8084
		return rest.exchange(url, HttpMethod.GET, null, HoaDon.class).getBody();
	}

	public KhachHang findCustomerByNumberPhone(String numberPhone) {
		String url = "http://localhost:8081/khachhang/" + numberPhone;

		return rest.exchange(url, HttpMethod.GET, null, KhachHang.class).getBody();
	}

	public KhachHang addCustomer(String soDT, String tenKhachHang) {
		String url = "http://localhost:8081/khachhang";

		KhachHang khachHang = new KhachHang();
		khachHang.setSoDT(soDT);
		khachHang.setTenKhachHang(tenKhachHang);
		khachHang.setThoiGianTao();

		return rest.exchange(url, HttpMethod.POST, new HttpEntity<>(khachHang), KhachHang.class).getBody();
	}

	public List<ThucDon> findMenuByName(String tenMonAn) {
		String url = "http://localhost:8083/thucdon/monan/" + tenMonAn;

		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ThucDon>>() {
		}).getBody();
	}

	// xử lý các hành động liên quan tới tạo hóa đơn (cập nhật tổng tiền, tổng điểm
	// tích lũy của khách hàng, lưu detail bill, lưu bill)
	public boolean behaviourBill(int diemTichLuyDaTra, int diemTichLuyNhanDuoc) {

		// chuyển tỉ lệ 1 điểm tích lũy = 1000
		int convertPoint = diemTichLuyDaTra * 1000;

		// lấy khách hàng từ session
		KhachHang khachHang = khachHangService.getCustomerBill();

		// nếu khách hàng không null thì lưu vào hóa đơn
		if (khachHang != null) {

			// tạo đối tượng cần lưu vào hóa đơn, truyền số điện thoại của khách hàng vào
			// hóa đơn
			HoaDon hoaDon = saveBill(khachHang.getSoDT(), convertPoint);

			// lưu thành công thì hóa đơn != null
			if (hoaDon != null) {

				// lưu detail bill
				if (saveDetailBill()) {

					// cập nhật thông tin khách hàng
					khachHang.setTongDiemTichLuy(
							khachHang.getTongDiemTichLuy() - diemTichLuyDaTra + diemTichLuyNhanDuoc);
					khachHang.setTongTienTichLuy(khachHang.getTongTienTichLuy().add(hoaDon.getTongTien()));

					// biến kiếm tra trạng thái cập nhật thành công khách hàng
					KhachHang status = saveCustomer(khachHang);

					// nếu thành công thì != null
					if (status != null) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private HoaDon saveBill(String numberPhone, int diemTichLuyDaTra) {
		String url = "http://localhost:8084/hoadon";
		
		//lấy nhân viên thanh toán hóa đơn hiện tại
		HttpSession session = request.getSession();
		NhanVien nv = (NhanVien) session.getAttribute("employee");

		// lấy các món ăn cần thanh toán từ session
		List<ThucDon> thucDon = thucDonService.getBill();

		// tổng tiền ban đầu = 0
		int totalAmount = 0;

		// mỗi lần lặp thì tăng tổng tiền của bill
		for (ThucDon td : thucDon) {
			totalAmount += td.getTongTien();
		}

		// tạo đối tượng
		HoaDon hoaDon = new HoaDon();
		hoaDon.setSoDT(numberPhone);
		hoaDon.setMaNhanVien(nv.getMaNhanVien());
		hoaDon.setTongTien(new BigDecimal(totalAmount).subtract(BigDecimal.valueOf(diemTichLuyDaTra)));
		hoaDon.setDiemTichLuyDaDung(diemTichLuyDaTra/1000);
		hoaDon.setThoiGianVao();
		hoaDon.setThoiGianRa();

		return rest.exchange(url, HttpMethod.POST, new HttpEntity<>(hoaDon), HoaDon.class).getBody();
	}

	private boolean saveDetailBill() {
		String url = "http://localhost:8084/chitiethoadon";

		// lấy các món ăn cần thanh toán từ session
		List<ThucDon> thucDon = thucDonService.getBill();

		return rest.exchange(url, HttpMethod.POST, new HttpEntity<>(thucDon), Boolean.class).getBody();
	}

	private KhachHang saveCustomer(KhachHang khachHang) {
		String url = "http://localhost:8081/khachhang";

		return rest.exchange(url, HttpMethod.PUT, new HttpEntity<>(khachHang), KhachHang.class).getBody();
	}
}
