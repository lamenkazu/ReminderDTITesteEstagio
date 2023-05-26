package com.daedrii.reminderdtitesteestagio;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ReminderDataManager {

    private ArrayList<ReminderGroup> reminderGroups;
    private ArrayList<Reminder> reminders;
    private HashMap<String, ArrayList<Reminder>> dateList;

    //Construtor das estruturas de dados
    public ReminderDataManager(){
        this.reminderGroups = new ArrayList<>(); //Lista de lembretes de uma data específica -  Datas 1-N Lembretes
        this.reminders = new ArrayList<>(); //Lista de Lembretes
        this.dateList = new HashMap<>(); // Lista de datas
    }

    //Seed para uma nova Lista para testes de implementação
    public void loadList()  {
        this.reminders.clear();
        this.reminderGroups.clear();
        this.dateList.clear();


        Date reference = new Date();

        try {
            addList(new Reminder("Colocar ração", "29/05/2023" ));
            addList(new Reminder("Limpar caixa de areia", "30/05/2023"));
            addList(new Reminder("Aniversario da Taís", "07/06/2023"));
        } catch (EmptyFieldException e) {
            Log.w("EmptyFieldException", e.getMessage());
        }catch(InvalidDateException e){
            Log.w("InvalidDateException", e.getMessage());
        }

    }

    //Cria um novo dado nas 3 estruturas de dados
    public void addList(Reminder newReminder) throws EmptyFieldException, InvalidDateException{

        if(newReminder.getDate().equals("") || newReminder.getName().equals("")){
            throw new EmptyFieldException("Os campos não podem estar vazios para a inserção.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date date = sdf.parse(newReminder.getDate());
            long timestamp = date.getTime();

            if(new Date(timestamp).before(new Date())){
                throw new InvalidDateException("A data informada precisa estar no futuro para ser inserida");
            }

        }catch (ParseException e){
            Log.d("SimpleDateFormat", e.getMessage());
        }


        this.reminders.add(newReminder); //Adciona um novo Reminder à lista de lembretes.

        //ReminderGroup agrupa lembretes de uma data específica (date)
        String date = newReminder.getDate();
        ReminderGroup group = findReminderGroupByDate(date);
        if (group == null) {
            group = new ReminderGroup(date, this.reminders);
            this.reminderGroups.add(group);
        }

        //Adciona uma nova data na lista de Dates, sem repetir uma data ja existente;
        ArrayList<Reminder> reminders = dateList.get(date);
        if (reminders == null) {
            reminders = new ArrayList<>();
            this.dateList.put(date, reminders);
        }

    }

    //Encontra um Agrupamento de Lembretes a partir de uma data, retorna nulo caso não encontra.
    //TODO (Tratar nulo - ou criar nova ThrowExcept)
    private ReminderGroup findReminderGroupByDate(String date) {
        for (ReminderGroup group : this.reminderGroups) {
            if (group.getDate().equals(date)) {
                return group;
            }
        }
        return null;
    }

    public ArrayList<ReminderGroup> getReminderGroups() {
        return this.reminderGroups;
    }

    public ArrayList<Reminder> getReminders() {
        return this.reminders;
    }

    public HashMap<String, ArrayList<Reminder>> getDateList() {
        return this.dateList;
    }
}
