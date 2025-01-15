package com.example.CafeShopManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.CafeShopManager.entities.NhanVien;
import com.example.CafeShopManager.jwt.JwtUtil;

import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {

	@Autowired
	private RestTemplate rest;

	@Autowired
	private HttpSession session;

	@Autowired
	private JwtUtil jwt;

	public boolean login(NhanVien nv) {
		String url = "http://localhost:8082/nhanvien/login";

		try {
			// Gửi yêu cầu POST đến API với đối tượng NhanVien
			ResponseEntity<NhanVien> response = rest.exchange(url, HttpMethod.POST, new HttpEntity<>(nv),
					NhanVien.class);

			// Kiểm tra mã trạng thái HTTP
			if (response.getStatusCode().is2xxSuccessful()) {
				// Lưu thông tin vào session nếu đăng nhập thành công
				session.setAttribute("employee", response.getBody());

				String token = jwt.generateToken(response.getBody().getSoDT(), response.getBody().getVaiTro());
				session.setAttribute("token", token);

				return true;
			}
		} catch (HttpClientErrorException.NotFound e) {
			// Xử lý khi trả về lỗi 404 (Not Found)
			System.out.println("Lỗi: Không tìm thấy API endpoint tại " + url);
		}

		return false; // Trả về false nếu có lỗi hoặc không thành công
	}

}
