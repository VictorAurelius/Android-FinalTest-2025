package com.example.debaihat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "baihat_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_BAIHAT = "baihat";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEN_BAI = "ten_bai";
    private static final String COLUMN_CA_SI = "ca_si";
    private static final String COLUMN_LUOT_LIKE = "luot_like";
    private static final String COLUMN_LUOT_SHARE = "luot_share";

    private Context context;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_BAIHAT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TEN_BAI + " TEXT NOT NULL,"
                + COLUMN_CA_SI + " TEXT NOT NULL,"
                + COLUMN_LUOT_LIKE + " INTEGER DEFAULT 0,"
                + COLUMN_LUOT_SHARE + " INTEGER DEFAULT 0"
                + ")";
        db.execSQL(CREATE_TABLE);

        // Insert sample data
        themDuLieuMau(db);

        Toast.makeText(context, "Đã tạo cơ sở dữ liệu thành công với dữ liệu mẫu", Toast.LENGTH_SHORT).show();
    }

    private void themDuLieuMau(SQLiteDatabase db) {
        int id = 90; // Starting ID

        // Sample data
        themBaiHat(db, id, "Chắc Ai Đó Sẽ Về", "Hà Hà", 1500, 300);
        id += 30;
        themBaiHat(db, id, "Lạc Trôi", "Thanh Cao", 2000, 400);
        id += 30;
        themBaiHat(db, id, "Nơi Này Có Anh", "Trần Bình", 1800, 350);
        id += 30;
        themBaiHat(db, id, "Hãy Trao Cho Anh", "Sơn Tùng", 1600, 280);
        id += 30;
        themBaiHat(db, id, "Có Chắc Yêu Là Đây", "Lạc Anh", 1, 1);
    }

    private void themBaiHat(SQLiteDatabase db, int id, String tenBai, String caSi, int luotLike, int luotShare) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_TEN_BAI, tenBai);
        values.put(COLUMN_CA_SI, caSi);
        values.put(COLUMN_LUOT_LIKE, luotLike);
        values.put(COLUMN_LUOT_SHARE, luotShare);

        db.insert(TABLE_BAIHAT, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAIHAT);
        onCreate(db);
    }

    // Get all songs
    public List<BaiHat> getAllBaiHat() {
        List<BaiHat> baiHatList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BAIHAT, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String tenBai = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_BAI));
                String caSi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CA_SI));
                int luotLike = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LUOT_LIKE));
                int luotShare = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LUOT_SHARE));

                BaiHat baiHat = new BaiHat(id, tenBai, caSi, luotLike, luotShare);
                baiHatList.add(baiHat);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return baiHatList;
    }

    // Delete song
    public boolean deleteBaiHat(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BAIHAT, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}