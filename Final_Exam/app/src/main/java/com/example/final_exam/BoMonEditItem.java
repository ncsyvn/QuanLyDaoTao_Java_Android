package com.example.final_exam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BoMonEditItem extends Activity {
    EditText name_edit_text, maKhoa_edit_text, moTa_edit_text;
    TextView id_text;
    Button btnEditOk;
    Button btnEditCancel;
    Button btnDelete;
    SQLiteDatabase db;
    Intent myLocalIntent;
    Bundle myBundle;
    String id;
    ListView list;

    String[] maMonHocList = new String[100];
    String[] tenMonHocList = new String[100];
    String[] maBoMonList = new String[100];
    String[] soTinChiList = new String[100];
    String[] soTietList = new String[100];
    String[] moTaList = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomon_edit_item);

        myLocalIntent = getIntent();
        myBundle = myLocalIntent.getExtras();

        id = myBundle.getString("id");
        String name = myBundle.getString("name");
        String maKhoa = myBundle.getString("maKhoa");
        String moTa = myBundle.getString("moTa");

        openDatabase();
        resizeMemoryList(id);
        queryCursor(id);

        BoMon_List_MonHoc_Adapter adapter = new BoMon_List_MonHoc_Adapter(this, maMonHocList, tenMonHocList, soTinChiList, soTietList);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);


        id_text = (TextView) findViewById(R.id.txtId);
        name_edit_text = (EditText) findViewById(R.id.txtName);
        maKhoa_edit_text = (EditText) findViewById(R.id.txtMaKhoa);
        moTa_edit_text = (EditText) findViewById(R.id.txtMoTa);

        btnEditOk = (Button) findViewById(R.id.save);
        btnEditCancel = (Button) findViewById(R.id.cancel);
        btnDelete = (Button) findViewById(R.id.delete);

        id_text.setText(id);
        name_edit_text.setText(name);
        maKhoa_edit_text.setText(maKhoa);
        moTa_edit_text.setText(moTa);

        btnEditOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = id_text.getText().toString();
                String name = name_edit_text.getText().toString();
                String maKhoa = maKhoa_edit_text.getText().toString();
                String moTa = moTa_edit_text.getText().toString();

                myBundle.putString("id", id);
                myBundle.putString("name", name);
                myBundle.putString("maKhoa", maKhoa);
                myBundle.putString("moTa", moTa);

                myLocalIntent.putExtras(myBundle);
                setResult(1, myLocalIntent);
                finish();
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
                String id = id_text.getText().toString();
                AlertDialog diaBox = createDialogBox();
                diaBox.show();
            }
        });
    }

    private AlertDialog createDialogBox() {
        AlertDialog myConfirmDeleteDialogBox =
                new AlertDialog.Builder(this)
                        //set message, title, and icon
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa bộ môn không?")
                        .setIcon(R.drawable.icon_delete)

                        //set three option buttons
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                myBundle.putString("id", id);
                                myLocalIntent.putExtras(myBundle);
                                setResult(2, myLocalIntent);
                                finish();
                            }
                        })//setPositiveButton
                        .setNeutralButton("Quay lại", new DialogInterface.OnClickListener() {
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

    public void resizeMemoryList(String id) {
        Cursor c = db.rawQuery("SELECT * FROM MonHoc WHERE maBoMon = '"+id+"'", null);

//        String[] columns = {"maMonHoc", "tenMonHoc", "maBoMon", "soTinChi", "soTiet", "moTa"};
//        Cursor c = db.query("MonHoc", columns,
//                null, null, null, null, "maMonHoc");
        int theTotal = c.getCount();
        maMonHocList = new String[theTotal];
        tenMonHocList = new String[theTotal];
        maBoMonList = new String[theTotal];
        soTinChiList = new String[theTotal];
        soTietList = new String[theTotal];
        moTaList = new String[theTotal];
    }

    private void queryCursor(String id) {
        int len = 0;
        try {
            String[] columns = {"maMonHoc", "tenMonHoc", "maBoMon", "soTinChi", "soTiet", "moTa"};

//            Cursor c = db.rawQuery("SELECT * FROM MonHoc WHERE maBoMon = '"+name+"'", null);
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
                System.out.println("c is: " + c.getString(maMonHocCol));
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa: " + id);
                if (c.getString(maBoMonCol).equals(id)) {
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
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
