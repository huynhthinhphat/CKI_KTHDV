document.addEventListener('DOMContentLoaded', function() {
	var btn_add_product = document.getElementById('btn_add_product');
	var model_add_product = document.getElementById('model_add_product');
	var btn_close_model_add = document.getElementById('btn_close_model_add');
	var model_update_quantity = document.getElementById('model_update_quantity');
	var btn_close_model_update = document.getElementById('btn_close_model_update');
	var tbody_list = document.getElementById('tbody_list');
	var btn_update = document.getElementById('btn_update');
	var btn_add = document.getElementById('btn_add');
	var search = document.getElementById('timkiem_trangchu');

	// Hiển thị và ẩn modal
	btn_add_product.addEventListener('click', function() {
		model_add_product.classList.add('show_model');
	});

	btn_close_model_add.addEventListener('click', function() {
		model_add_product.classList.remove('show_model');
	});

	btn_close_model_update.addEventListener('click', function() {
		model_update_quantity.classList.remove('show_model');
	});

	// Hàm tải dữ liệu
	function loadData(search) {
		$.ajax({
			url: 'http://localhost:8085/kho' + search,
			method: 'GET',
			success: function(res) {
				tbody_list.innerHTML = ''; // Xóa dữ liệu cũ trước khi thêm mới
				res.forEach(function(item, index) {
					var tbody_tr = `
                        <tr>
                            <td>${index + 1}</td>
                            <td>${item.tenSanPham}</td>
                            <td>${item.soLuong}</td>
                            <td>${item.donVi}</td>
                            <td>
                                <button data-item='${JSON.stringify(item)}' class="btn_edit form_button">
                                    <i class="fa-solid fa-pen"></i>
                                </button>
                                <button data-item='${JSON.stringify(item)}' class="btn_delete form_button" >
                                    <i class="fa-solid fa-trash"></i>
                                </button>
                            </td>
                        </tr>`;
					tbody_list.innerHTML += tbody_tr;
				});

				// Gắn sự kiện cho nút edit sau khi tải xong dữ liệu
				updateKho();
				deleteKho();
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
				tbody_list.innerHTML = 'Không có dữ liệu';
			}
		});
	}

	// Hàm gắn sự kiện cho nút edit
	function updateKho() {
		var btn_edit = document.querySelectorAll('.btn_edit');

		btn_edit.forEach(function(button) {
			button.addEventListener('click', function() {
				var data = JSON.parse(button.getAttribute('data-item'));
				model_update_quantity.classList.add('show_model');

				var quanityCurrent = document.getElementById('quanityCurrent');
				var quantityChange = document.getElementById('quantityChange');

				quanityCurrent.value = data.soLuong;

				// Xử lý cập nhật số lượng
				btn_update.onclick = function() {
					$.ajax({
						url: 'http://localhost:8085/kho/update/' + data.maSanPham + '?quantityChange=' + quantityChange.value,
						method: 'PUT',
						success: function() {
							loadData(''); // Tải lại dữ liệu sau khi cập nhật
							quantityChange.value = ''; // Reset input
							model_update_quantity.classList.remove('show_model'); // Đóng modal
						},
						error: function() {
							alert("Cập nhật thất bại. Vui lòng thử lại!");
						}
					});
				};
			});
		});
	}

	function addProduct() {


		// Xử lý thêm sản phẩm
		btn_add.onclick = function() {
			var ten = document.getElementById('ten').value;
			var soluong = document.getElementById('soluong').value;
			var donvi = document.getElementById('donvi').value;
			let item = {
				tenSanPham: ten,
				soLuong: parseInt(soluong),
				donVi: donvi
			};
			$.ajax({
				url: 'http://localhost:8085/kho',
				method: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(item),
				success: function(res) {
					if (res) {
						Swal.fire({
							title: res,
							icon: 'success',  // Biểu tượng (info, warning, error, success, question)
							confirmButtonText: 'OK'
						})
						loadData('');
					}
				},
				error: function(xhr, status, error) {
					Swal.fire({
						title: xhr.responseText,
						icon: 'error',  // Biểu tượng (info, warning, error, success, question)
						confirmButtonText: 'OK'
					})
				}
			})
		}
	}

	function deleteKho() {
		var btn_delete = document.querySelectorAll('.btn_delete');
		btn_delete.forEach(function(button) {
			button.addEventListener('click', function() {
				Swal.fire({
					title: 'Có chắc chắn muốn xóa?',  // Tiêu đề của hộp thoại
					icon: 'warning',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				}).then((result) => {
					if (result.isConfirmed) {
						var data = JSON.parse(button.getAttribute('data-item'));
						$.ajax({
							url: 'http://localhost:8085/kho/' + data.maSanPham,
							method: 'DELETE',
							success: function() {
								Swal.fire({
									title: 'Xóa thành công',  // Tiêu đề của hộp thoại
									icon: 'success',  // Biểu tượng (info, warning, error, success, question)
									confirmButtonText: 'OK'
								})
								loadData('');
							}
						})
					}
				})
			})
		})

	}

	search.addEventListener('input', function() {
		if(search.value!=null){
			loadData('/search?name=' + search.value);
		}else{
			loadData('');
		}
	})

	addProduct();
	// Gọi hàm tải dữ liệu ban đầu
	loadData('');
});
