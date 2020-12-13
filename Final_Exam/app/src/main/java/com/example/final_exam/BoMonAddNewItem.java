package com.example.final_exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BoMonAddNewItem extends Activity {
    EditText id_text, name_text, maKhoa_text, moTa_text;
    Button btnAdd;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bomon_add_new_item);

        final Intent myLocalIntent = getIntent();
        final Bundle myBundle = myLocalIntent.getExtras();

        btnAdd = (Button) findViewById(R.id.add);
        btnCancel = (Button) findViewById(R.id.cancel);
        id_text = (EditText) findViewById(R.id.txtId);
        name_text = (EditText) findViewById(R.id.txtName);
        maKhoa_text = (EditText) findViewById(R.id.txtMaKhoa);
        moTa_text = (EditText) findViewById(R.id.txtMoTa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id, name, maKhoa, moTa;
                id = "";
                name="";
                maKhoa="";
                moTa="";
                id = id_text.getText().toString();
                name = name_text.getText().toString();
                maKhoa = maKhoa_text.getText().toString();
                moTa = moTa_text.getText().toString();
                if (id.equals("")) {
                    System.out.print("id is: "+ id);
                    Toast.makeText(getApplicationContext(), "Mã bộ môn không được bỏ trống", Toast.LENGTH_LONG).show();
                } else {
                    myBundle.putString("id", id);
                    myBundle.putString("name", name);
                    myBundle.putString("maKhoa", maKhoa);
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
}
