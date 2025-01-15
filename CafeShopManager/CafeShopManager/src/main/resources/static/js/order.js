document.addEventListener('DOMContentLoaded', function() {
	var btn_close_model = document.getElementById('btn_close_model');
	var model_order = document.getElementById('model_order');
	var tbody_list_hoadon = document.getElementById('tbody_list_hoadon');
	var input_search = document.getElementById('timkiem_trangchu');
	var input_tongtien = document.getElementById('tongtien');
	var tbody_infor = document.getElementById('tbody_infor');
	btn_close_model.addEventListener('click', function() {
		model_order.classList.remove('show_model')
	})

	async function loadData(search) {
		tbody_list_hoadon.innerHTML = '';
		var tongtien = 0;
		try {
			// Gửi yêu cầu đầu tiên và chờ kết quả
			const res = await fetch('http://localhost:8084/hoadon' + search);
			const data = await res.json(); // Chuyển đổi kết quả thành JSON
			msg = res.text;
			if (data) {  // Nếu có dữ liệu
				for (let index = 0; index < data.length; index++) {
					const item = data[index];
					tongtien = tongtien + item.tongTien;
					// Gửi yêu cầu thứ hai và chờ kết quả
					const customerRes = await fetch('http://localhost:8081/khachhang/sdt/' + item.soDT);
					const customerData = await customerRes.json();

					if (customerData) {
						const tbory_tr = `<tr data-item='${JSON.stringify(item)}' class="item_order">
	            <td>${index + 1}</td>
	            <td>${item.thoiGianVao}</td>
	            <td>${item.soDT}</td>
	            <td>${customerData.tenKhachHang}</td>
	            <td>${item.diemTichLuyDaDung}</td>
	            <td>${item.tongTien.toLocaleString('vi-VN')}</td>
	          </tr>`;
						tbody_list_hoadon.innerHTML += tbory_tr;  // Thêm dòng vào bảng
					}
				}
				if (search == '') {
					input_tongtien.value = '';
				}
				else {
					input_tongtien.value = tongtien.toLocaleString('vi-VN') + ' VND';
				}
				loadDetailBill();
			}
		} catch (error) {
			console.error('Lỗi:', error); // Xử lý lỗi nếu có

		}
	}


	input_search.addEventListener('input', function() {
		if (input_search.value) {
			loadData("/search?sdt=" + input_search.value);
		}
		else {
			loadData('');
		}
	})

	function loadDetailBill() {
		tbody_infor.innerHTML = '';
		var tongtienthanhtoan = 0;
		var list_item_order = document.querySelectorAll('.item_order');
		list_item_order.forEach(function(button) {
			button.addEventListener('click', function() {
				var data = JSON.parse(button.getAttribute('data-item'));
				model_order.classList.add('show_model');
				console.log(data);
				$.ajax({
					url: 'http://localhost:8084/chitiethoadon/getcthd',
					method: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(data),
					success: function(res) {
						
						res.forEach(function(item, index) {
							var tbody_tr = `<tr>
								<td style="text-align: left; max-width: 10vh;">${item.tenMonAn}</td>
								<td style="text-align: center">${item.soLuong}</td>
								<td style="text-align: center">${item.giaMonAn.toLocaleString('vi-VN')}</td>
								<td style="text-align: end">${item.tongTien.toLocaleString('vi-VN')}</td>
							</tr>`
							tongtienthanhtoan = + item.tongTien;
							tbody_infor.innerHTML += tbody_tr;
						})
						console.log(res)
						
						document.getElementById('tenkhachhang').value = res[0].tenKhachHang;
						document.getElementById('diemtichluy').value = res[0].tongDiemTichLuy;
						document.getElementById('diemnhanduoc').value = Math.floor(tongtienthanhtoan / Math.pow(10, 4));;
						document.getElementById('diemthanhtoan').value = data.diemTichLuyDaDung;
						document.getElementById('tennhanvien').value = res[0].tenNhanVien;
						document.getElementById('totalAmount').textContent = tongtienthanhtoan.toLocaleString('vi-VN');
						document.getElementById('totalAmountCurrent').textContent =  (tongtienthanhtoan - (data.diemTichLuyDaDung * 1000)).toLocaleString('vi-VN');
					}
				})

			})
		})
	}

	loadData('');



})