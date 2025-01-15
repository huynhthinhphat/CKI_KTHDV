document.addEventListener('DOMContentLoaded', function() {
	var item_category = document.querySelectorAll('.item_category');
	var wrapper_item_menu = document.getElementById('wrapper_item_menu');
	var btn_pay = document.getElementById('btn_pay');
	var item_category_before;

	var item_category = document.querySelectorAll('.item_category')

	var totalAmountText = document.getElementById('totalAmount');
	var totalAmountCurrentText = document.getElementById('totalAmountCurrent');

	//gán tên khách hàng vào element html
	var order_customer = document.getElementById('order_customer');
	var order_pointer_current = document.getElementById('order_pointer_current');
	var order_pointer_pay = document.getElementById('order_pointer_pay');
	var order_pointer_receive = document.getElementById('order_pointer_receive');

	var timkiem_trangchu = document.getElementById('timkiem_trangchu');

	//hiển thị các món ăn theo danh mục đầu tiên
	loadDataMenu(item_category[0].getAttribute('maDanhMuc'))

	//nếu danh mục tạm không tồn tại thì mặc định sẽ cho hiệu ứng vào danh mục đầu tiên
	if (!item_category_before) {
		item_category[0].classList.add('active_category');
	}

	//bắt sự kiện khi click vào các danh mục
	item_category.forEach(function(item) {
		item.addEventListener('click', function() {

			//reset input tìm kiếm theo tên món ăn
			timkiem_trangchu.value = '';

			//xóa class khỏi danh mục đầu tiên
			item_category[0].classList.remove('active_category');

			//lấy thuộc tính của danh mục hiện tại
			var maDanhMuc = item.getAttribute('maDanhMuc');

			//thêm class mới vào danh mục hiện tại
			item.classList.add('active_category')

			//kiểm tra nếu danh mục trước tồn tại thì xóa class khỏi dmuc đó
			if (item_category_before) {
				item_category_before.classList.remove('active_category')
			}

			//gán danh mục hiện tại cho danh mục tạm
			item_category_before = item;

			//render data của các món ăn
			loadDataMenu(maDanhMuc);
		});
	});

	//mặc định sẽ hiển thị thông tin hóa đơn chưa được thanh toán
	dataDefaultBill()
	function dataDefaultBill() {
		$.ajax({
			url: 'http://localhost:8080/thucdon',
			method: 'GET',
			success: function(res) {
				dataBill(res);
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
			}
		});
	}

	//hàm render data lên view khi tìm kiếm hoặc chưa tìm kiếm
	function renderDataMenu(res) {
		wrapper_item_menu.innerHTML = '';
		res.forEach(function(item) {
			var itemHTML =
				`<div class="item_menu" data-item='${JSON.stringify(item)}'>
							        <div>
							            <img src="${item.hinhAnh}" alt="${item.tenMonAn}" />
							            <p>${item.giaMonAn.toLocaleString('vi-VN')} VNĐ</p>
							        </div>
							    	<div>
							        	<p>${item.tenMonAn}</p>
							    	</div>
							    </div>`;
			wrapper_item_menu.innerHTML += itemHTML;
		});
		clickItemMenu();
	}

	//hàm load data của các món ăn
	function loadDataMenu(maDanhMuc) {
		$.ajax({
			url: 'http://localhost:8083/thucdon/' + maDanhMuc,
			method: 'GET',
			success: function(res) {
				renderDataMenu(res)
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
				wrapper_item_menu.innerHTML = 'Có lỗi xảy ra khi tải dữ liệu. Vui lòng thử lại sau!';
			}
		});
	}

	//hàm click vào các menu sẽ thêm sp vào hóa đơn
	function clickItemMenu() {
		var item_menu = document.querySelectorAll('.item_menu');

		item_menu.forEach(function(item) {
			item.addEventListener('click', function() {
				var thucDon = JSON.parse(item.getAttribute('data-item'));
				addToBill(thucDon);
			});
		});
	}

	//hàm cộng số lượng sp trong bill
	function addToBill(item) {
		$.ajax({
			url: 'http://localhost:8080/thucdon',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(item),
			success: function(res) {
				dataBill(res);
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
			}
		});
	}

	//hàm trừ số lượng sp trong bill
	function minusToBill(item) {
		$.ajax({
			url: 'http://localhost:8080/thucdon',
			method: 'DELETE',
			contentType: 'application/json',
			data: JSON.stringify(item),
			success: function(res) {
				dataBill(res);
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
			}
		});
	}

	//hàm để clear bill sau khi thanh toán
	function clearBill() {
		$.ajax({
			url: 'http://localhost:8080/thucdon/clear',
			method: 'GET',
			success: function(res) {
				dataBill(res);
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
			}
		});
	}

	//tổng tiền ban đầu = 0
	var totalAmountCurrent = 0;

	//hàm dùng để render data bill
	function dataBill(res) {
		var tbody_infor = document.getElementById('tbody_infor');
		tbody_infor.innerHTML = '';

		var totalAmount = 0;

		res.forEach(function(item) {
			var tbody_tr = `<tr class="item_bill">
								<td>${item.tenMonAn}</td>	
								<td style="text-align: center"><span><button data-item='${JSON.stringify(item)}' class="btn_minus">-</button>${item.soLuong}<button data-item='${JSON.stringify(item)}' class="btn_add">+</button></span></td>
								<td style="text-align: center">${item.giaMonAn.toLocaleString('vi-VN')}</td>
								<td style="text-align: end">${item.tongTien.toLocaleString('vi-VN')}</td>									
							</tr>`;
			tbody_infor.innerHTML += tbody_tr;

			//mỗi lần duyệt thì cộng tiền của từng sp vào hóa đơn
			totalAmount += item.tongTien;
		})

		//button thêm số lượng sản phẩm trong bill
		var btn_add = document.querySelectorAll('.btn_add');
		btn_add.forEach(function(item) {
			item.addEventListener('click', function() {
				var thucDon = JSON.parse(item.getAttribute('data-item'));
				addToBill(thucDon);
			})
		})

		//button trừ số lượng sản phẩm trong bill
		var btn_minus = document.querySelectorAll('.btn_minus');
		btn_minus.forEach(function(item) {
			item.addEventListener('click', function() {
				var thucDon = JSON.parse(item.getAttribute('data-item'));
				minusToBill(thucDon);
			})
		})

		//gán tổng tiền vào element html
		totalAmountText.innerHTML = totalAmount.toLocaleString('vi-VN');
		totalAmountCurrentText.innerHTML = totalAmount.toLocaleString('vi-VN');

		changePoint(totalAmount);

		totalAmountCurrent = totalAmount;
	}

	//tính điểm tích lũy nhận được trong bill hiện tại
	function changePoint(totalAmount) {
		order_pointer_receive.innerHTML = Math.floor(totalAmount / Math.pow(10, 4));
	}

	var btn_check_user = document.getElementById('btn_check_user');
	var btn_close_model = document.getElementById('btn_close_model');
	var model_check_customer = document.getElementById('model_check_customer');

	var sodt = document.getElementById('sodt');
	var tenkhachhang = document.getElementById('tenkhachhang');
	var btn_check_customer = document.getElementById('btn_check_customer');
	var btn_add_customer = document.getElementById('btn_add_customer');

	let isEventAdded = false;

	btn_check_user.addEventListener('click', function() {
		model_check_customer.classList.add('show_model')
	})

	btn_close_model.addEventListener('click', function() {
		model_check_customer.classList.remove('show_model');
		btn_check_customer.style.display = 'block';
		btn_add_customer.style.display = 'none';
		sodt.value = '';
		tenkhachhang.value = '';
		sodt.readOnly = false;
	})

	btn_check_customer.addEventListener('click', function() {
		const regexNameCustomer = /^[a-zA-Z\u00C0-\u024F\u1E00-\u1EFF\s]+$/;
		const regexNumberPhone = /^\d+$/;

		//kiểm tra sdt hợp lệ
		if (!regexNumberPhone.test(sodt.value) || sodt.value.length !== 10 || sodt.value == '') {
			alert('Số điện thoại không hợp lệ');
			return;
		} else {
			$.ajax({
				url: 'http://localhost:8080/khachhang/' + sodt.value,
				method: 'GET',
				success: function(res) {
					//kiểm tra khách hàng tìm kiếm đã tồn tại hay chưa
					if (res == '') {
						Swal.fire({
							title: 'Khách hàng chưa tồn tại!',  // Tiêu đề của hộp thoại
							text: 'Hãy thêm khách hàng mới',  // Nội dung
							icon: 'info',  // Biểu tượng (info, warning, error, success, question)
							confirmButtonText: 'OK'
						}).then((result) => {
							//nếu nhấn OK vào swal
							if (result.isConfirmed) {

								//không cho phép đổi sdt đã nhập trước đó
								sodt.readOnly = true;

								//ẩn nút check customer
								btn_check_customer.style.display = 'none';

								//hiển nút thêm khách hàng
								btn_add_customer.style.display = 'block';

								//nếu event đã đăng ký
								if (!isEventAdded) {
									btn_add_customer.addEventListener('click', function() {
										//nếu tên khách hàng trống hoặc tên không hợp lệ
										if (tenkhachhang.value == '') {
											alert('Tên khách hàng không được để trống');
										} else if (!regexNameCustomer.test(tenkhachhang.value)) {
											alert('Tên khách hàng không hợp lệ');
										} else {
											$.ajax({
												url: 'http://localhost:8080/khachhang',
												method: 'POST',
												data: {
													soDT: sodt.value,
													tenKhachHang: tenkhachhang.value
												},
												success: function(res) {
													//nếu dữ liệu được thêm thành công sẽ trả về đối tượng khách hàng vừa thêm
													if (res != '') {
														Swal.fire({
															title: 'Thêm khách hàng mới thành công',  // Tiêu đề của hộp thoại
															icon: 'success',  // Biểu tượng (info, warning, error, success, question)
															confirmButtonText: 'OK'
														});

														//ẩn model check customer
														model_check_customer.classList.remove('show_model');

														//hiển thị nút check customer
														btn_check_customer.style.display = 'block';

														//ẩn nút thêm customerr
														btn_add_customer.style.display = 'none';

														//setup input số điện thoại có thể nhập
														sodt.readOnly = false;
													}

													//hiển thị khách hàng vừa được thêm thành công vào hóa đơn
													dataDefaultCustomer()
												},
												error: function(xhr, status, error) {
													console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
													console.error("Chi tiết lỗi: " + error);
												}
											});
										}
									});
									isEventAdded = true;
								}
							}
						});
					} else {

						//'Tên khách hàng: ' + res.tenKhachHang + '<br>' + 'Số điện thoại: ' + res.soDT + '<br>' + 'Tổng điểm tích lũy: ' + res.tongDiemTichLuy + '<br>' + 'Tổng tiền tích lũy: ' + res.tongTienTichLuy,
						Swal.fire({
							title: 'Thông tin khách hàng',  // Tiêu đề của hộp thoại
							html:
								'<div style="display: flex; align-items: center; flex-direction: column;"><div class="wrapper_text_swal"><div class="form_text_swal">Tên khách hàng:</div><div>' + res.tenKhachHang + '</div></div><div class="wrapper_text_swal"><div class="form_text_swal">Số điện thoại:</div><div>' + res.soDT + '</div></div><div class="wrapper_text_swal"><div class="form_text_swal">Tổng điểm tích lũy:</div><div>' + res.tongDiemTichLuy + '</div></div><div class="wrapper_text_swal"><div class="form_text_swal">Tổng tiền tích lũy:</div><div>' + res.tongTienTichLuy.toLocaleString('vi-VN') + ' VNĐ</div></div></div>',
							icon: 'success',  // Biểu tượng (info, warning, error, success, question)
							confirmButtonText: 'OK'
						});
						dataCustomer(res);
					}
				},
				error: function(xhr, status, error) {
					console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
					console.error("Chi tiết lỗi: " + error);
				}
			});
		}
	})

	//hàm gọi api để render thông tin customer lên view
	dataDefaultCustomer()
	function dataDefaultCustomer() {
		$.ajax({
			url: 'http://localhost:8080/khachhang',
			method: 'GET',
			success: function(res) {
				dataCustomer(res);
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
			}
		});
	}

	//hàm render thông tin customer lên view
	function dataCustomer(res) {
		//nếu sdt tồn tại
		if (res != '' && res != 'undefined') {
			//clear input số điện thoại
			sodt.value = '';

			//ẩn model
			model_check_customer.classList.remove('show_model');

			//gán thông tin khách hàng
			order_customer.innerHTML = res.tenKhachHang;
			order_pointer_current.innerHTML = res.tongDiemTichLuy;

			order_pointer_pay.addEventListener('input', function() {
				totalAmountCurrentText.innerHTML = (totalAmountCurrent - (this.value * 1000)).toLocaleString('vi-VN');;
			})
		}
	}

	//nút thanh toán
	pay();
	function pay() {
		btn_pay.addEventListener('click', function() {
			//nếu tên khách hàng đang trống
			if (order_customer.innerHTML == '') {
				Swal.fire({
					title: 'Khách hàng không được để trống!',  // Tiêu đề của hộp thoại
					icon: 'info',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				});
				return;
			}

			//nếu điểm thanh toán > điểm tích lũy hiện tại
			if (order_pointer_pay.value > parseFloat(order_pointer_current.innerHTML)) {
				Swal.fire({
					title: 'Điểm tích lũy không đủ!',  // Tiêu đề của hộp thoại
					icon: 'info',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				});
				order_pointer_pay.value = ''
				totalAmountCurrentText.innerHTML = totalAmountCurrent.toLocaleString('vi-VN');
				return;
			}

			//nếu điểm thanh toán < 0
			if (order_pointer_pay.value < 0) {
				Swal.fire({
					title: 'Điểm thanh toán không hợp lệ!',  // Tiêu đề của hộp thoại
					icon: 'info',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				});
				order_pointer_pay.value = ''
				totalAmountCurrentText.innerHTML = totalAmountCurrent.toLocaleString('vi-VN');
				return;
			}

			//nếu tổng tiền cần trả < 0
			if (parseFloat(totalAmountCurrentText.innerHTML) < 0) {
				Swal.fire({
					text: 'Tổng tiền cần trả không được nhỏ hơn 0!',  // Tiêu đề của hộp thoại
					icon: 'error',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				});
				return;
			}

			//lấy số lượng các món ăn trong bill hiện tại
			var item_bill = document.querySelectorAll('.item_bill');

			//nếu số lượng món ăn == 0 thì không thể thanh toán
			if (item_bill.length == 0) {
				Swal.fire({
					title: 'Không hóa đơn cần thanh toán!',  // Tiêu đề của hộp thoại
					icon: 'info',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				});
			} else { //thực hiện thanh toán
				Swal.fire({
					title: 'Xác nhận thanh toán?',  // Tiêu đề của hộp thoại
					icon: 'question',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				}).then((result) => {
					if (result.isConfirmed) {

						//cho điểm tích lũy mặc định = 0
						var diemTichLuy = 0;

						//nếu người dùng không nhập vào điểm thanh toán thì điểm tích lũy sẽ là 0 và ngược lại sẽ lấy giá trị trong input điểm thanh toán
						if (order_pointer_pay.value != '') {
							diemTichLuy = order_pointer_pay.value;
						}

						//gửi api để lưu các thông tin về hóa đơn và cập nhật người dùng
						$.ajax({
							url: 'http://localhost:8080/hoadon/' + diemTichLuy + '/' + order_pointer_receive.innerHTML,
							method: 'GET',
							success: function(res) {
								//xóa thông tin bill trong session
								clearBill();

								//cập nhạt lại số thứ tự phiếu
								var name_bill = document.getElementById('name_bill');
								name_bill.innerHTML = 'Phiếu thanh toán số ' + (parseInt(name_bill.innerHTML.substring(20)) + 1);

								//clear thông tin khách hàng
								order_customer.innerHTML = '';
								order_pointer_current.innerHTML = '';
								order_pointer_pay.value = '';
								Swal.fire({
									title: 'Thanh toán thành công',  // Tiêu đề của hộp thoại
									icon: 'success',  // Biểu tượng (info, warning, error, success, question)
								})
							},
							error: function(xhr, status, error) {
								Swal.fire({
									title: 'Hãy đăng nhập để thanh toán hóa đơn',  // Tiêu đề của hộp thoại
									icon: 'error',  // Biểu tượng (info, warning, error, success, question)
									confirmButtonText: 'Đăng nhập',
								}).then((result) => {
									if (result.isConfirmed) {
										window.location.href = "http://localhost:8080/";
									}
								})

							}
						});
					}
				});
			}
		});
	}

	var btn_delete_bill = document.getElementById('btn_delete_bill');
	btn_delete_bill.addEventListener('click', function() {
		Swal.fire({
			title: 'Xác nhận muốn hủy đơn?',  // Tiêu đề của hộp thoại
			icon: 'question',  // Biểu tượng (info, warning, error, success, question)
			confirmButtonText: 'OK'
		}).then((result) => {
			if (result.isConfirmed) {
				//xóa thông tin bill trong session
				clearBill();

				//clear thông tin khách hàng
				order_customer.innerHTML = '';
				order_pointer_current.innerHTML = '0';
				order_pointer_pay.value = '';
			}
		})
	})

	//khi input search có data thì sẽ gửi api để tìm các món ăn tương ứng
	timkiem_trangchu.addEventListener('input', function() {
		if (this.value.trim() !== '') {
			$.ajax({
				url: 'http://localhost:8080/thucdon/timkiem/' + this.value,
				method: 'GET',
				success: function(res) {
					renderDataMenu(res)
				},
				error: function(xhr, status, error) {
					console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
					console.error("Chi tiết lỗi: " + error);
				}
			});
		} else { //nếu input rỗng thì render lại data trước khi search
			item_category.forEach(function(item) {
				if (item.classList.contains('active_category')) {
					loadDataMenu(item.getAttribute('maDanhMuc'))
				}
			});
		}
	})
});
