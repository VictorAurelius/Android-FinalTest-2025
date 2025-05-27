package com.example.debaihat;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TextView tvGiaTrungBinh;
    private List<BaiHat> baiHatList;
    private BaiHatAdapter adapter;
    private SqliteHelper dbHelper;

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

        listView = findViewById(R.id.listViewBaiHat);
        tvGiaTrungBinh = findViewById(R.id.tvGiaTrungBinh);

        dbHelper = new SqliteHelper(this);
        baiHatList = dbHelper.getAllBaiHat();

        // Sort by singer name in descending order
        sortBySingerNameDesc();

        adapter = new BaiHatAdapter(this, baiHatList);
        listView.setAdapter(adapter);

        // Calculate and display the average score
        updateAverageScore();

        // Register context menu
        registerForContextMenu(listView);
    }

    private void sortBySingerNameDesc() {
        Collections.sort(baiHatList, new Comparator<BaiHat>() {
            @Override
            public int compare(BaiHat b1, BaiHat b2) {
                return b2.getTenCaSiCuoi().compareTo(b1.getTenCaSiCuoi());
            }
        });
    }

    private void updateAverageScore() {
        if (baiHatList.isEmpty()) {
            tvGiaTrungBinh.setText("0");
            return;
        }

        int totalScore = 0;
        for (BaiHat baiHat : baiHatList) {
            totalScore += baiHat.tinhDiem();
        }

        double avgScore = (double) totalScore / baiHatList.size();
        tvGiaTrungBinh.setText(String.format("%.2f", avgScore));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "Sửa");
        menu.add(0, 2, 0, "Xóa");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        BaiHat selectedBaiHat = baiHatList.get(info.position);

        switch (item.getItemId()) {
            case 1: // Edit - No functionality as per requirements
                return true;
            case 2: // Delete
                showDeleteConfirmDialog(selectedBaiHat);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showDeleteConfirmDialog(final BaiHat baiHat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirm_delete, null);
        builder.setView(dialogView);

        TextView tvTenBaiXoa = dialogView.findViewById(R.id.tvTenBaiXoa);
        TextView tvCaSiXoa = dialogView.findViewById(R.id.tvCaSiXoa);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnDelete = dialogView.findViewById(R.id.btnDelete);

        tvTenBaiXoa.setText("Tên bài: " + baiHat.getTenBai());
        tvCaSiXoa.setText("Ca sĩ: " + baiHat.getCaSi());

        final AlertDialog dialog = builder.create();

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnDelete.setOnClickListener(v -> {
            if (dbHelper.deleteBaiHat(baiHat.getId())) {
                baiHatList.remove(baiHat);
                adapter.notifyDataSetChanged();
                updateAverageScore();
            }
            dialog.dismiss();
        });

        dialog.show();
    }
}