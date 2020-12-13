package com.example.final_exam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

public class MonHocEditItem extends Activity implements
        AdapterView.OnItemSelectedListener{
    EditText tenMonHoc_text, tenBoMon_text, soTinChi_text, soTiet_text, moTa_text;
    TextView maMonHoc_text;
    Button btnEditOk;
    Button btnEditCancel;
    Button btnDelete;
    SQLiteDatabase db;
    Intent myLocalIntent;
    Bundle myBundle;
    String maMonHoc;
    String[] maBoMonList = new String[100];
    String[] tenBoMonList = new String[100];
    int positionChooseBoMon = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monhoc_edit_item);

        myLocalIntent = getIntent();
        myBundle = myLocalIntent.getExtras();

        openDatabase();
        resizeMemoryList();
        queryCursor();

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tenBoMonList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        maMonHoc = myBundle.getString("maMonHoc");
        String tenMonHoc = myBundle.getString("tenMonHoc");
        String maBoMon = myBundle.getString("maBoMon");
        String soTinChi = myBundle.getString("soTinChi");
        String soTiet = myBundle.getString("soTiet");
        String moTa = myBundle.getString("moTa");
        String tenBoMon = myBundle.getString("tenBoMon");

        System.out.println("Ma bo mon is: " + maBoMon);
        System.out.println("ten bo mon is: " + tenBoMon);

        // Đưa mã bộ môn về vị trí 0, tiến những cái còn lại lên 1 index
        for (int i=0; i<maBoMonList.length; i++)
        {
            if (maBoMonList[i].equals(maBoMon)){
                if (i!=0){
                    for (int j=i; j>0; j--){
                        tenBoMonList[j] = tenBoMonList[j-1];
                        maBoMonList[j] = maBoMonList[j-1];
                    }
                    maBoMonList[0] = maBoMon;
                    tenBoMonList[0] = tenBoMon;
                }
            }
        }

        maMonHoc_text = (TextView) findViewById(R.id.txtMaMonHoc);
        tenMonHoc_text = (EditText) findViewById(R.id.txtTenMonHoc);
        tenBoMon_text = (EditText) findViewById(R.id.txtTenBoMon);
        soTinChi_text = (EditText) findViewById(R.id.txtSoTinChi);
        soTiet_text = (EditText) findViewById(R.id.txtSoTiet);
        moTa_text = (EditText) findViewById(R.id.txtMoTa);

        btnEditOk = (Button) findViewById(R.id.save);
        btnEditCancel = (Button) findViewById(R.id.cancel);
        btnDelete = (Button) findViewById(R.id.delete);

        maMonHoc_text.setText(maMonHoc);
        tenMonHoc_text.setText(tenMonHoc);
        tenBoMon_text.setText(tenBoMon);
        soTinChi_text.setText(soTinChi);
        soTiet_text.setText(soTiet);
        moTa_text.setText(moTa);

        btnEditOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maMonHoc = maMonHoc_text.getText().toString();
                String tenMonHoc = tenMonHoc_text.getText().toString();
                String tenBoMon = tenBoMon_text.getText().toString();
                String soTinChi = soTinChi_text.getText().toString();
                String soTiet = soTiet_text.getText().toString();
                String moTa = moTa_text.getText().toString();
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
                } else
                {
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
        btnEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocalIntent.putExtras(myBundle);
                setResult(0, myLocalIntent);
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maMonHoc = maMonHoc_text.getText().toString();
                AlertDialog diaBox = createDialogBox();
                diaBox.show();
            }
        });
    }
    private AlertDialog createDialogBox(){
        AlertDialog myConfirmDeleteDialogBox =
                new AlertDialog.Builder(this)
                        //set message, title, and icon
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa môn học không?")
                        .setIcon(R.drawable.icon_delete)

                        //set three option buttons
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                myBundle.putString("maMonHoc", maMonHoc);
                                myLocalIntent.putExtras(myBundle);
                                setResult(2, myLocalIntent);
                                finish();
                            }
                        })//setPositiveButton
                        .setNeutralButton("Quay lại",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }//OnClick
                        })//setNeutralButton
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })//setNegativeButton
                        .create();
        return myConfirmDeleteDialogBox;
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

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        positionChooseBoMon = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
