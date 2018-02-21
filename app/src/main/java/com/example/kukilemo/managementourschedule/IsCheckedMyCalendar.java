package com.example.kukilemo.managementourschedule;

import io.realm.RealmObject;

/**
 * Created by kukilemo on 18/02/03.
 */

public class IsCheckedMyCalendar extends RealmObject {

    private int Date;
    private MyCalendar myCalendar;
    private Boolean isChecked;


    public int getDate() {
        return Date;
    }

    public void setDate(int date) {
        this.Date = date;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public MyCalendar getMyCalendar() {
        return myCalendar;
    }

    public void setMyCalendar(MyCalendar myCalendar) {
        this.myCalendar = myCalendar;
    }
}
