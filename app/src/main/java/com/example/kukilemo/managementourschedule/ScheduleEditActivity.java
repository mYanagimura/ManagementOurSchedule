package com.example.kukilemo.managementourschedule;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Optional;

import io.realm.Realm;
import io.realm.RealmResults;

public class ScheduleEditActivity extends AppCompatActivity {

    private Realm mRealm;
    EditText mScheduleEdit;
    EditText mTimeEdit;
    EditText mPlaceEdit;
    EditText mBelongingListEdit;
    Button mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);
        mRealm = Realm.getDefaultInstance();
        mScheduleEdit = (EditText) findViewById(R.id.scheduleEdit);
        mTimeEdit = (EditText) findViewById(R.id.timeEdit);
        mPlaceEdit = (EditText) findViewById(R.id.placeEdit);
        mBelongingListEdit = (EditText) findViewById(R.id.belongingListEdit);
        mDelete=(Button) findViewById(R.id.deleteButton);

        long scheduleId = getIntent().getLongExtra("Schedule_id", -1);
        if (scheduleId != -1) {
            RealmResults<Schedule> results = mRealm.where(Schedule.class).
                    equalTo("id", scheduleId).findAll();
            Schedule schedule = results.first();
            mScheduleEdit.setText(schedule.getTitle());
            mTimeEdit.setText(schedule.getTime());
            mPlaceEdit.setText(schedule.getPlace());
            for (Belonging belonging : schedule.getBelongingList()
                    ) {
                mBelongingListEdit.setText(belonging.getName() + "\n");
            }
            mDelete.setVisibility(View.VISIBLE);
        }else{
            mDelete.setVisibility(View.INVISIBLE);
        }
    }

    public void onDeleteTapped(View view){
        final long scheduleId=getIntent().getLongExtra("Schedule_id",-1);
        if(scheduleId!=-1){
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Schedule schedule =realm.where(Schedule.class)
                            .equalTo("id",scheduleId).findFirst();
                    schedule.deleteFromRealm();
                }
            });
        }
        finish();
    }

    public void onSaveTapped(View view) {
        long scheduleId = getIntent().getLongExtra("Schedule_id", -1);
        if (scheduleId != -1) {
            final RealmResults<Schedule> results = mRealm.where(Schedule.class)
                    .equalTo("id", scheduleId).findAll();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Schedule schedule = results.first();
                    schedule.setTitle(mScheduleEdit.getText().toString());
                    schedule.setTime(mTimeEdit.getText().toString());
                    schedule.setPlace(mPlaceEdit.getText().toString());

                    String belongingListStr = mBelongingListEdit.getText().toString();
                    Optional<String> value = Optional.ofNullable(belongingListStr);
                    belongingListStr = value.orElse("");
                    String[] belongingArray = belongingListStr.split("\n");
                    for (String belongingName : belongingArray
                            ) {
                        final Belonging belonging = new Belonging();
                        belonging.setName(belongingName);
                        final Belonging managedBelonging = realm.copyToRealm(belonging);
                        schedule.getBelongingList().add(managedBelonging);
                    }
                }
            });
            Snackbar.make(findViewById(android.R.id.content),
                    "アップデートしました", Snackbar.LENGTH_LONG)
                    .setAction("戻る", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    })
                    .setActionTextColor(Color.YELLOW).show();
            finish();
        } else {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Number maxId = realm.where(Schedule.class).max("id");
                    long nextId = 0;
                    if (maxId != null) nextId = maxId.longValue() + 1;
                    Schedule schedule
                            = realm.createObject(Schedule.class, new Long(nextId));
                    schedule.setTitle(mScheduleEdit.getText().toString());
                    schedule.setTime(mTimeEdit.getText().toString());
                    schedule.setPlace(mPlaceEdit.getText().toString());

                    String belongingListStr = mBelongingListEdit.getText().toString();
                    Optional<String> value = Optional.ofNullable(belongingListStr);
                    belongingListStr = value.orElse("");
                    String[] belongingArray = belongingListStr.split("\n");
                    for (String belongingName : belongingArray
                            ) {
                        final Belonging belonging = new Belonging();
                        belonging.setName(belongingName);
                        final Belonging managedBelonging = realm.copyToRealm(belonging);
                        schedule.getBelongingList().add(managedBelonging);
                    }
                }
            });

            Toast.makeText(this, getString(R.string.added_schedule), Toast.LENGTH_SHORT).show();
            finish();

        }
    }
}
