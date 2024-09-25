package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aircraftwar2024.R;


public class OfflineActivity extends AppCompatActivity implements View.OnClickListener {

    boolean musicOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.getActivityManager().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        musicOn = getIntent().getBooleanExtra("musicOn",false);
        Button btnEasy = (Button) findViewById(R.id.easy);
        btnEasy.setOnClickListener(this);
        Button btnNormal = (Button) findViewById(R.id.normal);
        btnNormal.setOnClickListener(this);
        Button btnDifficult = (Button) findViewById(R.id.difficult);
        btnDifficult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
        if (v.getId() == R.id.easy) {
            intent.putExtra("gameType",1);
        }
        else if (v.getId() == R.id.normal) {
            intent.putExtra("gameType",2);
        }
        else if (v.getId() == R.id.difficult) {
            intent.putExtra("gameType",3);
        }
        intent.putExtra("musicOn", musicOn);
        startActivity(intent);

    }



}