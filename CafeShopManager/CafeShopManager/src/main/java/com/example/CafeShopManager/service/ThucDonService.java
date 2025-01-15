package com.example.CafeShopManager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CafeShopManager.entities.ThucDon;

import jakarta.servlet.http.HttpSession;

@Service
public class ThucDonService {

	@Autowired
	private HttpSession session;

	public List<ThucDon> getBill() {
		List<ThucDon> bill = (List<ThucDon>) session.getAttribute("bill");
		if (bill == null) {
			bill = new ArrayList<>();
			session.setAttribute("bill", bill);
		}
		return bill;
	}

	public void addToBill(ThucDon thucDon) {
		List<ThucDon> bill = getBill();

		boolean found = false;

		for (ThucDon td : bill) {
			if (td.getTenMonAn().equals(thucDon.getTenMonAn())) {
				td.setSoLuong(td.getSoLuong() + 1);
				td.setTongTien();
				found = true;
				break;
			}
		}
		
		if (!found) {
			thucDon.setSoLuong(1);
			thucDon.setTongTien();
			bill.add(thucDon);
		}

		session.setAttribute("bill", bill);
	}
	
	public void minusToBill(ThucDon thucDon) {
		List<ThucDon> bill = getBill();

		for (ThucDon td : bill) {
			if (td.getTenMonAn().equals(thucDon.getTenMonAn())) {
				td.setSoLuong(td.getSoLuong() - 1);
				
				if(td.getSoLuong() == 0) {
					bill.remove(td);
				}else {
					td.setTongTien();
				}
				break;
			}
		}

		session.setAttribute("bill", bill);
	}

	public void clearBill() {
		session.removeAttribute("customer");
		session.removeAttribute("bill");
	}
}
