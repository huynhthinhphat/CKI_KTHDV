document.addEventListener('DOMContentLoaded', function() {
	var path = window.location.pathname;
	var link_page = document.querySelectorAll('.link_page');

	link_page.forEach(function(item) {
		var attr = item.getAttribute('href');

		if (attr == path) {
			item.style.backgroundColor = '#D64646';
		}

		// Chặn hành động click mặc định
		item.addEventListener('click', function(event) {
			// Dừng hành động mặc định của thẻ a
			event.preventDefault();

			$.ajax({
				url: 'http://localhost:8080' + attr,
				method: 'GET',
				success: function(res) {
					window.location.href = 'http://localhost:8080' + attr;
				},
				error: function(xhr, status, error) {
					console.log(xhr)
					console.log(status)
					console.log(error)
					Swal.fire({
						title: 'Bạn không đủ quyền để truy cập',  // Tiêu đề của hộp thoại
						icon: 'error',  // Biểu tượng (info, warning, error, success, question)
						confirmButtonText: 'OK'
					});
				}
			});
		});
	});
})