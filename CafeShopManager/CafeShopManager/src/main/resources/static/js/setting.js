document.addEventListener('DOMContentLoaded', function() {
	var btn_reset = document.getElementById('btn_reset');

	btn_reset.addEventListener('click', function() {
		Swal.fire({
			title: 'Bạn chắc chắn muốn reset?',  // Tiêu đề của hộp thoại
			text: 'Sau khi reset sẽ không khôi phục được!',
			icon: 'warning',  // Biểu tượng (info, warning, error, success, question)
			confirmButtonText: 'OK'
		}).then((result) => {
			if (result.isConfirmed) {
				$.ajax({
					url: 'http://localhost:8081/khachhang/reset',
					method: 'GET',
					success: function(res) {
						console.log(res)
						Swal.fire({
							title: 'Reset thành công!',  // Tiêu đề của hộp thoại
							icon: 'success',  // Biểu tượng (info, warning, error, success, question)
							confirmButtonText: 'OK'
						})
					},
					error: function(xhr, status, error) {
						Swal.fire({
							title: 'Lỗi service!',  // Tiêu đề của hộp thoại
							text: 'Reset thất bại!',
							icon: 'error',  // Biểu tượng (info, warning, error, success, question)
							confirmButtonText: 'OK'
						})
					}
				});
			}
		})
	})
})