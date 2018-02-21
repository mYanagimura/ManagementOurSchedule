package com.example.kukilemo.managementourschedule;

import android.widget.TextView;

/**
 * Created by kukilemo on 18/02/10.
 */

public class ChangeMyScheduleNotify {

    private TextView textView;

    private static final int PREVIOUS_DATE=1111111111;
    private static final int LATEST_DATE=1111111110;
    private TextChangeListenerInterface listener =null;

    public ChangeMyScheduleNotify(TextView textView){
    this.textView = textView;

    }

    public void CheckMySchedule(){
        if(this.listener!=null){
           MyCalendar textViewMyCalendar=(MyCalendar) textView.getTag(PREVIOUS_DATE);
           MyCalendar textBoxPreviousMyCalendar=(MyCalendar)textView.getTag(LATEST_DATE);
            if(textViewMyCalendar.getDate()!=textBoxPreviousMyCalendar.getDate()||
                    textViewMyCalendar.getMonth()!=textBoxPreviousMyCalendar.getMonth()||
                    textViewMyCalendar.getYear()!=textBoxPreviousMyCalendar.getYear()){
                listener.ChangeMySchedule();
            }
        }
    }

    public void setListener(TextChangeListenerInterface listener){
        this.listener=listener;
    }
}
