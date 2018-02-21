package com.example.kukilemo.managementourschedule;

import java.time.Year;

import io.realm.RealmObject;

/**
 * Created by kukilemo on 18/02/07.
 */

public class MyCalendar extends RealmObject{
    private int date;
    private int month;
    private int year;

    public MyCalendar(){

    }

    public MyCalendar(int date, int month, int year){
        this.date=date;
        this.month=month;
        this.year= year;

    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
