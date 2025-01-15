document.addEventListener('DOMContentLoaded', function() {
	var tbody_list = document.getElementById('tbody_list')
	var timkiem_before_date = document.getElementById('timkiem_before_date')
	var timkiem_after_date = document.getElementById('timkiem_after_date')
	var timkiem_name = document.getElementById('timkiem_name')



	function loadData(search, page = 1) {
	    $.ajax({
	        url: `http://localhost:8085/lichsuxuatnhapkho${search}?page=${page}`,
	        method: 'GET',
	        success: function(res) {
	            tbody_list.innerHTML = ''; // Xóa dữ liệu cũ trước khi thêm mới
	            res.forEach(function(item, index) {
	                var tbody_tr = `
	                    <tr>
	                        <td>${index + 1}</td>
	                        <td>${item.kho.tenSanPham}</td>
	                        <td>${item.soLuongTruoc}</td>
	                        <td>${item.soLuongSau}</td>
	                        <td>${item.thoiGianTao}</td>
	                    </tr>`;
	                tbody_list.innerHTML += tbody_tr;
	            });
	        },
	        error: function(xhr, status, error) {
	            Swal.fire({
	                title: xhr.responseText,
	                icon: 'error',  // Biểu tượng (info, warning, error, success, question)
	                confirmButtonText: 'OK'
	            });
	        }
	    });
	}

	timkiem_before_date.addEventListener('change', function(event) {
		if (timkiem_before_date.value != null) {
			loadData('/before/' + timkiem_before_date.value);
		}
		else {
			loadData('');
		}
	})
	timkiem_after_date.addEventListener('change', function(event) {
		if (timkiem_after_date.value != null) {
			loadData('/after/' + timkiem_after_date.value);
		}
		else {
			loadData('');
		}
	})
	timkiem_name.addEventListener('keydown', function(event) {
		if (timkiem_name.value != null && event.key === 'Enter') {
			loadData('/kho/' + timkiem_name.value);
		}
		else {
			loadData('');
		}
	})
	loadData('',1);
})