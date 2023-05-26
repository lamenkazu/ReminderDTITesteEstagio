package com.daedrii.reminderdtitesteestagio;

import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;

//Classe que lida com is componentes do item reminder
public class ReminderViewHolder {

    private MaterialTextView lblReminder, lblDate;
    private ImageButton lblDelete;

    private ReminderAdapter adapter;
    private int groupPosition;
    private int childPosition;

    public ReminderViewHolder(View itemView, ReminderAdapter adapter, int groupPosition, int childPosition) {
        this.adapter = adapter;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;

        this.lblReminder = itemView.findViewById(R.id.lbl_reminder);
        this.lblDate = itemView.findViewById(R.id.lbl_date);
        this.lblDelete = itemView.findViewById(R.id.btn_delete);
    }

    public void bind(Reminder reminder) {
        lblReminder.setText(reminder.getName());
        lblDate.setText(reminder.getDate());

        //Obtem a posição do Reminder em relação à lista completa, considerando a ordenação
        int absolutePosition = getAbsoluteChildPosition(groupPosition, childPosition);

        lblDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = getRealChildPosition(absolutePosition);
                if (position != RecyclerView.NO_POSITION) {
                    ArrayList<Reminder> reminders = adapter.getReminderList(groupPosition);
                    reminders.remove(position); // Remove o lembrete apenas se a lista não estiver vazia
                    adapter.notifyDataSetChanged();

                    //TODO Chama o método de remoção do banco de dados
                }
            }
        });
    }



    private int getAbsoluteChildPosition(int groupPosition, int childPosition) {
        int absolutePosition = 0;

        HashMap<String, ArrayList<Reminder>> caughtDateList =  ReminderAdapter.getDateList();
        for(int i = 0; i < groupPosition; i++){
            ArrayList<Reminder> reminders = caughtDateList.get(caughtDateList.keySet().toArray()[i]);
            absolutePosition += reminders.size();
        }

        absolutePosition += childPosition;
        return absolutePosition;
    }

    private int getRealChildPosition(int absolutePosition){
        int groupPosition = 0;
        int childPosition = absolutePosition;
        HashMap<String, ArrayList<Reminder>> caughtDateList =  ReminderAdapter.getDateList();

        while(groupPosition < caughtDateList.keySet().size() &&
              childPosition >= caughtDateList.get(caughtDateList.keySet().toArray()[groupPosition]).size()){

            childPosition -= caughtDateList.get(caughtDateList.keySet().toArray()[groupPosition]).size();
            groupPosition++;
        }
        return childPosition;
    }

    public void updatePosition(int groupPosition, int childPosition) {
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }

}
