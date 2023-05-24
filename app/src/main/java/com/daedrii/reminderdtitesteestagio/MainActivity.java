package com.daedrii.reminderdtitesteestagio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextInputEditText txtReminder, txtDate;
    MaterialDatePicker<Long> materialDatePicker;
    MaterialButton btnCreate;
    ListView reminderListView;

    private void startComponents(){
        txtReminder = findViewById(R.id.txt_reminder);
        txtDate = findViewById(R.id.txt_date);
        btnCreate = findViewById(R.id.btn_create);
        reminderListView = findViewById(R.id.reminder_list);
        reminderListView.setAdapter(new ReminderAdapter(MainActivity.this));

        materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Choose date to remind")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {

                String data = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                txtDate.setText(data);

            }

        });

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startComponents();

        txtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!materialDatePicker.isAdded())
                    materialDatePicker.show(getSupportFragmentManager(), "tag");

                return false;
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Esconde o teclado
                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtReminder.getWindowToken(), 0);

                ReminderAdapter.addList(new Reminder(txtReminder.getText().toString(), txtDate.getText().toString()));
                ((BaseAdapter) reminderListView.getAdapter()).notifyDataSetChanged();
            }
        });
    }
}