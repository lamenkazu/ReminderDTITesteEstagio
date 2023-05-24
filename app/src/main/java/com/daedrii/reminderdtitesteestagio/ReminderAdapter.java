package com.daedrii.reminderdtitesteestagio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ReminderAdapter extends BaseAdapter {

    private static ArrayList<Reminder> reminders;
    private Context context;

    public ReminderAdapter(Context context){
        this.context = context;
        reminders = new ArrayList<>();

        loadList();

    }

    public void loadList(){
        addList(new Reminder("Limpar caixa de areia", "25/05/2023"));
        addList(new Reminder("Colocar ração", "25/05/2023"));
    }

    public static void addList(Reminder newReminder){
        reminders.add(newReminder);
    }



    @Override
    public int getCount() {
        return this.reminders.size();
    }

    @Override
    public Object getItem(int position) {
        return this.reminders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Reminder actualReminder = this.reminders.get(position);


        //Recupera o view do adapter que vai customizar a lista
        View newView = LayoutInflater.from(this.context).inflate(R.layout.item_list_reminder, parent, false);

        MaterialTextView lblReminder = newView.findViewById(R.id.lbl_reminder);
        MaterialTextView lblDate = newView.findViewById(R.id.lbl_date);


        lblReminder.setText(actualReminder.getName());
        lblDate.setText(actualReminder.getDate());


        return newView;
    }
}
