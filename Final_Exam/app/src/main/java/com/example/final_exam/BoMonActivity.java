package com.example.final_exam;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BoMonActivity extends AppCompatActivity {
    SQLiteDatabase db;
    ListView list;
    Button btnAddItem;

    String[] maBoMonList = new String[100];
    String[] tenBoMonList = new String[100];
    String[] maKhoaList = new String[100];
    String[] moTaList = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomon_main);

        openDatabase();
        resizeMemoryList();
        queryCursor();

        BoMonListAdapter adapter=new BoMonListAdapter(this, maBoMonList, tenBoMonList, maKhoaList, moTaList);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        btnAddItem = (Button) findViewById(R.id.add);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent (BoMonActivity.this,
                        BoMonAddNewItem.class);
                Bundle myData = new Bundle();

                myData.putString("id", "a");
                myData.putString("name", "a");
                myData.putString("maKhoa", "a");
                myData.putString("moTa", "a");
                myIntent.putExtras(myData);
                startActivityForResult(myIntent, 101);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent (BoMonActivity.this,
                        BoMonEditItem.class);
                Bundle myData = new Bundle();

                myData.putString("id", maBoMonList[position]);
                myData.putString("name", tenBoMonList[position]);
                myData.putString("maKhoa", maKhoaList[position]);
                myData.putString("moTa", moTaList[position]);
                myIntent.putExtras(myData);
                startActivityForResult(myIntent, 102);

                if(position == 0) {
                    //code specific to first list item
                    Toast.makeText(getApplicationContext(),"Place Your First Option Code",Toast.LENGTH_SHORT).show();
                }

                else if(position == 1) {
                    //code specific to 2nd list item
                    Toast.makeText(getApplicationContext(),"Place Your Second Option Code",Toast.LENGTH_SHORT).show();
                }

                else if(position == 2) {

                    Toast.makeText(getApplicationContext(),"Place Your Third Option Code",Toast.LENGTH_SHORT).show();
                }
                else if(position == 3) {

                    Toast.makeText(getApplicationContext(),"Place Your Forth Option Code",Toast.LENGTH_SHORT).show();
                }
                else if(position == 4) {

                    Toast.makeText(getApplicationContext(),"Place Your Fifth Option Code",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void openDatabase(){
        db = this.openOrCreateDatabase("qldt", MODE_PRIVATE, null);
    }
    public void resizeMemoryList(){
        String[] columns = { "maBoMon", "tenBoMon", "maKhoa", "moTa"};
        Cursor c = db.query("BoMon", columns,
                null, null, null, null, "maBoMon");
        int theTotal = c.getCount();
        maBoMonList = new String[theTotal];
        tenBoMonList = new String[theTotal];
        maKhoaList = new String[theTotal];
        moTaList = new String[theTotal];
    }
    private void queryCursor() {
        int len=0;
        try {
            // obtain a list of <id, name, phone> from DB
            String[] columns = { "maBoMon", "tenBoMon", "maKhoa", "moTa"};

            Cursor c = db.query("BoMon", columns,
                    null, null, null, null, "maBoMon");
            int theTotal = c.getCount();
            Toast.makeText(this, "Total: " + theTotal, Toast.LENGTH_LONG).show();
            int idCol = c.getColumnIndex("maBoMon");
            int nameCol = c.getColumnIndex("tenBoMon");
            int maKhoaCol = c.getColumnIndex("maKhoa");
            int moTaCol = c.getColumnIndex("moTa");


            while (c.moveToNext()) {
                columns[0] = c.getString(idCol);
                columns[1] = c.getString(nameCol);
                columns[2] = c.getString(maKhoaCol);
                columns[3] = c.getString(moTaCol);

                maBoMonList[len] = columns[0];
                tenBoMonList[len] = columns[1];
                maKhoaList[len] = columns[2];
                moTaList[len] = columns[3];

                len = len + 1;
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void setListValue(){
        BoMonListAdapter adapter=new BoMonListAdapter(this, maBoMonList, tenBoMonList, maKhoaList, moTaList);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        Bundle myResults = data.getExtras();
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 101 ) && (resultCode == 1)){
            String id = myResults.getString("id");
            String name = myResults.getString("name");
            String maKhoa = myResults.getString("maKhoa");
            String moTa = myResults.getString("moTa");

            db.execSQL("insert into BoMon(maBoMon, tenBoMon, maKhoa, moTa) "
                    + " values ('" + id + "','" + name + "','" + maKhoa + "','" + moTa +"');" );
            resizeMemoryList();
            queryCursor();
            setListValue();
            Toast.makeText(getApplicationContext(), "Thêm mới bộ môn thành công", Toast.LENGTH_LONG).show();
        }
        else if ((requestCode == 101 ) && (resultCode == 0)){
            queryCursor();
            setListValue();
        }
        else if ((requestCode == 102 ) && (resultCode == 0)){
            queryCursor();
            setListValue();
        }
        else if ((requestCode == 102 ) && (resultCode == 1)){
            String id = myResults.getString("id");
            String name = myResults.getString("name");
            String maKhoa = myResults.getString("maKhoa");
            String moTa = myResults.getString("moTa");

            db.execSQL("update BoMon set tenBoMon = " + "'" + name + "'" + ", maKhoa = " + "'"
                    + maKhoa + "'" + ", moTa = " + "'" + moTa
                    + "'" +" where maBoMon = '" + id + "'");

            resizeMemoryList();
            queryCursor();
            setListValue();
            Toast.makeText(getApplicationContext(), "Sửa thông tin bộ môn thành công", Toast.LENGTH_LONG).show();
        }
        else if ((requestCode == 102 ) && (resultCode == 2)){
            String id = myResults.getString("id");
            db.execSQL("delete from BoMon where maBoMon = '" + id + "'");
            resizeMemoryList();
            queryCursor();
            setListValue();
            Toast.makeText(getApplicationContext(), "Xóa bộ môn thành công", Toast.LENGTH_LONG).show();
        }
    }

}
