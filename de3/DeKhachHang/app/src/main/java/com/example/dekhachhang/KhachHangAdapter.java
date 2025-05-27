package com.example.dekhachhang;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class KhachHangAdapter extends ArrayAdapter<KhachHang> {
    private Context context;
    private List<KhachHang> khachHangList;

    public KhachHangAdapter(Context context, List<KhachHang> khachHangList) {
        super(context, R.layout.khach_hang_item, khachHangList);
        this.context = context;
        this.khachHangList = khachHangList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.khach_hang_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvHoTen = convertView.findViewById(R.id.tvHoTen);
            viewHolder.tvNgayDanhGia = convertView.findViewById(R.id.tvNgayDanhGia);
            viewHolder.tvSoDienThoai = convertView.findViewById(R.id.tvSoDienThoai);
            viewHolder.tvDiemDanhGia = convertView.findViewById(R.id.tvDiemDanhGia);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        KhachHang khachHang = khachHangList.get(position);
        float diemDanhGia = khachHang.tinhDiemDanhGia();

        viewHolder.tvHoTen.setText(khachHang.getHoTen());
        viewHolder.tvNgayDanhGia.setText(khachHang.getNgayDanhGiaFormatted());
        viewHolder.tvSoDienThoai.setText(khachHang.getSoDienThoai());
        viewHolder.tvDiemDanhGia.setText(String.format("%.1f", diemDanhGia));

        // Set color based on rating
        if (diemDanhGia < 4) {
            viewHolder.tvDiemDanhGia.setTextColor(Color.RED);
        } else {
            viewHolder.tvDiemDanhGia.setTextColor(Color.GREEN);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvHoTen;
        TextView tvNgayDanhGia;
        TextView tvSoDienThoai;
        TextView tvDiemDanhGia;
    }
}