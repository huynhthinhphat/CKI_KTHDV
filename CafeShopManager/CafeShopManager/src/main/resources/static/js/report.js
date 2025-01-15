document.addEventListener('DOMContentLoaded', function() {
	var ctx1 = document.getElementById('myChart').getContext('2d');
	var ctx2 = document.getElementById('myChartDate').getContext('2d');
	var ctx3 = document.getElementById('myChartMonth').getContext('2d');

	let arrayOfProduct = [];
	let arrayOfDate = [];
	let arrayOfMonth = [];

	getQuantityOfProduct();
	getOrderFollowDate();
	getOrderFollowMonth();

	function getQuantityOfProduct() {
		$.ajax({
			url: 'http://localhost:8086/report/map/quantity',
			method: 'GET',
			success: function(res) {
				if (res) {
					// Xử lý dữ liệu từ cả hai API
					res.forEach(function(item) {
						arrayOfProduct.push({
							name: item.tenSanPham,
							quantity: item.tongSoLuongDaBan
						});
					});

					// Sau khi có đủ dữ liệu, vẽ biểu đồ
					drawChartQuantityProduct();
				}
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
			}
		});
	}

	function getOrderFollowDate() {
		$.ajax({
			url: 'http://localhost:8086/report/map/date',
			method: 'GET',
			success: function(res) {
				if (res) {
					res.forEach(function(item) {
						arrayOfDate.push({
							date: (item.time).substring(0, 10),
							totalAmount: item.tongTien,
						});
					})
					drawChartDate()
				}
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
			}
		});
	}

	function getOrderFollowMonth() {
		$.ajax({
			url: 'http://localhost:8086/report/map/month',
			method: 'GET',
			success: function(res) {
				if (res) {
					res.forEach(function(item) {
						arrayOfMonth.push({
							month: 'Tháng ' + item.month,
							totalAmount: item.tongTien,
						});
					})
					drawChartMonth()
				}
			},
			error: function(xhr, status, error) {
				console.error("Lỗi xảy ra khi gửi yêu cầu: " + status);
				console.error("Chi tiết lỗi: " + error);
			}
		});
	}

	function drawChartQuantityProduct() {
		// Dữ liệu cho biểu đồ từ arrayOfProduct
		const labels = arrayOfProduct.map(item => item.name);
		const data = arrayOfProduct.map(item => item.quantity);

		var myChart = new Chart(ctx1, {
			type: 'bar', // Loại biểu đồ (cột)
			data: {
				labels: labels, // Dữ liệu trục X (tên món ăn)
				datasets: [{
					label: 'Số lượng bán',
					data: data, // Dữ liệu trục Y (số lượng bán)
					backgroundColor: 'rgba(75, 192, 192, 0.2)',
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				}]
			},
			options: {
				responsive: true, // Cho phép biểu đồ có thể thay đổi kích thước linh hoạt
				plugins: {
					title: {
						display: true, // Hiển thị tiêu đề của biểu đồ
						text: 'Biểu đồ số lượng bán các sản phẩm' // Tên biểu đồ
					}
				},
				scales: {
					y: {
						beginAtZero: true // Bắt đầu trục Y từ 0
					}
				}
			}
		});
	}

	function drawChartDate() {
		// Dữ liệu cho biểu đồ từ arrayOfProduct
		const labels = arrayOfDate.map(item => item.date);
		const data = arrayOfDate.map(item => item.totalAmount);

		var myChartDate = new Chart(ctx2, {
			type: 'bar', // Loại biểu đồ (cột)
			data: {
				labels: labels, // Dữ liệu trục X (tên món ăn)
				datasets: [{
					label: 'Tổng tiền: ',
					data: data, // Dữ liệu trục Y (số lượng bán)
					backgroundColor: 'rgba(75, 192, 192, 0.2)',
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				}]
			},
			options: {
				responsive: true, // Cho phép biểu đồ có thể thay đổi kích thước linh hoạt
				plugins: {
					title: {
						display: true, // Hiển thị tiêu đề của biểu đồ
						text: 'Biểu đồ tổng tiền bán từng ngày' // Tên biểu đồ
					}
				},
				scales: {
					y: {
						beginAtZero: true // Bắt đầu trục Y từ 0
					}
				}
			}
		});
	}

	function drawChartMonth() {
		// Dữ liệu cho biểu đồ từ arrayOfProduct
		const labels = arrayOfMonth.map(item => item.month);
		const data = arrayOfMonth.map(item => item.totalAmount);

		var myChartMonth = new Chart(ctx3, {
			type: 'bar', // Loại biểu đồ (cột)
			data: {
				labels: labels, // Dữ liệu trục X (tên món ăn)
				datasets: [{
					label: 'Tổng tiền: ',
					data: data, // Dữ liệu trục Y (số lượng bán)
					backgroundColor: 'rgba(75, 192, 192, 0.2)',
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				}]
			},
			options: {
				responsive: true, // Cho phép biểu đồ có thể thay đổi kích thước linh hoạt
				plugins: {
					title: {
						display: true, // Hiển thị tiêu đề của biểu đồ
						text: 'Biểu đồ tổng tiền bán từng tháng trong năm' // Tên biểu đồ
					}
				},
				scales: {
					y: {
						beginAtZero: true // Bắt đầu trục Y từ 0
					}
				}
			}
		});
	}
});
