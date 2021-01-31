package com.example.sql_project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.sql_project.Grades.TABLE_GRADES;

public class GradesSubmission extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    Spinner quart,nameId;
    ArrayList<String> names = new ArrayList<>();
    EditText classN,gradeN;
    ArrayAdapter<String> adp1,adp2;
    String [] qArray={"","1","2","3","4"};
    int S_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_submission);

        quart=(Spinner)findViewById(R.id.quart);
        nameId=(Spinner)findViewById(R.id.nameId);
        classN=(EditText)findViewById(R.id.classN);
        gradeN=(EditText)findViewById(R.id.gradeN);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();

        quart.setOnItemSelectedListener(this);
        nameId.setOnItemSelectedListener(this);

        String[] columns = {Students.NAME};

        crsr = db.query(TABLE_GRADES, columns, null, null, null, null, null);
        int col1 = crsr.getColumnIndex(Students.NAME);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()){
            String name = crsr.getString(col1);
            String tmp =name;
            names.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();




        adp1 = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,names);
        adp2 = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,qArray);
        nameId.setAdapter(adp1);
        quart.setAdapter(adp2);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void Submit(View view) {
        if(classN.getText().toString().equals("")){
            Toast.makeText(this,"Class Name missing!",Toast.LENGTH_SHORT).show();
        }
        else{


        }
    }
}