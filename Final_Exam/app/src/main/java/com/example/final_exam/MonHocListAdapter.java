package com.example.final_exam;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MonHocListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] maMonHoc;
    private final String[] tenMonHoc;
    private final String[] tenBoMon;
    private final String[] soTinChi;
    private final String[] soTiet;
    private final String[] moTa;

    public MonHocListAdapter(Activity context, String[] maMonHoc, String[] tenMonHoc, String[] tenBoMon,
                             String[] soTinChi, String[] soTiet, String[] moTa) {
        super(context, R.layout.mylist, maMonHoc);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maMonHoc=maMonHoc;
        this.tenMonHoc=tenMonHoc;
        this.tenBoMon = tenBoMon;
        this.soTinChi=soTinChi;
        this.soTiet=soTiet;
        this.moTa=moTa;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.monhoc_list, null,true);

        TextView maMonHocTextView = (TextView) rowView.findViewById(R.id.maMonHoc);
        TextView tenMonHocTextView = (TextView) rowView.findViewById(R.id.tenMonHoc);
        TextView maBoMonTextView = (TextView) rowView.findViewById(R.id.tenBoMon);
        TextView soTinChiTextView = (TextView) rowView.findViewById(R.id.soTinChi);
        TextView soTietTextView = (TextView) rowView.findViewById(R.id.soTiet);
        TextView moTaTextView = (TextView) rowView.findViewById(R.id.moTa);

        maMonHocTextView.setText(maMonHoc[position]);
        tenMonHocTextView.setText(tenMonHoc[position]);
        maBoMonTextView.setText(tenBoMon[position]);
        soTinChiTextView.setText(soTinChi[position]);
        soTietTextView.setText(soTiet[position]);
        moTaTextView.setText(moTa[position]);

        return rowView;
    };
}
