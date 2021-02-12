package com.example.sql_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.sql_project.Students.TABLE_STUDENTS;

public class ViewStudents extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    ArrayAdapter<String> adp;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> info= new ArrayList<>();
    ListView stuView;
    EditText student,s_Phone,address,h_Phone,p1,p1_Phone,p2,p2_Phone;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        stuView=(ListView)findViewById(R.id.stuView);
        student=(EditText)findViewById(R.id.student);
        s_Phone=(EditText)findViewById(R.id.s_Phone);
        address=(EditText)findViewById(R.id.address);
        h_Phone=(EditText)findViewById(R.id.h_Phone);
        p1=(EditText)findViewById(R.id.p1);
        p1_Phone=(EditText)findViewById(R.id.p1_Phone);
        p2=(EditText)findViewById(R.id.p2);
        p2_Phone=(EditText)findViewById(R.id.p2_Phone);

        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();

        stuView.setOnItemClickListener(this);

        String[] columns = {Students.NAME};

        crsr = db.query(TABLE_STUDENTS, columns, null, null, null, null, null);
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

        if(names==null)
            stuView.setAdapter(null);
        else{
            adp= new ArrayAdapter<String>(this,
                    R.layout.support_simple_spinner_dropdown_item, names);
            stuView.setAdapter(adp);
        }



    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        String st=item.getTitle().toString();
        if(st.equals("Grades Insertion")) {
            Intent in = new Intent(this, GradesSubmission.class);
            startActivity(in);
        }
        if(st.equals("View Students")) {
            return false;
        }
        if(st.equals("Credits")) {
            Intent in = new Intent(this, Credits.class);
            startActivity(in);
        }
        if(st.equals("Sort Students")) {
            Intent in = new Intent(this, SortStudents.class);
            startActivity(in);
        }
        if(st.equals("Student Insertion")) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos=position;

        db = hlp.getReadableDatabase();

        String[] columns = {Students.NAME,Students.PHONE_NUMBER,Students.ADDRESS,Students.HOME_PHONE_NUMBER,Students.PARENT1_NAME,Students.P1_NUM,Students.PARENT2_NAME,Students.P2_NUM};
        String selection=  Students.KEY_ID;
        String [] selectionArgs= {String.valueOf(pos)};

        crsr = db.query(TABLE_STUDENTS, columns, selection, selectionArgs, null, null, null);
        int col1 = crsr.getColumnIndex(Students.NAME);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()){
            String info = crsr.getString(col1);
            String tmp =info;
            names.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();

        adp= new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, info);
        stuView.setAdapter(adp);



    }

    public void update(View view) {



    }

    public void ret_nameList(View view) {
        pos=-1;
        adp= new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, names);
        stuView.setAdapter(adp);
    }
}
