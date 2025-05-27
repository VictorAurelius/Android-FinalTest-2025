tạo xml cho mainacitivity.xml gồm:
1, thanh gang có 2 nút: Trái là biểu tượng tìm kiếm (tượng trưng), phải là nút sắp xếp
2, thanh tiêu đề: trái là "Giá trung bình", phải là giá trị Giá trung bình của tất cả hàng hóa
3, CustomListView để hiển thị danh sách hàng hóa:
mỗi phần tử gồm:
+ dòng 1: trái là tên hàng, phải là giá bán
+ dòng 2: trái là "Giảm giá còn", phải là giá đã giảm
+ hoặc dòng 2: "không giảm giá"

trong MainActivity.java:
+ hiển thị danh sách hàng hóa trong CustomListView
+ nếu hàng hóa có giảm giá thì hiển thị màu khác với giá gốc, ngược lại thì hiển thị "Không giảm giá"
+ Tính và hiển thi giá trung bình của tất cả hàng hóa
+ click vào nút "Sắp xếp" sẽ sắp xếp danh sách hàng hóa theo giảm dần về tên hàng hóa
