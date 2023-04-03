package com.mtha.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class Add_note extends AppCompatActivity {
    EditText etTitle, etContent;
    TextView txtDate;

    Button btnLuu, btnDate;
    NoteProvider noteProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getViews();
        noteProvider = new NoteProvider(Add_note.this);
        noteProvider.open();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //thuc hien insert du lieu
                noteProvider.insNote(getNoteInfo());
                //dong activity nay lai, tro ve MainActivity
                finish();
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog(){
        //lay ra ngay thang hien tai
        final Calendar calendar = Calendar.getInstance();
        int _year = calendar.get(Calendar.YEAR);
        int _month = calendar.get(Calendar.MONTH);
        int _day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(Add_note.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //select date va hien len txtDate
                txtDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },_year,_month,_day);
        dialog.show();
    }
    private void getViews(){
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        txtDate = findViewById(R.id.txtDate);
        btnLuu = findViewById(R.id.btnLuu);
        btnDate = findViewById(R.id.btnDate);
    }

    public Note getNoteInfo(){
        return new Note(etTitle.getText().toString(), etContent.getText().toString(),
                txtDate.getText().toString());
    }

    @Override
    protected void onDestroy() {
        noteProvider.close();
        super.onDestroy();
    }
}