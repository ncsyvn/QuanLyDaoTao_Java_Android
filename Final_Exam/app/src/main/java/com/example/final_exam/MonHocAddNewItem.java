package com.example.final_exam;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MonHocAddNewItem extends Activity implements
        AdapterView.OnItemSelectedListener {
    SQLiteDatabase db;
    EditText maMonHoc_text, tenMonHoc_text, tenBoMon_text, soTinChi_text, soTiet_text, moTa_text;
    Button btnAdd;
    Button btnCancel;
    String[] maBoMonList = new String[100];
    String[] tenBoMonList = new String[100];
    int positionChooseBoMon = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monhoc_add_new_item);

        final Intent myLocalIntent = getIntent();
        final Bundle myBundle = myLocalIntent.getExtras();

        btnAdd = (Button) findViewById(R.id.add);
        btnCancel = (Button) findViewById(R.id.cancel);
        maMonHoc_text = (EditText) findViewById(R.id.txtMaMonHoc);
        tenMonHoc_text = (EditText) findViewById(R.id.txtTenMonHoc);
        tenBoMon_text = (EditText) findViewById(R.id.txtTenBoMon);
        soTinChi_text = (EditText) findViewById(R.id.txtSoTinChi);
        soTiet_text = (EditText) findViewById(R.id.txtSoTiet);
        moTa_text = (EditText) findViewById(R.id.txtMoTa);

        openDatabase();
        resizeMemoryList();
        queryCursor();
        for (int i=0; i<tenBoMonList.length; i++){
            System.out.println(tenBoMonList[i]);
        }
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tenBoMonList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maMonHoc, tenMonHoc, tenBoMon, soTinChi, soTiet, moTa;
                maMonHoc = "";
                tenMonHoc = "";
                tenBoMon = "";
                soTinChi = "";
                soTiet = "";
                moTa = "";
                maMonHoc = maMonHoc_text.getText().toString();
                tenMonHoc = tenMonHoc_text.getText().toString();
                tenBoMon = tenBoMon_text.getText().toString();
                soTinChi = soTinChi_text.getText().toString();
                soTiet = soTiet_text.getText().toString();
                moTa = moTa_text.getText().toString();

                if (maMonHoc.equals("")) {
                    Toast.makeText(getApplicationContext(), "Mã môn học không được bỏ trống", Toast.LENGTH_LONG).show();
                } else if (soTinChi.equals("")) {
                    Toast.makeText(getApplicationContext(), "Số tín chỉ không được bỏ trống", Toast.LENGTH_LONG).show();
                } else if (soTiet.equals("")) {
                    Toast.makeText(getApplicationContext(), "Số tiết không được bỏ trống", Toast.LENGTH_LONG).show();
                } else if (checkInt(soTinChi) == 0) {
                    Toast.makeText(getApplicationContext(), "Số tín chỉ phải là số", Toast.LENGTH_LONG).show();
                } else if (checkInt(soTiet) == 0) {
                    Toast.makeText(getApplicationContext(), "Số tiết phải là số", Toast.LENGTH_LONG).show();
                } else {
                    myBundle.putString("maMonHoc", maMonHoc);
                    myBundle.putString("tenMonHoc", tenMonHoc);
                    myBundle.putString("maBoMon", maBoMonList[positionChooseBoMon]);
                    myBundle.putString("soTinChi", soTinChi);
                    myBundle.putString("soTiet", soTiet);
                    myBundle.putString("moTa", moTa);

                    myLocalIntent.putExtras(myBundle);
                    setResult(1, myLocalIntent);
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocalIntent.putExtras(myBundle);
                setResult(0, myLocalIntent);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        positionChooseBoMon = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void openDatabase() {
        db = this.openOrCreateDatabase("qldt", MODE_PRIVATE, null);
    }

    public void resizeMemoryList() {
        String[] columns = {"maBoMon", "tenBoMon"};
        Cursor c = db.query("BoMon", columns,
                null, null, null, null, "maBoMon");
        int theTotal = c.getCount();
        maBoMonList = new String[theTotal];
        tenBoMonList = new String[theTotal];
    }

    private void queryCursor() {
        int len = 0;
        try {
            // obtain a list of <id, name, phone> from DB
            String[] columns = {"maBoMon", "tenBoMon"};

            Cursor c = db.query("BoMon", columns,
                    null, null, null, null, "maBoMon");
            int theTotal = c.getCount();
            Toast.makeText(this, "Total: " + theTotal, Toast.LENGTH_LONG).show();
            int maBoMonCol = c.getColumnIndex("maBoMon");
            int tenBoMonCol = c.getColumnIndex("tenBoMon");

            while (c.moveToNext()) {
                columns[0] = c.getString(maBoMonCol);
                columns[1] = c.getString(tenBoMonCol);

                maBoMonList[len] = columns[0];
                tenBoMonList[len] = columns[1];

                len = len + 1;
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public int checkInt(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '0' && s.charAt(i) != '1' && s.charAt(i) != '2' && s.charAt(i) != '3'
                    && s.charAt(i) != '4' & s.charAt(i) != '5' && s.charAt(i) != '6' && s.charAt(i) != '7'
                    && s.charAt(i) != '8' && s.charAt(i) != '9') {
                return 0;
            }
        }
        return 1;
    }
}

