package com.example.dehanghoa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView listViewHangHoa;
    private TextView tvGiaTrungBinh;
    private Button btnSort;
    private List<HangHoa> hangHoaList;
    private HangHoaAdapter adapter;
    private SqliteHelper dbHelper;

    private static final int MENU_EDIT = 1;
    private static final int MENU_DELETE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        listViewHangHoa = findViewById(R.id.listViewHangHoa);
        tvGiaTrungBinh = findViewById(R.id.tvGiaTrungBinh);
        btnSort = findViewById(R.id.btnSort);

        // Initialize database helper
        dbHelper = new SqliteHelper(this);

        // Initialize data
        loadDataFromDatabase();

        // Set up sort button click listener
        btnSort.setOnClickListener(v -> sortHangHoaByNameDescending());

        // Register for context menu
        registerForContextMenu(listViewHangHoa);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewHangHoa) {
            menu.setHeaderTitle("Tùy chọn");
            menu.add(0, MENU_EDIT, 0, "Sửa");
            menu.add(0, MENU_DELETE, 1, "Xóa");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        HangHoa selectedItem = hangHoaList.get(position);

        switch (item.getItemId()) {
            case MENU_EDIT:
                // No action required for "Edit" as per requirements
                Toast.makeText(this, "Chức năng sửa chưa được cài đặt", Toast.LENGTH_SHORT).show();
                return true;
            case MENU_DELETE:
                showDeleteConfirmationDialog(selectedItem, position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showDeleteConfirmationDialog(HangHoa hangHoa, int position) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");

        StringBuilder message = new StringBuilder();
        message.append("Bạn có chắc chắn muốn xóa hàng hóa này?\n\n")
                .append("Mã: ").append(hangHoa.getMa()).append("\n")
                .append("Tên: ").append(hangHoa.getTenHang()).append("\n")
                .append("Giá niêm yết: ").append(currencyFormat.format(hangHoa.getGiaNiemYet())).append("\n");

        if (!hangHoa.isGiamGia()) {
            // Has 15% discount
            message.append("Giảm giá: Có (15%)\n")
                    .append("Giá sau giảm: ").append(currencyFormat.format(hangHoa.tinhGiaBan()));
        } else {
            // No discount
            message.append("Giảm giá: Không");
        }

        builder.setMessage(message.toString());

        builder.setPositiveButton("Đồng ý", (dialog, which) -> {
            deleteHangHoaFromDB(hangHoa.getMa());
            hangHoaList.remove(position);
            adapter.notifyDataSetChanged();
            calculateAndDisplayAveragePrice();
            Toast.makeText(MainActivity.this, "Đã xóa hàng hóa", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteHangHoaFromDB(int maHangHoa) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "ma = ?";
        String[] selectionArgs = {String.valueOf(maHangHoa)};
        db.delete("hanghoa", selection, selectionArgs);
    }

    private void loadDataFromDatabase() {
        hangHoaList = new ArrayList<>();

        // First, ensure we have sample data
        dbHelper.insertSampleData();

        // Query the database to get all HangHoa items
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "ma",
                "ten",
                "gia",
                "giamgia"
        };

        Cursor cursor = db.query(
                "hanghoa",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int ma = cursor.getInt(cursor.getColumnIndexOrThrow("ma"));
            String ten = cursor.getString(cursor.getColumnIndexOrThrow("ten"));
            int gia = cursor.getInt(cursor.getColumnIndexOrThrow("gia"));
            boolean giamGia = cursor.getInt(cursor.getColumnIndexOrThrow("giamgia")) == 1;

            HangHoa hangHoa = new HangHoa(ten, gia, giamGia);
            hangHoa.setMa(ma);
            hangHoaList.add(hangHoa);
        }

        cursor.close();

        // Set up the adapter
        adapter = new HangHoaAdapter(this, hangHoaList);
        listViewHangHoa.setAdapter(adapter);

        // Calculate and display average price
        calculateAndDisplayAveragePrice();
    }

    private void calculateAndDisplayAveragePrice() {
        if (hangHoaList.isEmpty()) {
            tvGiaTrungBinh.setText("0");
            return;
        }

        int totalPrice = 0;
        for (HangHoa hangHoa : hangHoaList) {
            totalPrice += hangHoa.tinhGiaBan();
        }

        double averagePrice = (double) totalPrice / hangHoaList.size();

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvGiaTrungBinh.setText(currencyFormat.format(averagePrice));
    }

    private void sortHangHoaByNameDescending() {
        Collections.sort(hangHoaList, (hh1, hh2) ->
                hh2.getTenHang().compareToIgnoreCase(hh1.getTenHang()));

        adapter.notifyDataSetChanged();
    }
}