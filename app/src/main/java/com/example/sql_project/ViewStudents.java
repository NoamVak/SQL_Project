package com.example.sql_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ViewStudents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
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
}
