package com.example.dehanghoa;

public class HangHoa {
    private static int AUTO_ID = 90;
    private int ma;
    private String tenHang;
    private int giaNiemYet;
    private boolean giamGia;

    public HangHoa() {
        this.ma = AUTO_ID;
        AUTO_ID += 30;
    }

    public HangHoa(String tenHang, int giaNiemYet, boolean giamGia) {
        this.ma = AUTO_ID;
        AUTO_ID += 30;

        if (tenHang == null || tenHang.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên hàng không được để trống");
        }

        if (giaNiemYet <= 0) {
            throw new IllegalArgumentException("Giá niêm yết phải lớn hơn 0");
        }

        this.tenHang = tenHang;
        this.giaNiemYet = giaNiemYet;
        this.giamGia = giamGia;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        if (tenHang == null || tenHang.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên hàng không được để trống");
        }
        this.tenHang = tenHang;
    }

    public int getGiaNiemYet() {
        return giaNiemYet;
    }

    public void setGiaNiemYet(int giaNiemYet) {
        if (giaNiemYet <= 0) {
            throw new IllegalArgumentException("Giá niêm yết phải lớn hơn 0");
        }
        this.giaNiemYet = giaNiemYet;
    }

    public boolean isGiamGia() {
        return giamGia;
    }

    public void setGiamGia(boolean giamGia) {
        this.giamGia = giamGia;
    }

    public int tinhGiaBan() {
        if (giamGia) {
            return giaNiemYet;
        } else {
            return giaNiemYet - (15 * giaNiemYet / 100);
        }
    }

    @Override
    public String toString() {
        return "HangHoa{" +
                "ma=" + ma +
                ", tenHang='" + tenHang + '\'' +
                ", giaNiemYet=" + giaNiemYet +
                ", giamGia=" + giamGia +
                ", giaBan=" + tinhGiaBan() +
                '}';
    }
}