package com.mtha.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String TAG="SQLite";
    private static final String DB_NAME="dbNote";
    private static final  int DB_VERSION=1;
    public static final String TB_NOTE = "tbleNote";
    public static final String NOTE_ID="noteId";
    public static final String NOTE_TITLE ="noteTitle";
    public static final String NOTE_CONTENT ="noteContent";
    public static final String DATE_CREATE="dateCreate";

    public MyDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //dinh nghia mot cau lenh tao bang
        String sql =" CREATE TABLE " + TB_NOTE +" ( " + NOTE_ID +" INTEGER PRIMARY KEY, " +
                NOTE_TITLE +" TEXT, " + NOTE_CONTENT +" TEXT,"+ DATE_CREATE +" TEXT ) ";
        //cau lenh thuc thi
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NOTE);
        onCreate(sqLiteDatabase);
    }

    //them mot ghi chu
    public void insNote(@NonNull Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, note.getNoteTitle());
        values.put(NOTE_CONTENT, note.getNoteContent());
        db.insert(TB_NOTE, null, values);
        db.close();
    }
}
