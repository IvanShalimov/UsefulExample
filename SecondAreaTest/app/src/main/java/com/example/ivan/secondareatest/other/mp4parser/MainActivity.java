package com.example.ivan.secondareatest.other.mp4parser;

import android.app.ActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ivan.secondareatest.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    List<ActivityManager.RunningTaskInfo> list;
    ActivityManager am;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.app_name) + " : " + getLocalClassName());
        am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        button = findViewById(R.id.test);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //startActivity(new Intent("mngtasks1_activity_c"));
        Uri carriot = Uri.parse("https://devel.cariot.ru/mobile");
        Intent intent = new Intent(Intent.ACTION_VIEW,carriot);
        startActivity(intent);
    }


    public void onInfoClick(View v) {
        list = am.getRunningTasks(10);
        for (ActivityManager.RunningTaskInfo task : list) {
            if (task.baseActivity.flattenToShortString().startsWith("com.example.ivan.secondareatest")) {
                Log.d(LOG_TAG, "------------------");
                Log.d(LOG_TAG, "Count: " + task.numActivities);
                Log.d(LOG_TAG, "Root: " + task.baseActivity.flattenToShortString());
                Log.d(LOG_TAG, "Top: " + task.topActivity.flattenToShortString());
            }
        }
    }
}
