package com.example.sql_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    HelperDB hlp;
    EditText student,s_Phone,address,h_Phone,p1,p1_Phone,p2,p2_Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

    }

    public void Submit(View view) {





        ContentValues cv = new ContentValues();



        db = hlp.getWritableDatabase();

        db.insert(Students.TABLE_STUDENTS, null, cv);

        db.close();
    }
}