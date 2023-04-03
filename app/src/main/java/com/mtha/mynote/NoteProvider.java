package com.mtha.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Lop thuc hien cac thao tac toi csdl
 */
public class NoteProvider {
    private MyDbHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public NoteProvider(Context context){
        this.context = context;
    }

    /**
     * Mo ket noi toi csdl
     * lay ra doi tuong sqlitedatbase de thuc hien doc/ghi
     * @return
     */
    public NoteProvider open() throws SQLException {
        dbHelper = new MyDbHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * dong ket noi toi csdl
     */
    public void close(){
        dbHelper.close();
    }

    /**
     * them moi note vao db
     * @param note
     */
    public void insNote(Note note){
        ContentValues values = new ContentValues();
        values.put(dbHelper.NOTE_TITLE, note.getNoteTitle());
        values.put(dbHelper.NOTE_CONTENT, note.getNoteContent());
        values.put(dbHelper.DATE_CREATE, note.getDateCreate());
        //thuc hien insert vao db
        database.insert(dbHelper.TB_NOTE,null,values);
    }

    /**
     *
     * @param id: cap nhat theo id
     * @param note: du lieu can cap nhat
     * @return so ban ghi duoc cap nhat
     */
    public int updNote(int id, Note note){
        ContentValues values = new ContentValues();
        values.put(dbHelper.NOTE_TITLE, note.getNoteTitle());
        values.put(dbHelper.NOTE_CONTENT, note.getNoteContent());
        values.put(dbHelper.DATE_CREATE, note.getDateCreate());
        int result = database.update(dbHelper.TB_NOTE,values,
                dbHelper.NOTE_ID+"= "+id,null);
        return result;
    }

    /**
     *
     * @param id: xoa ban ghi theo id
     * @return so ban ghi duoc xoa
     */
    public int delNote(int id){
        int result = database.delete(dbHelper.TB_NOTE,
                dbHelper.NOTE_ID +" = " + id, null);
        return result;
    }

    public ArrayList<Note> getAllNote(){
        ArrayList<Note> lsNotes= new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + dbHelper.TB_NOTE,null);
        if(cursor.getCount()>0){
            //co du lieu trong database
            while (cursor.moveToNext()){
                Note note = new Note(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                //them note vao ds
                lsNotes.add(note);
            }
        }
        cursor.close();
        return lsNotes;
    }
}
