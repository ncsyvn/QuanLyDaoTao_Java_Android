package com.example.final_exam;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MonHocActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ListView list;
    Button btnAddItem;

    String[] maMonHocList = new String[100];
    String[] tenMonHocList = new String[100];
    String[] maBoMonList = new String[100];
    String[] soTinChiList = new String[100];
    String[] soTietList = new String[100];
    String[] moTaList = new String[100];
    String[] tenBoMonList = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monhoc_main);

        openDatabase();
        resizeMemoryList();
        queryCursor();
        queryTenBoMon();

        MonHocListAdapter adapter = new MonHocListAdapter(this, maMonHocList,
                tenMonHocList, tenBoMonList, soTinChiList, soTietList, moTaList);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        btnAddItem = (Button) findViewById(R.id.add);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MonHocActivity.this,
                        MonHocAddNewItem.class);
                Bundle myData = new Bundle();

                myData.putString("maMonHoc", "a");
                myData.putString("tenMonHoc", "a");
                myData.putString("maBoMon", "a");
                myData.putString("soTinChi", "a");
                myData.putString("soTiet", "a");
                myData.putString("moTa", "a");
                myIntent.putExtras(myData);
                startActivityForResult(myIntent, 101);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(MonHocActivity.this,
                        MonHocEditItem.class);
                Bundle myData = new Bundle();
                myData.putString("maMonHoc", maMonHocList[position]);
                myData.putString("tenMonHoc", tenMonHocList[position]);
                myData.putString("maBoMon", maBoMonList[position]);
                myData.putString("soTinChi", soTinChiList[position]);
                myData.putString("soTiet", soTietList[position]);
                myData.putString("moTa", moTaList[position]);
                myData.putString("tenBoMon", tenBoMonList[position]);

                myIntent.putExtras(myData);
                startActivityForResult(myIntent, 102);
            }
        });
    }

    public void openDatabase() {
        db = this.openOrCreateDatabase("qldt", MODE_PRIVATE, null);
    }

    public void resizeMemoryList() {
        String[] columns = {"maMonHoc", "tenMonHoc", "maBoMon", "soTinChi", "soTiet", "moTa"};
        Cursor c = db.query("MonHoc", columns,
                null, null, null, null, "maMonHoc");
        System.out.println(c);
        int theTotal = c.getCount();
        maMonHocList = new String[theTotal];
        tenMonHocList = new String[theTotal];
        maBoMonList = new String[theTotal];
        soTinChiList = new String[theTotal];
        soTietList = new String[theTotal];
        moTaList = new String[theTotal];
        tenBoMonList = new String[theTotal];
    }

    private void queryCursor() {
        int len = 0;
        try {
            // obtain a list of <id, name, phone> from DB
            String[] columns = {"maMonHoc", "tenMonHoc", "maBoMon", "soTinChi", "soTiet", "moTa"};

            Cursor c = db.query("MonHoc", columns,
                    null, null, null, null, "maMonHoc");
            int theTotal = c.getCount();
            Toast.makeText(this, "Total: " + theTotal, Toast.LENGTH_LONG).show();
            int maMonHocCol = c.getColumnIndex("maMonHoc");
            int tenMonHocCol = c.getColumnIndex("tenMonHoc");
            int maBoMonCol = c.getColumnIndex("maBoMon");
            int soTinChiCol = c.getColumnIndex("soTinChi");
            int soTietCol = c.getColumnIndex("soTiet");
            int moTaCol = c.getColumnIndex("moTa");

            while (c.moveToNext()) {
                columns[0] = c.getString(maMonHocCol);
                columns[1] = c.getString(tenMonHocCol);
                columns[2] = c.getString(maBoMonCol);
                columns[3] = Integer.toString((c.getInt(soTinChiCol)));
                columns[4] = Integer.toString((c.getInt(soTietCol)));
                columns[5] = c.getString(moTaCol);

                maMonHocList[len] = columns[0];
                tenMonHocList[len] = columns[1];
                maBoMonList[len] = columns[2];
                soTinChiList[len] = columns[3];
                soTietList[len] = columns[4];
                moTaList[len] = columns[5];

                len = len + 1;
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void queryTenBoMon() {
        int len = 0;
        try {
            // obtain a list of <id, name, phone> from DB
            String[] columns = {"maBoMon", "tenBoMon"};
            String[] maBoMonTmp = new String[100];
            String[] tenBoMonTmp = new String[100];

            Cursor c = db.query("BoMon", columns,
                    null, null, null, null, "tenBoMon");

            int maBoMonCol = c.getColumnIndex("maBoMon");
            int tenBoMonCol = c.getColumnIndex("tenBoMon");

            while (c.moveToNext()) {
                columns[0] = c.getString(maBoMonCol);
                columns[1] = c.getString(tenBoMonCol);

                maBoMonTmp[len] = columns[0];
                tenBoMonTmp[len] = columns[1];

                len = len + 1;
            }
            for (int i = 0; i < maBoMonList.length; i++) {
                for (int j = 0; j < maBoMonTmp.length; j++) {
                    if (maBoMonList[i].equals(maBoMonTmp[j])) {
                        tenBoMonList[i] = tenBoMonTmp[j];
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setListValue() {
        MonHocListAdapter adapter = new MonHocListAdapter(this, maMonHocList,
                tenMonHocList, tenBoMonList, soTinChiList, soTietList, moTaList);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        Bundle myResults = data.getExtras();
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 101) && (resultCode == 1)) {
            String maMonHoc = myResults.getString("maMonHoc");
            String tenMonHoc = myResults.getString("tenMonHoc");
            String maBoMon = myResults.getString("maBoMon");
            System.out.println(maBoMon);
            int soTinChi = Integer.parseInt(myResults.getString("soTinChi"));
            int soTiet = Integer.parseInt(myResults.getString("soTiet"));
            String moTa = myResults.getString("moTa");

            db.execSQL("insert into MonHoc(maMonHoc, tenMonHoc, maBoMon, soTinChi, soTiet, moTa) "
                    + " values ('" + maMonHoc + "','" + tenMonHoc + "','" + maBoMon + "'," + soTinChi + "," + soTiet + ",'" + moTa + "');");
            resizeMemoryList();
            queryCursor();
            queryTenBoMon();
            setListValue();
            Toast.makeText(getApplicationContext(), "Thêm mới môn học thành công", Toast.LENGTH_LONG).show();
        } else if ((requestCode == 101) && (resultCode == 0)) {
            queryCursor();
            queryTenBoMon();
            setListValue();
        } else if ((requestCode == 102) && (resultCode == 0)) {
            queryCursor();
            setListValue();
        } else if ((requestCode == 102) && (resultCode == 1)) {
            String maMonHoc = myResults.getString("maMonHoc");
            String tenMonHoc = myResults.getString("tenMonHoc");
            String maBoMon = myResults.getString("maBoMon");
            int soTinChi = Integer.parseInt(myResults.getString("soTinChi"));
            int soTiet = Integer.parseInt(myResults.getString("soTiet"));
            String moTa = myResults.getString("moTa");

            db.execSQL("update MonHoc set tenMonHoc = " + "'" + tenMonHoc + "'" + ", maBoMon = " + "'"
                    + maBoMon + "'" + ", soTinChi = " + soTinChi + ", soTiet = " + soTiet + ", moTa = " + "'" + moTa
                    + "'" + " where maMonHoc = '" + maMonHoc + "'");

            resizeMemoryList();
            queryCursor();
            setListValue();
            queryTenBoMon();
            Toast.makeText(getApplicationContext(), "Sửa thông tin môn học thành công", Toast.LENGTH_LONG).show();
        } else if ((requestCode == 102) && (resultCode == 2)) {
            String maMonHoc = myResults.getString("maMonHoc");
            System.out.println("Ma mon hoc : " + maMonHoc);
            db.execSQL("delete from MonHoc where maMonHoc = '" + maMonHoc + "'");
            resizeMemoryList();
            queryCursor();
            setListValue();
            queryTenBoMon();
            Toast.makeText(getApplicationContext(), "Xóa môn học thành công", Toast.LENGTH_LONG).show();
        }
    }
}
