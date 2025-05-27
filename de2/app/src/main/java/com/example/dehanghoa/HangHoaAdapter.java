package com.example.dehanghoa;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HangHoaAdapter extends ArrayAdapter<HangHoa> {

    private Context context;
    private List<HangHoa> hangHoaList;

    public HangHoaAdapter(@NonNull Context context, List<HangHoa> hangHoaList) {
        super(context, 0, hangHoaList);
        this.context = context;
        this.hangHoaList = hangHoaList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_hang_hoa, parent, false);
        }

        HangHoa currentHangHoa = hangHoaList.get(position);

        TextView tvTenHang = listItem.findViewById(R.id.tvTenHang);
        TextView tvGiaBan = listItem.findViewById(R.id.tvGiaBan);
        TextView tvGiamGiaLabel = listItem.findViewById(R.id.tvGiamGiaLabel);
        TextView tvGiaSauGiam = listItem.findViewById(R.id.tvGiaSauGiam);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        tvTenHang.setText(currentHangHoa.getTenHang());
        tvGiaBan.setText(currencyFormat.format(currentHangHoa.getGiaNiemYet()));

        if (!currentHangHoa.isGiamGia()) {
            // Item has 15% discount
            tvGiamGiaLabel.setText("Giảm giá còn");
            tvGiaSauGiam.setText(currencyFormat.format(currentHangHoa.tinhGiaBan()));
            tvGiaSauGiam.setTextColor(Color.RED);
        } else {
            // No discount
            tvGiamGiaLabel.setText("Không giảm giá");
            tvGiaSauGiam.setText("");
        }

        return listItem;
    }
}