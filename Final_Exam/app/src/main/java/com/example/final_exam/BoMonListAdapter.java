package com.example.final_exam;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BoMonListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] maBoMon;
    private final String[] tenBoMon;
    private final String[] maKhoa;
    private final String[] moTa;

    public BoMonListAdapter(Activity context, String[] maBoMon, String[] tenBoMon,
                            String[] maKhoa, String[] moTa) {
        super(context, R.layout.mylist, maBoMon);
        // TODO Auto-generated constructor stub
//        for (int i=0; i<maBoMon.length; i++){
//            if (maBoMon[i] == null && tenBoMon[i] == null && maKhoa[i] == null && moTa[i] == null){
//
//            }
//        }
        this.context=context;
        this.maBoMon=maBoMon;
        this.tenBoMon=tenBoMon;
        this.maKhoa=maKhoa;
        this.moTa=moTa;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.bomon_list, null,true);

        TextView maBoMonTextView = (TextView) rowView.findViewById(R.id.maBoMon);
        TextView tenBoMonTextView = (TextView) rowView.findViewById(R.id.tenBoMon);
        TextView maKhoaTextView = (TextView) rowView.findViewById(R.id.maKhoa);
        TextView moTaTextView = (TextView) rowView.findViewById(R.id.moTa);

        maBoMonTextView.setText(maBoMon[position]);
        tenBoMonTextView.setText(tenBoMon[position]);
        maKhoaTextView.setText(maKhoa[position]);
        moTaTextView.setText(moTa[position]);

        return rowView;

    };
}
