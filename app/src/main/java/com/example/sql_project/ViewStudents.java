package com.example.sql_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import static com.example.sql_project.Students.KEY_ID;
import static com.example.sql_project.Students.TABLE_STUDENTS;

public class ViewStudents extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    ArrayAdapter<String> adp;
    ArrayList<String> names = new ArrayList<>();
    String name, addr, p1_name,p2_name;
    int pos,phone,h_phoneNum,p1_num, p2_num, active;
    ListView stuView;
    EditText student,s_Phone,address,h_Phone,p1, p1_Phone, p2,p2_Phone;
    ToggleButton state;


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
        state=(ToggleButton)findViewById(R.id.state);

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
        pos=position+1;

        db = hlp.getReadableDatabase();

        String[] columns = {Students.NAME,Students.PHONE_NUMBER,Students.ADDRESS,Students.HOME_PHONE_NUMBER,Students.PARENT1_NAME,Students.P1_NUM,Students.PARENT2_NAME,Students.P2_NUM,Students.ACTIVE};
        String selection=  Students.KEY_ID+"=?";
        String [] selectionArgs= {String.valueOf(pos)};

        crsr = db.query(TABLE_STUDENTS, columns, selection, selectionArgs, null, null, null);
        int col1 = crsr.getColumnIndex(Students.NAME);
        int col2= crsr.getColumnIndex(Students.PHONE_NUMBER);
        int col3= crsr.getColumnIndex(Students.ADDRESS);
        int col4= crsr.getColumnIndex(Students.HOME_PHONE_NUMBER);
        int col5= crsr.getColumnIndex(Students.PARENT1_NAME);
        int col6= crsr.getColumnIndex(Students.P1_NUM);
        int col7= crsr.getColumnIndex(Students.PARENT2_NAME);
        int col8= crsr.getColumnIndex(Students.P2_NUM);
        int col9= crsr.getColumnIndex(Students.ACTIVE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()){
            name= crsr.getString(col1);
            phone= crsr.getInt(col2);
            addr= crsr.getString(col3);
            h_phoneNum= crsr.getInt(col4);
            p1_name= crsr.getString(col5);
            p1_num= crsr.getInt(col6);
            p2_name= crsr.getString(col7);
            p2_num= crsr.getInt(col8);
            active= crsr.getInt(col9);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();

        student.setText(name);
        s_Phone.setText(String.valueOf(phone));
        address.setText(addr);
        h_Phone.setText(String.valueOf(h_phoneNum));
        p1.setText(p1_name);
        p1_Phone.setText(String.valueOf(p1_num));
        p2.setText(p2_name);
        p2_Phone.setText(String.valueOf(p2_num));
        if(active==1)
            state.setChecked(true);
        else state.setChecked(false);
    }

    /**
     * Update. - Updates student's info that's in the database
     *
     * @param view the view
     */
    public void update(View view) {

        if(pos<1){
            Toast.makeText(this,"HAHAHA YOU FOOL YOU THOUGHT YOU CAN TRICK ME",Toast.LENGTH_LONG).show();
        }
        else{
            ContentValues cv= new ContentValues();
            db= hlp.getWritableDatabase();
            db.delete(TABLE_STUDENTS, KEY_ID+"=?", new String[]{String.valueOf(pos)});

            if(student.getText().toString().equals("") || s_Phone.getText().toString().equals(""))
                Toast.makeText(this,"Name or Phone number are missing",Toast.LENGTH_SHORT).show();
            else{
                cv.put(Students.KEY_ID,pos);
                cv.put(Students.NAME,student.getText().toString());
                cv.put(Students.PHONE_NUMBER,Integer.parseInt(s_Phone.getText().toString()));
                cv.put(Students.ADDRESS,address.getText().toString());
                cv.put(Students.HOME_PHONE_NUMBER,Integer.parseInt(h_Phone.getText().toString()));
                cv.put(Students.PARENT1_NAME,p1.getText().toString());
                cv.put(Students.PARENT2_NAME,p2.getText().toString());
                cv.put(Students.P1_NUM,Integer.parseInt(p1_Phone.getText().toString()));
                cv.put(Students.P2_NUM,Integer.parseInt(p2_Phone.getText().toString()));
                if(state.isChecked())
                    cv.put(Students.ACTIVE,1);
                else cv.put(Students.ACTIVE,0);
                db.insert(Students.TABLE_STUDENTS, null, cv);

                db.close();

                names.set(pos-1,student.getText().toString());
                adp.notifyDataSetChanged();
            }
        }


    }

}
