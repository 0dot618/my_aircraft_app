package com.example.aircraftwar2024.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityManager.getActivityManager().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView myScore1=findViewById(R.id.text1);
        TextView opScore1=findViewById(R.id.text2);
        TextView result=findViewById(R.id.text3);

        int myScore = getIntent().getIntExtra("myScore",0);
        int opScore = getIntent().getIntExtra("opScore",0);

        myScore1.setText("你的分数是： "+myScore);
        opScore1.setText("对手分数是： "+opScore);

        if(myScore>opScore){
            result.setText("你赢了！");
        }else if((myScore<opScore)){
            result.setText("你输了！");
        }else{
            result.setText("平局!");
        }


        Button returnbtn = (Button) findViewById(R.id.returnTo);
        returnbtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.returnTo){
            OnlineActivity.isOnline = false;
            while(true) {
                if (ActivityManager.getActivityManager().currentActivity() instanceof MainActivity) {
                    break;
                }
                ActivityManager.getActivityManager().finishActivity();
            }
        }

    }
}