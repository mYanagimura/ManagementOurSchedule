package com.example.kukilemo.managementourschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private Realm mRealm;
    private ListView mListView;
    private ScheduleAdapter adapter;
    private CustomLayout headerLinearLayout;

    private static final int PREVIOUS_DATE=1111111111;
    private static final int LATEST_DATE=1111111110;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRealm =Realm.getDefaultInstance();
        RealmResults<Schedule> schedules=mRealm.where(Schedule.class).findAll();

        headerLinearLayout = new CustomLayout(this);




        mListView = findViewById(R.id.listView);
        mListView.addHeaderView(headerLinearLayout,null,false);
        adapter = new ScheduleAdapter(schedules);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Schedule schedule =
                                (Schedule) adapterView.getItemAtPosition(i);
                        startActivity(new Intent(MainActivity.this,
                                ScheduleEditActivity.class).
                                putExtra("Schedule_id",schedule.getId()));
                    }
                }
        );



    }

    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_schedule) {
            startActivity(new Intent(MainActivity.this,
                    ScheduleEditActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
