package com.daedrii.reminderdtitesteestagio;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ReminderAdapter extends BaseExpandableListAdapter {

    private static ArrayList<ReminderGroup> reminderGroups;

    private static HashMap<String, ArrayList<Reminder>> dateList;
    private static ArrayList<Reminder> reminders;
    private Context context;

    public ReminderAdapter(Context context){
        this.context = context;
        reminders = new ArrayList<>();
        dateList = new HashMap<String, ArrayList<Reminder>>();

        loadList();

    }

    public void loadList(){

        reminderGroups = new ArrayList<>();

        addList(new Reminder("Colocar ração", "25/05/2023"));
        addList(new Reminder("Limpar caixa de areia", "24/05/2023"));
        addList(new Reminder("Limpar a janela", "13/05/2023"));

        dateList = new HashMap<>();
        for(ReminderGroup group: reminderGroups){
            String date = group.getDate();
            ArrayList<Reminder> reminders = group.getReminders();
            dateList.put(date, reminders);
        }

    }

    public static void addList(Reminder newReminder){
        String date = newReminder.getDate();

        ReminderGroup reminderGroup = null;
        for(ReminderGroup group: reminderGroups){
            if(group.getDate().equals(date)){
                reminderGroup = group;
                break;
            }
        }

        if (reminderGroup == null) {
            reminderGroup = new ReminderGroup(date);
            reminderGroups.add(reminderGroup);
        }

        if(reminderGroup.getReminders() == null){
            reminderGroup.setReminders(new ArrayList<>());
        }

        reminderGroup.getReminders().add(newReminder);

        //Atualiza o dateList com o novo grupo
        dateList.put(date, reminderGroup.getReminders());
    }


    @Override
    public int getGroupCount() {
        return this.dateList.keySet().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String date = (String) getGroup(groupPosition);
        ArrayList<Reminder> reminders = dateList.get(date);
        return (reminders != null) ? reminders.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {

        ArrayList<String> dates = new ArrayList<>(this.dateList.keySet());
        Collections.sort(dates, new Comparator<String>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            @Override
            public int compare(String date1, String date2) {
                try {
                    Date d1 = dateFormat.parse(date1);
                    Date d2 = dateFormat.parse(date2);
                    return d1.compareTo(d2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        return dates.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String date = (String) getGroup(groupPosition);
        ArrayList<Reminder> reminders = dateList.get(date);
        return (reminders != null && reminders.size() > childPosition) ? reminders.get(childPosition) : null;

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.date_list, null);

        }
        MaterialTextView listTitleTextView = convertView.findViewById(R.id.list_title);
        listTitleTextView.setText(listTitle);

        //Verificação da lista de reminders para uma determinada data estar vazia
        ArrayList<Reminder> reminders = dateList.get(listTitle);
        if(reminders.isEmpty() || reminders == null){
            listTitleTextView.setVisibility(View.GONE);
        }else{
            listTitleTextView.setVisibility(View.VISIBLE);

        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Reminder actualReminder = (Reminder) getChild(groupPosition, childPosition);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_reminder, null);
        }

        MaterialTextView lblReminder = convertView.findViewById(R.id.lbl_reminder);
        MaterialTextView lblDate = convertView.findViewById(R.id.lbl_date);
        ImageButton lblDelete = convertView.findViewById(R.id.btn_delete);

        lblReminder.setText(actualReminder.getName());
        lblDate.setText(actualReminder.getDate());

        //Obtem a posição do Reminder em relação à lista completa, considerando a ordenação
        int absolutePosition = getAbsoluteChildPosition(groupPosition, childPosition);

        lblDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtém a posição real do Reminder na lsita sem a ordenação
                int realPosition = getRealChildPosition(absolutePosition);

                ArrayList<Reminder> reminders = dateList.get(getGroup(groupPosition));
                reminders.remove(realPosition);
                if(reminders.isEmpty())
                    reminderGroups.remove(getGroup(groupPosition));

                notifyDataSetChanged();

            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private int getAbsoluteChildPosition(int groupPosition, int childPosition) {
        int absolutePosition = 0;

        for(int i = 0; i < groupPosition; i++){
            ArrayList<Reminder> reminders = dateList.get(dateList.keySet().toArray()[i]);
            absolutePosition += reminders.size();
        }

        absolutePosition += childPosition;
        return absolutePosition;
    }

    private int getRealChildPosition(int absolutePosition){
        int groupPosition = 0;
        int childPosition = absolutePosition;
        while(groupPosition < dateList.keySet().size() && childPosition >= dateList.get(dateList.keySet().toArray()[groupPosition]).size()){
            childPosition -= dateList.get(dateList.keySet().toArray()[groupPosition]).size();
            groupPosition++;
        }
        return childPosition;
    }
}
