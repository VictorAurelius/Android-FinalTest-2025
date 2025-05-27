package com.example.dekhachhang;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SqliteHelper dbHelper;
    private List<KhachHang> khachHangList;
    private KhachHangAdapter adapter;
    private ListView lvKhachHang;
    private TextView tvAverage;
    private Button btnSort;
    private boolean isSorted = false;

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
        lvKhachHang = findViewById(R.id.lvKhachHang);
        tvAverage = findViewById(R.id.tvAverage);
        btnSort = findViewById(R.id.btnSort);

        // Initialize database helper
        dbHelper = new SqliteHelper(this);

        // Load data
        loadData();

        // Register for context menu
        registerForContextMenu(lvKhachHang);

        // Set up sort button
        btnSort.setOnClickListener(v -> {
            if (isSorted) {
                // Reset to original order
                loadData();
                isSorted = false;
            } else {
                // Sort by rating in descending order
                Collections.sort(khachHangList, (kh1, kh2) ->
                    Float.compare(kh2.tinhDiemDanhGia(), kh1.tinhDiemDanhGia()));
                adapter.notifyDataSetChanged();
                isSorted = true;
            }
        });
    }

    private void loadData() {
        khachHangList = dbHelper.getAllKhachHang();
        adapter = new KhachHangAdapter(this, khachHangList);
        lvKhachHang.setAdapter(adapter);

        // Calculate average rating
        updateAverageRating();
    }

    private void updateAverageRating() {
        if (khachHangList.isEmpty()) {
            tvAverage.setText("0.0");
            return;
        }

        float sum = 0;
        for (KhachHang kh : khachHangList) {
            sum += kh.tinhDiemDanhGia();
        }
        float average = sum / khachHangList.size();
        tvAverage.setText(String.format("%.1f", average));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lvKhachHang) {
            menu.add(0, 1, 0, "Sửa");
            menu.add(0, 2, 1, "Xóa");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        KhachHang selectedKhachHang = khachHangList.get(info.position);

        switch (item.getItemId()) {
            case 1: // Edit - no action as per requirements
                return true;
            case 2: // Delete
                showDeleteConfirmationDialog(selectedKhachHang);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showDeleteConfirmationDialog(KhachHang khachHang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_delete, null);

        TextView tvDeleteHoTen = dialogView.findViewById(R.id.tvDeleteHoTen);
        TextView tvDeleteSoDienThoai = dialogView.findViewById(R.id.tvDeleteSoDienThoai);
        TextView tvDeleteNgayDanhGia = dialogView.findViewById(R.id.tvDeleteNgayDanhGia);
        TextView tvDeleteDiemDanhGia = dialogView.findViewById(R.id.tvDeleteDiemDanhGia);

        tvDeleteHoTen.setText("Họ tên: " + khachHang.getHoTen());
        tvDeleteSoDienThoai.setText("Số điện thoại: " + khachHang.getSoDienThoai());
        tvDeleteNgayDanhGia.setText("Ngày đánh giá: " + khachHang.getNgayDanhGiaFormatted());
        tvDeleteDiemDanhGia.setText("Điểm đánh giá: " + String.format("%.1f", khachHang.tinhDiemDanhGia()));

        builder.setView(dialogView)
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Delete from database
                    dbHelper.deleteKhachHang(khachHang.getMa());

                    // Reload data
                    loadData();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}