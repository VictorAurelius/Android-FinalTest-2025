tạo class KhachHang.java gồm:
mã: kiểu string, bắt đầu bằng 2 ký tự "KH", theo sau là số tăng dần bắt đầu từ 90, bước nhảy 30
họ và tên khách hàng: kiểu String, không được để trống
so điện thoại: kiểu String, không được để trống, định dạng 10 số, bắt đầu bằng 0
ngày tháng đánh giá: kiểu datetime, không được để trống, định dạng dd/MM/yyyy
bình chọn: kiểu float, thang điểm 5 lấy lẻ 1 số thập phân

viết file SqliteHelper.java để quản lý cơ sở dữ liệu:
+ tạo database
+ nhập dữ liệu mẫu với các giá trị tùy chọn: 10 khách hàng, họ tên, số điện thoại, ngày tháng đánh giá, bình chọn

viết method trong Class BaiHat.java: để tính: Điểm đánh giá = Bình chọn + ( 5 - Bình chọn ) * (90 + 1)/100

tạo xml cho mainacitivity.xml gồm:
1, thanh gang: nút bên Trái là biểu tượng tìm kiếm (tượng trưng), nút bên phải là "Sắp xếp"
2, thanh tiêu đề: trái là "Trung bình", phải là giá trị Giá trung bình
3, CustomListView để hiển thị danh sách khách hàng:
mỗi phần tử gồm:
+ dòng 1: trái là tên khách hàng, phải là ngày tháng đánh giá
+ dòng 2: trái là số điện thoại, phải là điểm đánh giá (đã tính bằng hàm)

viết file Adapter cho CustomListView

trong MainActivity.java:
+ hiển thị danh sách bài hát trong CustomListView
+ trung bình điểm của tất cả khách hàng
+ Các điểm < 4 sẽ có màu đỏ, >= 4 sẽ có màu xanh
+ click vào nút "Sắp xếp" sẽ sắp xếp danh sách theo điểm đánh giá giảm dần

nhấn và giữ 1 phần tử trên CustomListVien thì hiển thị context menu
có 2 lựa chọn:
1, Sửa: không có sự kiện
2, xóa: hiển thị màn hình confirm xóa với các thông tin bài hát được chọn hiển thị
nếu đồng ý thì xóa phần tử khỏi cơ sở dữ liệu và cập nhật lại CustomListView, điểm trung bình