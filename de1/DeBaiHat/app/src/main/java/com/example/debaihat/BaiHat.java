package com.example.debaihat;

public class BaiHat {
    private int id;
    private String tenBai;
    private String caSi;
    private int soLuotLike;
    private int soLuotShare;

    // Constructor
    public BaiHat(int id, String tenBai, String caSi, int soLuotLike, int soLuotShare) {
        this.id = id;
        this.tenBai = tenBai;
        this.caSi = caSi;
        this.soLuotLike = soLuotLike;
        this.soLuotShare = soLuotShare;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenBai() {
        return tenBai;
    }

    public void setTenBai(String tenBai) {
        this.tenBai = tenBai;
    }

    public String getCaSi() {
        return caSi;
    }

    public void setCaSi(String caSi) {
        this.caSi = caSi;
    }

    public int getSoLuotLike() {
        return soLuotLike;
    }

    public void setSoLuotLike(int soLuotLike) {
        this.soLuotLike = soLuotLike;
    }

    public int getSoLuotShare() {
        return soLuotShare;
    }

    public void setSoLuotShare(int soLuotShare) {
        this.soLuotShare = soLuotShare;
    }

    // Method to calculate points
    public int tinhDiem() {
        return soLuotLike + soLuotShare * 5 + 90;
    }

    // Method to get the last word of the singer's name
    public String getTenCaSiCuoi() {
        String[] words = caSi.split("\\s+");
        return words.length > 0 ? words[words.length - 1] : "";
    }
}