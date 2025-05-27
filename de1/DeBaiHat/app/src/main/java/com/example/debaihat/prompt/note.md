tạo class BaiHat.java gồm:
id: kiểu int, tăng dần bắt đầu từ 90, bước nhảy 30
Tên bài: kiểu String, không được để trống
Ca sĩ: kiểu int, không được để trống
Số lượt like: kiểu int, long hơn hoặc bằng 0
Số lượt share: kiểu int, long hơn hoặc bằng 0

viết file SqliteHelper.java để quản lý cơ sở dữ liệu:
+ tạo database
+ nhập dữ liệu mẫu với các giá trị tùy chọn, minh chứng đã thực hiện tạo
cơ sở dữ liệu thành công có dữ liệu mẫu bằng Toast

viết method trong Class BaiHat.java: 
1, để tính: Điểm = Số lượt like + Số lượt share * 5 + 90
2, để lấy Tên ca sĩ là từ cuối cùng trong chuỗi Ca sĩ


tạo xml cho mainacitivity.xml gồm:
1, thanh gang: nút bên Trái là biểu tượng tìm kiếm (tượng trưng)
2, thanh tiêu đề: trái là "Trung bình", phải là giá trị Giá trung bình
3, CustomListView để hiển thị danh sách bài hát:
mỗi phần tử gồm:
+ dòng 1: trái là tên bài, phải là Điểm
+ dòng 2: trái là ca sỹ, phải là:
1, biểu tưởng trái tim và luợt like
2, biểu tượng chia sẻ và lượt share

trong MainActivity.java:
+ hiển thị danh sách bài hát trong CustomListView
+ trung bình điểm của tất cả bài hát
+ Các bài hát trên 160 điểm màu đỏ, dưới 160 điểm màu xanh
+ Sắp xếp danh sách bài hát theo giảm dần tên Tên ca sĩ mỗi khi hiển thị

nhấn và giữ 1 phần tử trên CustomListVien thì hiển thị context menu
có 2 lựa chọn:
1, Sửa: không có sự kiện
2, xóa: hiển thị màn hình confirm xóa với các thông tin bài hát được chọn hiển thị
nếu đồng ý thì xóa phần tử khỏi cơ sở dữ liệu và cập nhật lại CustomListView, điểm trung bình
+ thêm nút quay về trong dialog confirm