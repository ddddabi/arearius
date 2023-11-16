package com.example.arearius.adapter;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arearius.R;

public class ApkAdapter extends BaseAdapter {
    List<PackageInfo> packageList;
    Activity context;
    PackageManager packageManager;

    public ApkAdapter(Activity context, List<PackageInfo> packageList, PackageManager packageManager) {
        this.context = context;

        // 앱 이름으로 정렬
        Collections.sort(packageList, new Comparator<PackageInfo>() {
            public int compare(PackageInfo packageInfo1, PackageInfo packageInfo2) {
                String appName1 = packageManager.getApplicationLabel(packageInfo1.applicationInfo).toString();
                String appName2 = packageManager.getApplicationLabel(packageInfo2.applicationInfo).toString();
                return appName1.compareToIgnoreCase(appName2);
            }
        });

        this.packageList = packageList;
        this.packageManager = packageManager;
    }

    private class ViewHolder {
        TextView sizeTextView;
        TextView apkName;
        ImageView icon;
    }

    @Override
    public int getCount() {
        return packageList.size();
    }

    @Override
    public Object getItem(int position) {
        return packageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.main_lv_item, parent, false);
            holder = new ViewHolder();

            holder.apkName = convertView.findViewById(R.id.appname);
            holder.icon = convertView.findViewById(R.id.imageIcon);
            holder.sizeTextView = convertView.findViewById(R.id.size_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PackageInfo packageInfo = (PackageInfo) getItem(position);
        Drawable appIcon = packageManager.getApplicationIcon(packageInfo.applicationInfo);
        String appName = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
        appIcon.setBounds(0, 0, 80, 80);

        holder.icon.setImageDrawable(appIcon);
        holder.apkName.setText(appName);

        // 앱의 크기를 바이트 단위로 가져오기
        long appSizeInBytes = new File(packageInfo.applicationInfo.sourceDir).length();

        // size_tv에 앱 크기 표시
        holder.sizeTextView.setText(formatFileSize(appSizeInBytes));

        return convertView;
    }
    private String formatFileSize(long sizeInBytes) {
        double kiloByte = sizeInBytes / 1024.0;
        double megaByte = kiloByte / 1024.0;
        double gigaByte = megaByte / 1024.0;

        if (gigaByte >= 1.0) {
            return String.format(Locale.getDefault(), "%.2f GB", gigaByte);
        } else if (megaByte >= 1.0) {
            return String.format(Locale.getDefault(), "%.2f MB", megaByte);
        } else if (kiloByte >= 1.0) {
            return String.format(Locale.getDefault(), "%.2f KB", kiloByte);
        } else {
            return String.format(Locale.getDefault(), "%d Bytes", sizeInBytes);
        }
    }
}