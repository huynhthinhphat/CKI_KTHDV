document.addEventListener('DOMContentLoaded', function() {
    var matkhau  = document.getElementById('matkhau');
    var checkBox = document.getElementById('checkBox');
    
    // Hàm để thay đổi kiểu của trường mật khẩu
    function togglePasswordVisibility() {
        if (checkBox.checked) {
            matkhau.type = 'text'; // Hiển thị mật khẩu
        } else {
            matkhau.type = 'password'; // Ẩn mật khẩu
        }
    }

    // Lắng nghe sự kiện 'change' của checkbox
    checkBox.addEventListener('change', togglePasswordVisibility);
});
