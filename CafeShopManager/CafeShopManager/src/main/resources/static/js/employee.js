document.addEventListener('DOMContentLoaded', function() {
	var btn_add_product = document.getElementById('btn_add_product');
	var btn_close = document.querySelectorAll('.button_close');

	var model_employee = document.getElementById('model_employee');
	var model_category = document.getElementById('model_category');

	var msg = document.getElementById('msg');

	var tbody = document.getElementById('tbody');

	var btn_update = document.getElementById('btn_update');
	var ten = document.getElementById('ten');
	var sodt = document.getElementById('sodt');
	var matkhau = document.getElementById('matkhau');

	btn_add_product.addEventListener('click', function() {
		msg.innerHTML = 'Thêm nhân viên mới';

		document.getElementById('btn_add').style.display = 'block';
		document.getElementById('btn_update').style.display = 'none';

		ten.value = '';
		sodt.value = '';
		matkhau.value = '';

		model_employee.classList.add('show_model')
	})

	btn_close.forEach(function(item, index) {
		item.addEventListener('click', function() {
			console.log(index)
			if (index == 0) {
				if (model_employee.classList.contains('show_model')) {
					model_employee.classList.remove('show_model')
				}
			} else if (index == 1) {
				if (model_category.classList.contains('show_model')) {
					model_category.classList.remove('show_model')
				}
			}

			msg.innerHTML = '';
		})
	})

	loadDataEmployee()
	function loadDataEmployee() {
		$.ajax({
			url: 'http://localhost:8082/nhanvien',
			method: 'GET',
			success: function(res) {
				console.log(res)
				renderData(res);
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
			}
		});
	}

	function renderData(res) {
		tbody.innerHTML = '';
		res.forEach(function(item, index) {
			var itemHTML =
				`<tr>
					<td>${index + 1}</td>
					<td>${item.soDT}</td>
					<td>${item.tenNhanVien}</td>
					<td>${item.thoiGianTao}</td>
					<td>
						<button data-item='${JSON.stringify(item)}' class="btn_edit form_button"><i class="fa-solid fa-pen"></i></button>
						<button data-item='${JSON.stringify(item)}' class="btn_delete form_button"><i class="fa-solid fa-trash"></i></button>
					</td>
				</tr>`;
			tbody.innerHTML += itemHTML;
		})

		edit();
		deleteItem();
	}

	function validateForm() {
		const regexName = /^[a-zA-Z\u00C0-\u024F\u1E00-\u1EFF\s]+$/;
		const regexNumberPhone = /^\d+$/;

		if (!regexName.test(ten.value)) {
			Swal.fire({
				title: 'Tên nhân viên không hợp lệ!',  // Tiêu đề của hộp thoại
				icon: 'info',  // Biểu tượng (info, warning, error, success, question)
				confirmButtonText: 'OK'
			})
			return false;
		}

		if (!regexNumberPhone.test(sodt.value)) {
			Swal.fire({
				title: 'Số điện thoại không hợp lệ!',  // Tiêu đề của hộp thoại
				icon: 'info',  // Biểu tượng (info, warning, error, success, question)
				confirmButtonText: 'OK'
			})
			return false;
		}

		if (parseInt(sodt.value.length) != 10) {
			Swal.fire({
				title: 'Độ dài số điện thoại không hợp lệ!',  // Tiêu đề của hộp thoại
				icon: 'info',  // Biểu tượng (info, warning, error, success, question)
				confirmButtonText: 'OK'
			})
			return false;
		}

		if (ten.value == '' || sodt.value == '' || matkhau.value == '') {
			Swal.fire({
				title: 'Thông tin cập nhật không được để trống!',  // Tiêu đề của hộp thoại
				icon: 'info',  // Biểu tượng (info, warning, error, success, question)
				confirmButtonText: 'OK'
			})
			return false;
		}
		return true;
	}

	function edit() {
		var btn_edit = document.querySelectorAll('.btn_edit');

		btn_edit.forEach(function(item) {
			item.addEventListener('click', function() {
				btn_update.removeAttribute('data-item');

				var data = JSON.parse(item.getAttribute('data-item'));

				btn_update.setAttribute('data-item', item.getAttribute('data-item'));

				document.getElementById('btn_add').style.display = 'none';
				document.getElementById('btn_update').style.display = 'block';

				msg.innerHTML = 'Cập nhật nhân viên';

				ten.value = data.tenNhanVien;
				sodt.value = data.soDT;
				matkhau.value = data.matKhau;

				model_employee.classList.add('show_model');
			})
		})

		acceptUpdate();
	}

	function acceptUpdate() {
		btn_update.addEventListener('click', function() {

			if (!validateForm()) {
				return;
			}

			var data = JSON.parse(btn_update.getAttribute('data-item'));

			data["tenNhanVien"] = ten.value;
			data["soDT"] = sodt.value;
			data["matKhau"] = matkhau.value;

			$.ajax({
				url: 'http://localhost:8082/nhanvien/' + data.maNhanVien,
				method: 'PUT',
				contentType: 'application/json',
				data: JSON.stringify(data),
				success: function(res) {
					Swal.fire({
						title: 'Cập nhật thành công!',  // Tiêu đề của hộp thoại
						icon: 'success',  // Biểu tượng (info, warning, error, success, question)
						confirmButtonText: 'OK'
					}).then(() => {
						model_employee.classList.remove('show_model');
						loadDataEmployee()
					})
				},
				error: function(xhr, status, error) {
					console.log(xhr)
					Swal.fire({
						title: xhr.responseText,  // Tiêu đề của hộp thoại
						icon: 'error'
					})
				}
			});
		})
	}

	function deleteItem() {
		var btn_delete = document.querySelectorAll('.btn_delete');

		btn_delete.forEach(function(item) {
			item.addEventListener('click', function() {

				var data = JSON.parse(item.getAttribute('data-item'));

				Swal.fire({
					title: 'Bạn muốn xóa nhân viên ' + data.tenNhanVien + '?',  // Tiêu đề của hộp thoại
					icon: 'question',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				}).then((result) => {
					if (result.isConfirmed) {
						$.ajax({
							url: 'http://localhost:8082/nhanvien/' + data.maNhanVien,
							method: 'DELETE',
							success: function() {
								Swal.fire({
									title: 'Xóa thành công!',  // Tiêu đề của hộp thoại
									icon: 'success',  // Biểu tượng (info, warning, error, success, question)
									confirmButtonText: 'OK'
								}).then(() => {
									loadDataEmployee()
								})
							},
							error: function(xhr, status, error) {
								Swal.fire({
									title: xhr.responseText,  // Tiêu đề của hộp thoại
									icon: 'error'
								})
							}
						});
					}
				})
			})
		})
	}

	create();
	function create() {
		var btn_add = document.getElementById('btn_add');
		btn_add.addEventListener('click', function() {

			if (!validateForm()) {
				return;
			}

			let newEmployee = {
				"tenNhanVien": ten.value,
				"soDT": sodt.value,
				"matKhau": matkhau.value
			};

			$.ajax({
				url: 'http://localhost:8082/nhanvien',
				method: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(newEmployee),
				success: function(res) {
					Swal.fire({
						title: 'Thêm thành công!',  // Tiêu đề của hộp thoại
						icon: 'success',  // Biểu tượng (info, warning, error, success, question)
						confirmButtonText: 'OK'
					}).then(() => {
						ten.value = '';
						sodt.value = '';
						matkhau.value = '';

						model_employee.classList.remove('show_model');
						loadDataEmployee()
					})
				},
				error: function(xhr, status, error) {
					Swal.fire({
						title: xhr.responseText,  // Tiêu đề của hộp thoại
						icon: 'error'
					})
				}
			});
		})
	}

	search();
	function search() {
		var timkiem_nhanvien = document.getElementById('timkiem_nhanvien');

		timkiem_nhanvien.addEventListener('input', function() {
			if (timkiem_nhanvien.value == '') {
				loadDataEmployee()
			} else {
				$.ajax({
					url: 'http://localhost:8082/nhanvien/search/' + timkiem_nhanvien.value,
					method: 'GET',
					success: function(res) {
						renderData(res)
					}
				});
			}
		})
	}
})