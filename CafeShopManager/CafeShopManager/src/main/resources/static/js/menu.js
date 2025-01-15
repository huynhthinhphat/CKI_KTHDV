document.addEventListener('DOMContentLoaded', function() {
	var btn_add_product = document.getElementById('btn_add_product');
	var btn_add_category = document.getElementById('btn_add_category');
	var btn_close = document.querySelectorAll('.button_close');

	var model_menu = document.getElementById('model_menu');
	var model_category = document.getElementById('model_category');

	var msg_menu = document.getElementById('msg_menu');
	var wrapper_category = document.getElementById('wrapper_category');
	var tbody_list = document.getElementById('tbody_list');
	var loaidanhmuc = document.getElementById('loaidanhmuc');
	var btn_update = document.getElementById('btn_update');
	var btn_add = document.getElementById('btn_add');
	var ten = document.getElementById('ten');
	var gia = document.getElementById('gia');
	var btn_add_categorys = document.getElementById('btn_add_categorys');
	var tendm = document.getElementById('tendm');
	var timkiem_trangchu = document.getElementById('timkiem_trangchu');

	// Hiển thị modal thêm sản phẩm mới
	btn_add_product.addEventListener('click', function() {
		msg_menu.innerHTML = 'Thêm sản phẩm mới';
		model_menu.classList.add('show_model');
		document.getElementById('btn_add').style.display = 'block';
		document.getElementById('btn_update').style.display = 'none';
	});

	// Hiển thị modal thêm danh mục mới
	btn_add_category.addEventListener('click', function() {
		model_category.classList.add('show_model');
	});

	// Đóng modal
	btn_close.forEach(function(item, index) {
		item.addEventListener('click', function() {
			if (index === 0 && model_menu.classList.contains('show_model')) {
				model_menu.classList.remove('show_model');
				ten.value = ''
				gia.value = ''
			} else if (index === 1 && model_category.classList.contains('show_model')) {
				model_category.classList.remove('show_model');
			}
			msg_menu.innerHTML = '';
		});
	});

	// Hàm load danh mục
	function loadDanhMuc() {
		$.ajax({
			url: 'http://localhost:8083/danhmuc',
			method: 'GET',
			success: function(res) {
				wrapper_category.innerHTML = ''; // Xóa dữ liệu cũ trước khi thêm mới
				loaidanhmuc.innerHTML = '';
				res.forEach(function(item) {
					var btn_category = `
                        <a class="item_category" data-item='${JSON.stringify(item)}'>
                            ${item.tenDanhMuc}
                            <button class="btn_delete_DM" data-item='${JSON.stringify(item)}'><i  class="fa-solid fa-minus"></i></button>
                        </a>`;
					var option_category = `<option value='${JSON.stringify(item)}'>${item.tenDanhMuc}</option>`;
					loaidanhmuc.innerHTML += option_category;
					wrapper_category.innerHTML += btn_category;
				});
				deleteCategory();
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
				tbody_list.innerHTML = 'Có lỗi xảy ra khi tải dữ liệu. Vui lòng thử lại sau!';
			}
		});
	}

	// Hàm load dữ liệu món ăn theo danh mục
	function loadDataMenu(search) {
		$.ajax({
			url: 'http://localhost:8083/thucdon' + search,
			method: 'GET',
			success: function(res) {
				tbody_list.innerHTML = '';
				res.forEach(function(item, index) {
					var tbody_tr = `
                        <tr>
                            <td>${index + 1}</td>
                           
                            <td>${item.tenMonAn}</td>
                            <td>${item.giaMonAn.toLocaleString('vi-VN')}</td>
                            <td>
                                <button data-item='${JSON.stringify(item)}' class="btn_edit form_button"><i class="fa-solid fa-pen"></i></button>
                                <button data-item='${JSON.stringify(item)}' class="btn_delete form_button"><i class="fa-solid fa-trash"></i></button>
                            </td>
                        </tr>`;
					tbody_list.innerHTML += tbody_tr;
				});
				updateMenu();
				deleteMenu();
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
				tbody_list.innerHTML = 'Có lỗi xảy ra khi tải dữ liệu. Vui lòng thử lại sau!';
			}
		});
	}

	/*	<td class="item_img"><img src="${item.hinhAnh}" alt="${item.tenMonAn}"></td>*/

	// Cập nhật menu
	function updateMenu() {
		var btn_edit = document.querySelectorAll('.btn_edit');

		btn_edit.forEach(function(button) {
			button.addEventListener('click', function() {
				msg_menu.innerHTML = 'Cập nhật sản phẩm';
				document.getElementById('btn_add').style.display = 'none';
				document.getElementById('btn_update').style.display = 'block';

				var data = JSON.parse(button.getAttribute('data-item'));
				model_menu.classList.add('show_model');
				ten.value = data.tenMonAn;
				loaidanhmuc.value = JSON.stringify(data.danhMuc); // Gán giá trị danh mục
				gia.value = data.giaMonAn;

				btn_update.onclick = function() {
					let selectedDanhMuc = JSON.parse(loaidanhmuc.value); // Chuyển đổi lại object
					let itemupdate = {
						maMonAn: data.maMonAn,
						tenMonAn: ten.value,
						danhMuc: selectedDanhMuc,
						giaMonAn: parseInt(gia.value),
						hinhAnh: data.hinhAnh
					};

					$.ajax({
						url: 'http://localhost:8083/thucdon/' + data.maMonAn,
						method: 'PUT',
						contentType: 'application/json',
						data: JSON.stringify(itemupdate),
						success: function() {
							loadDataMenu(''); // Tải lại dữ liệu sau khi cập nhật
							model_menu.classList.remove('show_model'); // Đóng modal
							Swal.fire({
								title: 'Cập nhật thành công',
								icon: 'success',  // Biểu tượng (info, warning, error, success, question)
								confirmButtonText: 'OK'
							})
						},
						error: function() {
							Swal.fire({
								title: 'Cập nhật thất bại. Vui lòng thử lại',
								icon: 'warning',  // Biểu tượng (info, warning, error, success, question)
								confirmButtonText: 'OK'
							})
						}
					});
				};
			});
		});
	}


	function addMenu() {
		btn_add.onclick = function() {
			let item = {
				tenMonAn: ten.value,
				danhMuc: JSON.parse(loaidanhmuc.value),
				giaMonAn: parseInt(gia.value)

			};
			$.ajax({
				url: 'http://localhost:8083/thucdon',
				method: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(item),
				success: function(res) {
					if (res) {
						Swal.fire({
							title: 'Thêm thành công',
							icon: 'success',  // Biểu tượng (info, warning, error, success, question)
							confirmButtonText: 'OK'
						})
						loadDataMenu('');
						model_menu.classList.remove('show_model');
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


	function deleteMenu() {
		var btn_delete = document.querySelectorAll('.btn_delete');
		btn_delete.forEach(function(button) {
			button.addEventListener('click', function() {
				console.log("ok")
				Swal.fire({
					title: 'Bạn có chắc chắn muốn xóa!',  // Tiêu đề của hộp thoại
					icon: 'warning',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				}).then((result) => {
					if (result.isConfirmed) {
						var data = JSON.parse(button.getAttribute('data-item'));
						console.log(data)
						$.ajax({
							url: 'http://localhost:8083/thucdon/' + data.maMonAn,
							method: 'DELETE',
							success: function() {
								Swal.fire({
									title: 'Xóa thành công',  // Tiêu đề của hộp thoại
									icon: 'success',  // Biểu tượng (info, warning, error, success, question)
									confirmButtonText: 'OK'
								})
								loadDataMenu('');
							}
						})
					}
				})
			})
		})

	}


	function addCategory() {
		btn_add_categorys.onclick = function() {
			let item = {
				tenDanhMuc: tendm.value,
			};
			$.ajax({
				url: 'http://localhost:8083/danhmuc',
				method: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(item),
				success: function(res) {
					if (res) {
						Swal.fire({
							title: 'Thêm thành công',
							icon: 'success',  // Biểu tượng (info, warning, error, success, question)
							confirmButtonText: 'OK'
						})
						loadDanhMuc();
						model_category.classList.remove('show_model');
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

	function deleteCategory() {
		var btn_delete = document.querySelectorAll('.btn_delete_DM');
		btn_delete.forEach(function(button) {
			button.addEventListener('click', function() {

				Swal.fire({
					title: 'Bạn có chắc chắn muốn xóa!',  // Tiêu đề của hộp thoại
					icon: 'warning',  // Biểu tượng (info, warning, error, success, question)
					confirmButtonText: 'OK'
				}).then((result) => {
					if (result.isConfirmed) {
						var data = JSON.parse(button.getAttribute('data-item'));
						console.log(data.maDanhMuc)
						$.ajax({
							url: 'http://localhost:8083/danhmuc/' + data.maDanhMuc,
							method: 'DELETE',
							success: function() {
								Swal.fire({
									title: 'Xóa thành công',  // Tiêu đề của hộp thoại
									icon: 'success',  // Biểu tượng (info, warning, error, success, question)
									confirmButtonText: 'OK'
								})
								loadDanhMuc();
							},
							error: function(xhr, status, error) {
								Swal.fire({
									title: 'Xóa thất bại',
									text: 'Vẫn còn sản phẩm liên kết danh mục này',
									icon: 'error',  // Biểu tượng (info, warning, error, success, question)
									confirmButtonText: 'OK'
								})
							}
						})
					}
				})
			})
		})

	}
	timkiem_trangchu.addEventListener('input', function() {
		if (timkiem_trangchu != null) {
			loadDataMenu('/search?name=' + timkiem_trangchu.value);
		} else {
			loadDataMenu('');
		}

	})

	addCategory();
	addMenu();
	loadDanhMuc();
	loadDataMenu('');
});
