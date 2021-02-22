package com.example.sql_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    HelperDB hlp;
    EditText student,s_Phone,address, h_Phone,p1,p1_Phone,p2, p2_Phone;
    int active=1;

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
            Intent in = new Intent(this, ViewStudents.class);
            startActivity(in);
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
            return false;
        }
        return true;
    }

    /**
     * Submit. - Student info insertion into the database
     *
     * @param view the view
     */
    public void Submit(View view) {
        if(student.getText().toString().equals("") || s_Phone.getText().toString().equals("")|| p1_Phone.getText().toString().equals("")|| address.getText().toString().equals(""))
            Toast.makeText(this,"Name or Phone number are missing",Toast.LENGTH_SHORT).show();
        else{

            ContentValues cv = new ContentValues();

            cv.put(Students.NAME,student.getText().toString());
            cv.put(Students.PHONE_NUMBER,Integer.parseInt(s_Phone.getText().toString()));
            cv.put(Students.ADDRESS,address.getText().toString());
            if(!h_Phone.getText().toString().equals(""))
                cv.put(Students.HOME_PHONE_NUMBER,Integer.parseInt(h_Phone.getText().toString()));
            if(!p1.getText().toString().equals(""))
                cv.put(Students.PARENT1_NAME,p1.getText().toString());
            if(!p2.getText().toString().equals(""))
                cv.put(Students.PARENT2_NAME,p2.getText().toString());
            cv.put(Students.P1_NUM,Integer.parseInt(p1_Phone.getText().toString()));
            if(!p2_Phone.getText().toString().equals(""))
                cv.put(Students.P2_NUM,Integer.parseInt(p2_Phone.getText().toString()));
            cv.put(Students.ACTIVE,active);

            db = hlp.getWritableDatabase();

            db.insert(Students.TABLE_STUDENTS, null, cv);

            db.close();

            student.setText("");
            s_Phone.setText("");
            address.setText("");
            h_Phone.setText("");
            p1.setText("");
            p2.setText("");
            p1_Phone.setText("");
            p2_Phone.setText("");
        }

    }
}