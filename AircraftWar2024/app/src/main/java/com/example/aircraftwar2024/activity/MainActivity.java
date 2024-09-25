package com.example.aircraftwar2024.activity;

import static com.example.aircraftwar2024.R.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.MediumGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean musicOn = false;
    public Boolean isOline = false;
    //把音乐选中状态放在这个musicOn里面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.getActivityManager().addActivity(this);
        Button btn1 = (Button) findViewById(R.id.gameBegin);
        Button btn2 = (Button) findViewById(R.id.gameTogether);
        RadioGroup radioGroup = (RadioGroup) findViewById(id.myRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rcheck = (RadioButton) findViewById(i);
                String checkText = rcheck.getText().toString();
                if(rcheck.getId()== id.open){
                    musicOn = true;
                }else{
                    musicOn = false;
                }
            }
        });
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.gameBegin) {
            Intent intent1 = new Intent(MainActivity.this, OfflineActivity.class);
            intent1.putExtra("musicOn", musicOn);
            startActivity(intent1);
        }
        else if (v.getId() == R.id.gameTogether) {
            isOline = true;
            Intent intent = new Intent(MainActivity.this, OnlineActivity.class);
            intent.putExtra("musicOn", musicOn);
            startActivity(intent);
        }
    }

}