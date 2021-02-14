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
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.sql_project.Grades.TABLE_GRADES;
import static com.example.sql_project.Students.TABLE_STUDENTS;

public class SortStudents extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    Spinner filter1, filter2;
    TextView viewInfo;
    ArrayAdapter<String> adp1, adp2;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> classes = new ArrayList<>();
    ArrayList<String> fillIn = new ArrayList<>();
    String[] arrFilter = {"filter", "All the grades of one student", "grades in one subject", "all students grades in one subject /up"};
    String nameLoc;
    int row;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_students);

        filter1 = (Spinner) findViewById(R.id.filter1);
        filter2 = (Spinner) findViewById(R.id.filter2);
        viewInfo = (TextView) findViewById(R.id.viewInfo);

        hlp = new HelperDB(this);
        db = hlp.getReadableDatabase();

        filter1.setOnItemSelectedListener(this);
        filter2.setOnItemSelectedListener(this);

        adp1 = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, arrFilter);
        filter1.setAdapter(adp1);

        filter2.setAdapter(null);

        String[] columns = {Students.NAME};

        crsr = db.query(TABLE_STUDENTS, columns, null, null, null, null, null);
        int col1 = crsr.getColumnIndex(Students.NAME);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String name = crsr.getString(col1);
            String tmp = name;
            names.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();

        db = hlp.getReadableDatabase();
        String[] columns1 = {Grades.CLASS};

        crsr = db.query(TABLE_GRADES, columns1, null, null, null, null, null);
        col1 = crsr.getColumnIndex(Grades.CLASS);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String subj = crsr.getString(col1);
            String tmp = subj;
            if (!classes.contains(tmp))
                classes.add(tmp);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String st = item.getTitle().toString();
        if (st.equals("Grades Insertion")) {
            Intent in = new Intent(this, GradesSubmission.class);
            startActivity(in);
        }
        if (st.equals("View Students")) {
            Intent in = new Intent(this, ViewStudents.class);
            startActivity(in);
        }
        if (st.equals("Credits")) {
            Intent in = new Intent(this, Credits.class);
            startActivity(in);
        }
        if (st.equals("Sort Students")) {
            return false;
        }
        if (st.equals("Student Insertion")) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Spinner s1 = (Spinner) parent;
        Spinner s2 = (Spinner) parent;
        db = hlp.getReadableDatabase();
        if (s1.getId() == filter1.getId()) {
            row = pos;
            if (pos == 0) {
                filter2.setAdapter(null);
                viewInfo.setText("");
            }
            else if (pos == 1) {
                adp2 = new ArrayAdapter<String>(this,
                        R.layout.support_simple_spinner_dropdown_item, names);
                filter2.setAdapter(adp2);
                viewInfo.setText("");
            }
            else if (pos == 2 || pos == 3) {
                adp2 = new ArrayAdapter<String>(this,
                        R.layout.support_simple_spinner_dropdown_item, classes);
                filter2.setAdapter(adp2);
                viewInfo.setText("");
            }
        } else if (s2.getId() == filter2.getId()) {
            if (row == 1) {
                viewInfo.setText("");
                nameLoc = names.get(pos);
                String[] columns = {Grades.CLASS, Grades.GRADE};
                String selection = Grades.KEY_ID + "=?";
                String[] selectionArgs = {String.valueOf(pos)};

                crsr = db.query(TABLE_GRADES, columns, selection, selectionArgs, null, null, null);
                int col1 = crsr.getColumnIndex(Grades.CLASS);
                int col2 = crsr.getColumnIndex(Grades.GRADE);
                crsr.moveToFirst();
                while (!crsr.isAfterLast()) {
                    String subj = crsr.getString(col1);
                    int grade = crsr.getInt(col2);
                    viewInfo.append(subj + " - " + grade + "\n");
                    crsr.moveToNext();
                }
                crsr.close();
                db.close();
            }

            else if (row == 2) {
                viewInfo.setText("");
                String[] columns = {Grades.KEY_ID, Grades.CLASS, Grades.GRADE};
                String selection= Grades.CLASS+"=?";
                String[] selectionArgs= {classes.get(pos)};
                String orderBy = Grades.KEY_ID;

                crsr = db.query(TABLE_GRADES, columns, selection, selectionArgs, null, null, orderBy);
                int col1 = crsr.getColumnIndex(Grades.CLASS);
                int col2 = crsr.getColumnIndex(Grades.GRADE);
                int col3 = crsr.getColumnIndex(Grades.KEY_ID);
                crsr.moveToFirst();
                while (!crsr.isAfterLast()) {
                    String subj = crsr.getString(col1);
                    int grade = crsr.getInt(col2);
                    nameLoc = names.get(crsr.getInt(col3));
                    viewInfo.append(nameLoc + " - " + subj + " - " + grade + "\n");
                    crsr.moveToNext();
                }
                crsr.close();
                db.close();
            }

            else if (row == 3) {
                viewInfo.setText("");
                String[] columns = {Grades.KEY_ID, Grades.CLASS, Grades.GRADE};
                String selection= Grades.CLASS+"=?";
                String[] selectionArgs= {classes.get(pos)};
                String orderBy = Grades.GRADE;

                crsr = db.query(TABLE_GRADES, columns, selection, selectionArgs, null, null, orderBy);
                int col1 = crsr.getColumnIndex(Grades.CLASS);
                int col2 = crsr.getColumnIndex(Grades.GRADE);
                int col3 = crsr.getColumnIndex(Grades.KEY_ID);
                crsr.moveToFirst();
                while (!crsr.isAfterLast()) {
                    String subj = crsr.getString(col1);
                    int grade = crsr.getInt(col2);
                    nameLoc = names.get(crsr.getInt(col3));
                    viewInfo.append(nameLoc + " - " + subj + " - " + grade + "\n");
                    crsr.moveToNext();
                }
                crsr.close();
                db.close();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        viewInfo.setText("");

    }
}
