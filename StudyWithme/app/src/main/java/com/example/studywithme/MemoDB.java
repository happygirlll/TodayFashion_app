package com.example.studywithme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class MemoDB extends SQLiteOpenHelper {
    public MemoDB(@Nullable Context context) {
        super(context, "memo.db", null, 2);
    }
    //테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table memo(_id integer primary key autoincrement,content text,wdate text, user_email text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE memo ADD COLUMN user_email TEXT");
        }
    }
}