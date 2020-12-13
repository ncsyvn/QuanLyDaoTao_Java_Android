package com.example.final_exam;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BoMon_List_MonHoc_Adapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] maMonHoc;
    private final String[] tenMonHoc;
    private final String[] soTinChi;
    private final String[] soTiet;

    public BoMon_List_MonHoc_Adapter(Activity context, String[] maMonHoc, String[] tenMonHoc,
                             String[] soTinChi, String[] soTiet) {
        super(context, R.layout.mylist, maMonHoc);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maMonHoc=maMonHoc;
        this.tenMonHoc=tenMonHoc;
        this.soTinChi=soTinChi;
        this.soTiet=soTiet;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.bomon_list_monhoc, null,true);

        TextView maMonHocTextView = (TextView) rowView.findViewById(R.id.txtMaMonHoc);
        TextView tenMonHocTextView = (TextView) rowView.findViewById(R.id.txtTenMonHoc);
        TextView soTinChiTextView = (TextView) rowView.findViewById(R.id.txtSoTinChi);
        TextView soTietTextView = (TextView) rowView.findViewById(R.id.txtSoTiet);

        maMonHocTextView.setText(maMonHoc[position]);
        tenMonHocTextView.setText(tenMonHoc[position]);
        soTinChiTextView.setText(soTinChi[position]);
        soTietTextView.setText(soTiet[position]);

        return rowView;
    };
}
