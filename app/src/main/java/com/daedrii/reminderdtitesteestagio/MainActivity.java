package com.daedrii.reminderdtitesteestagio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
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

    private ReminderAdapter reminderAdapter;
    private ReminderDataManager dataManager;

    private TextInputEditText txtReminder, txtDate;
    private MaterialDatePicker<Long> materialDatePicker;
    private MaterialButton btnCreate;
    private ExpandableListView dateListView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!materialDatePicker.isAdded())
                        materialDatePicker.show(getSupportFragmentManager(), "tag");
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Esconde o teclado do txtReminder quando clica no botao
                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(txtReminder.getWindowToken(), 0);

                try{
                    String textReminder = txtReminder.getText().toString();
                    String textDate = txtDate.getText().toString();

                    dataManager.addList(new Reminder(textReminder, textDate));

                    reminderAdapter.setData(dataManager.getReminders());

                    reminderAdapter.updateData();
                }catch(EmptyFieldException e){
                    Log.w("EmptyFieldException", e.getMessage());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }catch (InvalidDateException e){
                    Log.w("InvalidDateException", e.getMessage());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Carrega a lista de lembretes predefinidos
        dataManager.loadList();

        // Atualiza os dados do adapter apenas se houver dados no ReminderDataManager
        if (dataManager != null && !dataManager.getReminders().isEmpty()) {
            reminderAdapter.setData(dataManager.getReminders());
            reminderAdapter.updateData();
            reminderAdapter.notifyDataSetChanged(); // Notifica o adapter para atualizar a exibição
        }



    }

    public void initComponents(){
        txtReminder = findViewById(R.id.txt_reminder);
        txtDate = findViewById(R.id.txt_date);
        btnCreate = findViewById(R.id.btn_create);
        dateListView = findViewById(R.id.reminder_list);
        dataManager = new ReminderDataManager();

        reminderAdapter = new ReminderAdapter(MainActivity.this, dataManager);
        dateListView.setAdapter(reminderAdapter); // A lista agora se adapta ao ReminderAdapter

        //Criação do DatePicker
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
}