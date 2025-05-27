package com.example.dekhachhang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "khachhang.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_KHACH_HANG = "khachhang";
    private static final String COLUMN_MA = "ma";
    private static final String COLUMN_HO_TEN = "hoten";
    private static final String COLUMN_SO_DIEN_THOAI = "sodienthoai";
    private static final String COLUMN_NGAY_DANH_GIA = "ngaydanhgia";
    private static final String COLUMN_BINH_CHON = "binhchon";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_KHACH_HANG + "("
                + COLUMN_MA + " TEXT PRIMARY KEY,"
                + COLUMN_HO_TEN + " TEXT NOT NULL,"
                + COLUMN_SO_DIEN_THOAI + " TEXT NOT NULL,"
                + COLUMN_NGAY_DANH_GIA + " TEXT NOT NULL,"
                + COLUMN_BINH_CHON + " REAL NOT NULL"
                + ")";
        db.execSQL(createTableQuery);

        // Insert sample data
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KHACH_HANG);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        String[][] sampleData = {
            {"Nguyễn Văn An", "0912345678", "01/01/2023", "4.5"},
            {"Trần Thị Bình", "0923456789", "15/02/2023", "3.7"},
            {"Lê Văn Cường", "0934567890", "10/03/2023", "1.0"},
            {"Phạm Thị Dung", "0945678901", "05/04/2023", "4.2"},
            {"Hoàng Văn Em", "0956789012", "20/05/2023", "3.5"},
            {"Ngô Thị Fay", "0967890123", "15/06/2023", "4.8"},
            {"Đỗ Văn Giang", "0978901234", "01/07/2023", "2.9"},
            {"Vũ Thị Hoa", "0989012345", "18/08/2023", "4.1"},
            {"Bùi Văn Inh", "0990123456", "22/09/2023", "1.0"},
            {"Mai Thị Kiều", "0901234567", "30/10/2023", "2.0"}
        };

        for (String[] data : sampleData) {
            try {
                ContentValues values = new ContentValues();
                KhachHang khachHang = new KhachHang(
                    data[0],
                    data[1],
                    KhachHang.parseDate(data[2]),
                    Float.parseFloat(data[3])
                );

                values.put(COLUMN_MA, khachHang.getMa());
                values.put(COLUMN_HO_TEN, khachHang.getHoTen());
                values.put(COLUMN_SO_DIEN_THOAI, khachHang.getSoDienThoai());
                values.put(COLUMN_NGAY_DANH_GIA, khachHang.getNgayDanhGiaFormatted());
                values.put(COLUMN_BINH_CHON, khachHang.getBinhChon());

                db.insert(TABLE_KHACH_HANG, null, values);
            } catch (Exception e) {
                Log.e("SqliteHelper", "Error inserting sample data: " + e.getMessage());
            }
        }
    }

    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> khachHangList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_KHACH_HANG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        if (cursor.moveToFirst()) {
            do {
                try {
                    String ma = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MA));
                    String hoTen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HO_TEN));
                    String soDienThoai = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SO_DIEN_THOAI));
                    String ngayDanhGiaStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_DANH_GIA));
                    float binhChon = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_BINH_CHON));

                    Date ngayDanhGia = dateFormat.parse(ngayDanhGiaStr);
                    KhachHang khachHang = new KhachHang(ma, hoTen, soDienThoai, ngayDanhGia, binhChon);
                    khachHangList.add(khachHang);
                } catch (ParseException e) {
                    Log.e("SqliteHelper", "Error parsing date: " + e.getMessage());
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return khachHangList;
    }

    public void deleteKhachHang(String ma) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KHACH_HANG, COLUMN_MA + " = ?", new String[]{ma});
        db.close();
    }
}