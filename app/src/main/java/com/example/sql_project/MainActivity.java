package com.example.sql_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    HelperDB hlp;
    EditText student,s_Phone,address,h_Phone,p1,p1_Phone,p2,p2_Phone;
    String sStudent,sAddress,sP1,sP2;
    int iS_Phone,iH_Phone,iP1_Phone,iP2_Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student=(EditText)findViewById(R.id.student);
        s_Phone=(EditText)findViewById(R.id.s_Phone);
        address=(EditText)findViewById(R.id.address);
        h_Phone=(EditText)findViewById(R.id.h_Phone);
        p1=(EditText)findViewById(R.id.p1);
        p1_Phone=(EditText)findViewById(R.id.p1_Phone);
        p2=(EditText)findViewById(R.id.p2);
        p2_Phone=(EditText)findViewById(R.id.p2_Phone);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

    }

    public void Submit(View view) {
        if(student.getText().toString().equals(""))
            Toast.makeText(this,"Student Name missing!",Toast.LENGTH_SHORT).show();
        else{
            sStudent=student.getText().toString();
            sAddress=address.getText().toString();
            sP1=p1.getText().toString();
            sP2=p2.getText().toString();
            iS_Phone=Integer.parseInt(s_Phone.getText().toString());
            iH_Phone=Integer.parseInt(h_Phone.getText().toString());
            iP1_Phone=Integer.parseInt(p1_Phone.getText().toString());
            iP2_Phone=Integer.parseInt(p2_Phone.getText().toString());


            ContentValues cv = new ContentValues();

            cv.put(Students.NAME,sStudent);
            cv.put(Students.PHONE_NUMBER,iS_Phone);
            cv.put(Students.ADDRESS,sAddress);
            cv.put(Students.HOME_PHONE_NUMBER,iH_Phone);
            cv.put(Students.PARENT1_NAME,sP1);
            cv.put(Students.PARENT2_NAME,sP2);
            cv.put(Students.P1_NUM,iP1_Phone);
            cv.put(Students.P2_NUM,iP2_Phone);
            cv.put(Students.ACTIVE,1);

            db = hlp.getWritableDatabase();

            db.insert(Students.TABLE_STUDENTS, null, cv);

            db.close();
        }

    }
}