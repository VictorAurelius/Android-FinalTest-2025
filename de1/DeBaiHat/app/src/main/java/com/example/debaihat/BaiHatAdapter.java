package com.example.debaihat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BaiHatAdapter extends BaseAdapter {
    private Context context;
    private List<BaiHat> baiHatList;

    public BaiHatAdapter(Context context, List<BaiHat> baiHatList) {
        this.context = context;
        this.baiHatList = baiHatList;
    }

    @Override
    public int getCount() {
        return baiHatList.size();
    }

    @Override
    public BaiHat getItem(int position) {
        return baiHatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_bai_hat, parent, false);
            holder = new ViewHolder();
            holder.tvTenBai = convertView.findViewById(R.id.tvTenBai);
            holder.tvDiem = convertView.findViewById(R.id.tvDiem);
            holder.tvCaSi = convertView.findViewById(R.id.tvCaSi);
            holder.tvLuotLike = convertView.findViewById(R.id.tvLuotLike);
            holder.tvLuotShare = convertView.findViewById(R.id.tvLuotShare);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BaiHat baiHat = getItem(position);

        holder.tvTenBai.setText(baiHat.getTenBai());
        holder.tvDiem.setText(String.valueOf(baiHat.tinhDiem()));
        holder.tvCaSi.setText(baiHat.getCaSi());
        holder.tvLuotLike.setText(String.valueOf(baiHat.getSoLuotLike()));
        holder.tvLuotShare.setText(String.valueOf(baiHat.getSoLuotShare()));

        // Set color based on score
        if (baiHat.tinhDiem() > 160) {
            holder.tvDiem.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            holder.tvDiem.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTenBai;
        TextView tvDiem;
        TextView tvCaSi;
        TextView tvLuotLike;
        TextView tvLuotShare;
        // Removed ImageView references from ViewHolder since they're set once during inflation
    }
}