package com.example.kukilemo.managementourschedule;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.view.GestureDetector;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kukilemo on 18/02/06.
 */

public class CustomLayout extends LinearLayout implements GestureDetector.OnGestureListener{
    private final int dateLabel[]={R.id.dateLabel1, R.id.dateLabel2,R.id.dateLabel3,R.id.dateLabel4};

    private static final int SWIPE_MIN_DISTANCE=50;
    private static final int SWIPE_THRESHOLD_VELOCITY=200;
    private static final int SWIPE_MAX_OFF_PATH =250;
    private static final int PREVIOUS_DATE=1111111111;
    private static final int LATEST_DATE=1111111110;
    private View headerLinearLayout;
    protected GestureDetector mGestureDetector;
    private Calendar calendar;
    private MyCalendar latestDayCalendar;
    private ChangeMyScheduleNotify changeMyScheduleNotify;

    public CustomLayout(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context,this);

        headerLinearLayout = LayoutInflater.from(context).inflate(
                R.layout.header_layout,this);
        calendar = Calendar.getInstance();
        int presentDate= calendar.get(Calendar.DATE);
        int presentMonth = calendar.get(Calendar.MONTH)+1;
        int presentYear = calendar.get(Calendar.YEAR);

        for(int i=0;i<4;i++){
            TextView dateLabelTextView = headerLinearLayout.findViewById(dateLabel[i]);
            dateLabelTextView.setText(String.valueOf(presentDate));
            MyCalendar myCalendar= new MyCalendar(presentDate,presentMonth,presentYear);
            dateLabelTextView.setTag(PREVIOUS_DATE,myCalendar);
            dateLabelTextView.setTag(LATEST_DATE,myCalendar);
            presentDate--;
            if(latestDayCalendar ==null){
                latestDayCalendar =new MyCalendar(presentDate,presentMonth,presentYear);
            }
        }

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent){
        super.onInterceptTouchEvent(motionEvent);
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        mGestureDetector.onTouchEvent(motionEvent);

        return true;
    }
    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public  boolean onFling(MotionEvent event1, MotionEvent event2,
                            float velocityX, float velocityY){
        try{
            float distance_x=event1.getX()-event2.getX();
            float distance_y=Math.abs((event1.getY()-event2.getY()));
            float velocity_x=Math.abs(velocityX);

            if(Math.abs(distance_x)>SWIPE_MIN_DISTANCE && velocity_x>SWIPE_THRESHOLD_VELOCITY
                    && SWIPE_MAX_OFF_PATH >distance_y){
                

                if(distance_x>0){
                    //日付を遡っていく処理
                    for(int i=0;i<4;i++){
                        TextView dateLabelTextView=
                                (TextView)headerLinearLayout.findViewById(dateLabel[i]);
                        MyCalendar myCalendar= (MyCalendar) dateLabelTextView.getTag(PREVIOUS_DATE);
                        int myDate=myCalendar.getDate();
                        int myMonth=myCalendar.getMonth();
                        int myYear=myCalendar.getYear();

                        if(myDate!=1) {
                            myDate--;
                        }else{
                            //1日の処理
                            calendar.set(myYear,myMonth,myDate);
                            int lastDayOfMonth = calendar.getActualMaximum(Calendar.DATE);
                            myDate=lastDayOfMonth;
                            if(myMonth==1) {
                                myMonth=12;
                                myYear--;
                                myCalendar.setYear(myYear);
                            }else{
                                myMonth--;
                            }
                            myCalendar.setMonth(myMonth);
                        }
                        myCalendar.setDate(myDate);
                        dateLabelTextView.setText(String.valueOf(myDate));
                        dateLabelTextView.setTag(PREVIOUS_DATE,myCalendar);
                    }
                    ChangeMySchedules();
                }else {
                    for(int i=0;i<4;i++) {
                        TextView dateLabelTextView =
                                (TextView)headerLinearLayout.findViewById(dateLabel[i]);
                        MyCalendar myCalendar= (MyCalendar) dateLabelTextView.getTag(PREVIOUS_DATE);
                        int myDate=myCalendar.getDate();
                        int myMonth=myCalendar.getMonth();
                        int myYear=myCalendar.getYear();


                        if (myDate-1 == latestDayCalendar.getDate() && myMonth == latestDayCalendar.getMonth()
                                && myYear == latestDayCalendar.getYear()) {
                            break;
                        } else {
                            calendar.set(myYear,myMonth+1,myDate);
                            int lastDayOfMonth = calendar.getActualMaximum(Calendar.DATE);

                            if(lastDayOfMonth == myDate){
                                myDate=0;
                                if(myMonth == 12){
                                    myMonth=1;
                                    myYear++;
                                    myCalendar.setYear(myYear);
                                }else{
                                    myMonth++;
                                }
                                myCalendar.setMonth(myMonth);
                            }
                            myDate++;
                            myCalendar.setDate(myDate);

                            dateLabelTextView.setText(String.valueOf(myDate));
                            dateLabelTextView.setTag(PREVIOUS_DATE,myCalendar);
                        }
                    }
                    ChangeMySchedules();

                }
            }
            return true;
        }catch (Exception e){}
        return false;
    }


    public void ChangeMySchedules() {

      //  for(int i=0;i<4;i++){
        //    TextView dateLabelTextView = headerLinearLayout.findViewById(dateLabel[i]);
          //  dateLabelTextView.setTag(LATEST_DATE,dateLabelTextView.getTag(PREVIOUS_DATE));
       // }

        //getViewを使ってViewを更新する

        ListView mListView =(ListView) this.getParent();

        for(int position=1;position<mListView.getChildCount()+1;position++){
            View targetView = mListView.getChildAt(position);
            mListView.getAdapter().getView(position,targetView,mListView);

        }
    }
}
