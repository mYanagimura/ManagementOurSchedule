package com.example.kukilemo.managementourschedule;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmList;

/**
 * Created by kukilemo on 18/01/28.
 */

public class ScheduleAdapter extends RealmBaseAdapter<Schedule>  {

    private static final int PREVIOUS_DATE=1111111111;
    private static final int LATEST_DATE=1111111110;
    private Realm mRealm;
    private static class ViewHolder{
        TextView title;
        LinearLayout checkBoxLayout;
    }

    public ScheduleAdapter(@Nullable OrderedRealmCollection<Schedule> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
       final ViewHolder viewHolder;

        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.original_constraint_layout,parent,false);
            viewHolder =new ViewHolder();
            viewHolder.title= convertView.findViewById(R.id.originalTitleLabel);
            viewHolder.checkBoxLayout = convertView.findViewById(R.id.originalCheckBoxLayout);

            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        final Schedule schedule =adapterData.get(position);

        viewHolder.title.setText(schedule.getTitle());

        final int dateLabel[]={R.id.dateLabel1, R.id.dateLabel2,R.id.dateLabel3,R.id.dateLabel4};
        CustomLayout header = (CustomLayout)parent.getChildAt(0);
        LinearLayout linear=(LinearLayout)header.getChildAt(0);
        ConstraintLayout constaint=(ConstraintLayout)linear.getChildAt(0);
        final LinearLayout linear2=(LinearLayout)constaint.getChildAt(0);

        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(schedule.getIsCheckedMyCalendarList().size()==0){
                    //各スケジュールのgetIsCheckedMyCalendarの初期化

                    for (int i=0;i<4;i++) {

                        TextView dateLabelTextView = (TextView) linear2.getChildAt(i);
                        IsCheckedMyCalendar isCheckedMyCalendar= new IsCheckedMyCalendar();
                        isCheckedMyCalendar.setIsChecked(false);
                        isCheckedMyCalendar.setMyCalendar((MyCalendar) dateLabelTextView.getTag(PREVIOUS_DATE));
                        schedule.getIsCheckedMyCalendarList().add(isCheckedMyCalendar);
                    }
                }else{
                    //各スケジュールのgetIsCheckedMyCalendarの追加
                    //checkBoxの更新
                    for (int i=0;i<4;i++
                            ) {
                        TextView dateLabelTextView = (TextView) linear2.getChildAt(i);
                        MyCalendar textViewMyCalendar=(MyCalendar)dateLabelTextView.getTag(PREVIOUS_DATE);

                        Boolean included =false;
                        for (IsCheckedMyCalendar isCheckedMyCalendar: schedule.getIsCheckedMyCalendarList()) {
                            if(isCheckedMyCalendar.getMyCalendar().getDate()==textViewMyCalendar.getDate()&&
                                    isCheckedMyCalendar.getMyCalendar().getYear()==textViewMyCalendar.getYear()&&
                                    isCheckedMyCalendar.getMyCalendar().getMonth()==textViewMyCalendar.getMonth()){
                                CheckBox checkBox= (CheckBox) viewHolder.checkBoxLayout.getChildAt(i);
                                checkBox.setChecked(isCheckedMyCalendar.getIsChecked());
                                checkBox.setTag(isCheckedMyCalendar.getMyCalendar());
                                included=true;
                            }
                        }
                        if(!included){
                            IsCheckedMyCalendar isCheckedMyCalendar= new IsCheckedMyCalendar();
                            isCheckedMyCalendar.setIsChecked(false);
                            isCheckedMyCalendar.setMyCalendar((MyCalendar) dateLabelTextView.getTag(PREVIOUS_DATE));
                            schedule.getIsCheckedMyCalendarList().add(isCheckedMyCalendar);
                        }
                    }
                }

                //CheckBoxの値を取得するためのイベントリスナーの登録
                if(schedule.getIsCheckedMyCalendarList().size()!=0&&
                        schedule.getInCheckBoxIsCheckedMyCalendar().size()==0){
                    for (int i=0;i<4;i++) {
                        final CheckBox checkBox= (CheckBox) viewHolder.checkBoxLayout.getChildAt(i);

                        final int finalI = i;
                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mRealm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        TextView dateLabelTextView = (TextView) linear2.getChildAt(finalI);
                                        MyCalendar textViewMyCalendar=(MyCalendar)dateLabelTextView.getTag(PREVIOUS_DATE);

                                        for (int position=0;position<schedule.getIsCheckedMyCalendarList().size();
                                             position++) {
                                            IsCheckedMyCalendar isCheckedMyCalendar = schedule.getIsCheckedMyCalendarList()
                                                    .get(position);
                                            MyCalendar checkBoxMyCalendar =isCheckedMyCalendar.getMyCalendar();
                                            if(textViewMyCalendar.getDate() == checkBoxMyCalendar.getDate()&&
                                                    textViewMyCalendar.getMonth() == checkBoxMyCalendar.getMonth()&&
                                                    textViewMyCalendar.getYear() == checkBoxMyCalendar.getYear()){
                                                isCheckedMyCalendar.setIsChecked(checkBox.isChecked());

                                                if(schedule.getInCheckBoxIsCheckedMyCalendar().size()<5) {
                                                    //初期化
                                                    schedule.getInCheckBoxIsCheckedMyCalendar().add(isCheckedMyCalendar);
                                                }else{
                                                    //上書き
                                                    schedule.getInCheckBoxIsCheckedMyCalendar().set(finalI,
                                                            isCheckedMyCalendar);
                                                }
                                            }
                                        }
                                    }
                                });

                            }
                        });

                    }
                }



            }
        });

        return convertView;
    }
}
