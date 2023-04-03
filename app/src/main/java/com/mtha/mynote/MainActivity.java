package com.mtha.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvNote;
    NoteProvider noteProvider;
    NoteAdapter noteAdapter;
    List<Note> lsNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvNote = findViewById(R.id.lvNote);
        noteProvider = new NoteProvider(MainActivity.this);
        noteProvider.open();
        lsNotes = noteProvider.getAllNote();
        noteAdapter = new NoteAdapter(MainActivity.this,R.layout.layout_note,lsNotes);
        //set adapter vao listview
        lvNote.setAdapter(noteAdapter);
    }

    public void onInsNote(View view) {
        //add new note
        Intent intentIns = new Intent(MainActivity.this, Add_note.class);
        startActivity(intentIns);
    }

    @Override
    protected void onDestroy() {
        noteProvider.close();//dong csdl
        super.onDestroy();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        noteAdapter.refreshData();
    }
}