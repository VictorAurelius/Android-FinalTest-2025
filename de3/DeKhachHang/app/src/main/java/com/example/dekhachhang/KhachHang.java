package com.example.dekhachhang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KhachHang {
    private String ma;
    private String hoTen;
    private String soDienThoai;
    private Date ngayDanhGia;
    private float binhChon;
    private static int idCounter = 90;

    public KhachHang(String hoTen, String soDienThoai, Date ngayDanhGia, float binhChon) {
        this.ma = generateMa();
        setHoTen(hoTen);
        setSoDienThoai(soDienThoai);
        setNgayDanhGia(ngayDanhGia);
        setBinhChon(binhChon);
    }

    public KhachHang(String ma, String hoTen, String soDienThoai, Date ngayDanhGia, float binhChon) {
        this.ma = ma;
        setHoTen(hoTen);
        setSoDienThoai(soDienThoai);
        setNgayDanhGia(ngayDanhGia);
        setBinhChon(binhChon);
    }

    private String generateMa() {
        String result = "KH" + idCounter;
        idCounter += 30;
        return result;
    }

    public float tinhDiemDanhGia() {
        return binhChon + (5 - binhChon) * (10 + 1) / 100f;
    }

    // Getters and setters
    public String getMa() {
        return ma;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        if (hoTen == null || hoTen.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || soDienThoai.trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        if (!soDienThoai.matches("^0\\d{9}$")) {
            throw new IllegalArgumentException("Số điện thoại phải có 10 số và bắt đầu bằng 0");
        }
        this.soDienThoai = soDienThoai;
    }

    public Date getNgayDanhGia() {
        return ngayDanhGia;
    }

    public String getNgayDanhGiaFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(ngayDanhGia);
    }

    public void setNgayDanhGia(Date ngayDanhGia) {
        if (ngayDanhGia == null) {
            throw new IllegalArgumentException("Ngày đánh giá không được để trống");
        }
        this.ngayDanhGia = ngayDanhGia;
    }

    public float getBinhChon() {
        return binhChon;
    }

    public void setBinhChon(float binhChon) {
        if (binhChon < 0 || binhChon > 5) {
            throw new IllegalArgumentException("Bình chọn phải từ 0 đến 5");
        }
        // Làm tròn đến 1 chữ số thập phân
        this.binhChon = Math.round(binhChon * 10) / 10f;
    }

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.parse(dateString);
    }
}