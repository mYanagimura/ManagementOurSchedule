package com.example.kukilemo.managementourschedule;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kukilemo on 18/01/28.
 */


public class Schedule extends RealmObject {
    @PrimaryKey
    private long id;
    private String title;
    private RealmList<Belonging> belongingList;
    private String place;
    private String time;
    private RealmList<IsCheckedMyCalendar> isCheckedMyCalendarList;
    private RealmList<IsCheckedMyCalendar> inCheckBoxIsCheckedMyCalendar;

    public RealmList<IsCheckedMyCalendar> getInCheckBoxIsCheckedMyCalendar() {
        return inCheckBoxIsCheckedMyCalendar;
    }

    public void setInCheckBoxIsCheckedMyCalendar(RealmList<IsCheckedMyCalendar> inCheckBoxIsCheckedMyCalendar) {
        this.inCheckBoxIsCheckedMyCalendar = inCheckBoxIsCheckedMyCalendar;
    }


    public RealmList<IsCheckedMyCalendar> getIsCheckedMyCalendarList() {
        return isCheckedMyCalendarList;
    }

    public void setIsCheckedMyCalendarList(RealmList<IsCheckedMyCalendar> isCheckedMyCalendarList) {
        this.isCheckedMyCalendarList = isCheckedMyCalendarList;
    }

    public RealmList<Belonging> getBelongingList() {
        return belongingList;
    }

    public void setBelongingList(RealmList<Belonging> belongingList) {
        this.belongingList = belongingList;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String datetime) {
        this.time = datetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
