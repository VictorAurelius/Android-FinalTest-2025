package com.example.dehanghoa;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hanghoa.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_HANGHOA = "hanghoa";
    private static final String COLUMN_MA = "ma";
    private static final String COLUMN_TEN = "ten";
    private static final String COLUMN_GIA = "gia";
    private static final String COLUMN_GIAMGIA = "giamgia";

    private Context context;

    public SqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_HANGHOA + " (" +
                COLUMN_MA + " INTEGER PRIMARY KEY, " +
                COLUMN_TEN + " TEXT NOT NULL, " +
                COLUMN_GIA + " INTEGER NOT NULL, " +
                COLUMN_GIAMGIA + " INTEGER NOT NULL);";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HANGHOA);
        onCreate(db);
    }

    public void insertSampleData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HANGHOA, null, null); // Clear existing data

        String[] productNames = {
                "Laptop Dell XPS", "iPhone 15 Pro", "Samsung Galaxy Tab S9",
                "Xiaomi Redmi Note 12", "Sony PlayStation 5", "Nintendo Switch OLED",
                "Apple Watch Series 9", "Samsung Smart TV", "Bose QuietComfort", "Canon EOS R6"
        };

        int[] prices = {
                25000000, 30000000, 18000000, 5000000, 15000000,
                8500000, 12000000, 20000000, 7500000, 45000000
        };

        boolean[] discounts = {
                true, false, true, false, true,
                false, true, false, true, false
        };

        ContentValues values = new ContentValues();
        boolean success = true;

        for (int i = 0; i < productNames.length; i++) {
            HangHoa hh = new HangHoa(productNames[i], prices[i], discounts[i]);

            values.clear();
            values.put(COLUMN_MA, hh.getMa());
            values.put(COLUMN_TEN, hh.getTenHang());
            values.put(COLUMN_GIA, hh.getGiaNiemYet());
            values.put(COLUMN_GIAMGIA, hh.isGiamGia() ? 1 : 0);

            long result = db.insert(TABLE_HANGHOA, null, values);
            if (result == -1) {
                success = false;
            }
        }

        Toast.makeText(context, success ?
                "Đã thêm 10 mẫu dữ liệu thành công" :
                "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();

        db.close();
    }
}