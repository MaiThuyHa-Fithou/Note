package com.mtha.mynote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    Context context;
    int layoutID;
    List<Note> lsNotes;
    NoteProvider noteProvider;//thuc hien cac phuong thuc thao tac toi db

    public NoteAdapter(@NonNull Context context, int resource, @NonNull List<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutID = resource;
        lsNotes = objects;
        noteProvider = new NoteProvider(context);
        noteProvider.open();//mo ket noi
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutID, null);
        //lay ra doi tuong Note tai vi tri position
        Note note = lsNotes.get(position);
        //lay ra duoc cac doi tuong view control tren layout resource
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtDate = view.findViewById(R.id.txtDateCreate);
        ImageButton btnEdit = view.findViewById(R.id.btnEdit);
        ImageButton btnDel = view.findViewById(R.id.btnDel);
        //databind du lieu cac doi tuong view
        txtTitle.setText(note.getNoteTitle());
        txtDate.setText(note.getDateCreate());
        //xu ly su kien khi click len cac imagebutton
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xu ly su kien edit o day
                updateNote(note);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xu ly su kien del o day
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                builder.setTitle("Ban co chac chan xoa "+ note.getNoteTitle()+ " hay khong? ");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //xu ly nut YES tren dialog o day
                        //thuc hien xoa trong database
                        if(noteProvider.delNote(note.getNoteId())>0){
                            Toast.makeText(context, "Xoa thanh cong!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xoa that bai!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //refresh data sau khi xoa
                        refreshData();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //khong lam gi ca, dong dialog lai
                        dialog.dismiss();
                    }
                });
                //tao dialog va hien thi
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }
    public void refreshData(){
        lsNotes.clear();//xoa du lieu cu
        lsNotes.addAll(noteProvider.getAllNote());
        notifyDataSetChanged();//goi phuong thuc de reload lai adapter
    }

    private void updateNote(Note note){
        //load dialog activity_add_note.xml
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        //load layout can hien thi len view
        View view = inflater.inflate(R.layout.activity_add_note,null);
        //set view hien thi len alertdialog
        builder.setView(view);
        //get view control va databind du lieu truoc khi thuc hien cap nhat
        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etContent = view.findViewById(R.id.etContent);
        TextView txtDate = view.findViewById(R.id.txtDate);
        etTitle.setText(note.getNoteTitle());
        etContent.setText(note.getNoteContent());
        txtDate.setText(note.getDateCreate());
        //goi hien thi dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        //xu ly su kien update
        view.findViewById(R.id.btnLuu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = note.getNoteId();
                //khoi tao doi tuong note can cap nhat
                String title = etTitle.getText().toString();
                String content =etContent.getText().toString();
                String date = note.getDateCreate();
                Note upNote = new Note(id, title,content,date);
                if(noteProvider.updNote(id,upNote)>0)
                    Toast.makeText(context, "Update success!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Update failed!", Toast.LENGTH_SHORT).show();
                //reload adapter
                refreshData();
                dialog.dismiss();//dong dialog
            }
        });
    }
}
