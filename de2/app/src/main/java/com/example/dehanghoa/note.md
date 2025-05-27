tạo class HangHoa.java gồm:
Mã: kiểu int, tăng dần bắt đầu từ 90, bước nhảy 30
Tên hàng: kiểu String, không được để trống
Giá niêm yết: kiểu int, không được để trống, giá trị lớn hơn 0
Giảm giá: kiểu boolen, true nếu có giảm giá, false nếu không

có constructor, gtetter, setter, toString
tạo method tinhGiaBan: tính giá bán theo nguyên tắc:
Nếu giảm giá = true thì giá bán = giá niêm yết
Nếu giảm giá = false thì giá bán = giá niêm yết - (15 * giá niêm yết / 100)

viết file SqliteHelper.java để quản lý cơ sở dữ liệu:
+tạo database
+nhập dữ liệu mẫu với các giá trị tùy chọn, minh chứng đã thực hiện tạo
cơ sở dữ liệu thành công có dữ liệu mẫu bằng Toast

