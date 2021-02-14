package com.example.sql_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.sql_project.Grades.CLASS;
import static com.example.sql_project.Grades.GRADE;
import static com.example.sql_project.Grades.QUARTER_NUM;
import static com.example.sql_project.Grades.TABLE_GRADES;
import static com.example.sql_project.Students.ACTIVE;
import static com.example.sql_project.Students.ADDRESS;
import static com.example.sql_project.Students.HOME_PHONE_NUMBER;
import static com.example.sql_project.Students.KEY_ID;
import static com.example.sql_project.Students.NAME;
import static com.example.sql_project.Students.P1_NUM;
import static com.example.sql_project.Students.P2_NUM;
import static com.example.sql_project.Students.PARENT1_NAME;
import static com.example.sql_project.Students.PARENT2_NAME;
import static com.example.sql_project.Students.PHONE_NUMBER;
import static com.example.sql_project.Students.TABLE_STUDENTS;

public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbexam.db";
    private static final int DATABASE_VERSION = 3;
    String strCreate, strDelete;
    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        strCreate="CREATE TABLE "+TABLE_STUDENTS;
        strCreate+=" ("+KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+NAME+" TEXT,";
        strCreate+=" "+PHONE_NUMBER+" INTEGER,";
        strCreate+=" "+ADDRESS+" TEXT,";
        strCreate+=" "+HOME_PHONE_NUMBER+" INTEGER,";
        strCreate+=" "+PARENT1_NAME+" TEXT,";
        strCreate+=" "+P1_NUM+" INTEGER,";
        strCreate+=" "+PARENT2_NAME+" TEXT,";
        strCreate+=" "+P2_NUM+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_GRADES;
        strCreate+=" ("+Grades.KEY_ID+" INTEGER,";
        strCreate+=" "+CLASS+" TEXT,";
        strCreate+=" "+QUARTER_NUM+" INTEGER,";
        strCreate+=" "+GRADE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        strDelete="DROP TABLE IF EXISTS "+TABLE_STUDENTS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+TABLE_GRADES;
        db.execSQL(strDelete);

        onCreate(db);
    }

}
