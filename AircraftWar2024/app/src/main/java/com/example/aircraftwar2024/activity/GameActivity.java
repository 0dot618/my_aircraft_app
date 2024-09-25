package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.dao.Score;
import com.example.aircraftwar2024.dao.ScoreDaoImpl;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;

import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GameActivity extends AppCompatActivity  {
    private static final String TAG = "GameActivity";

    Boolean musicOn = true;

    private int gameType=2;
    public static int screenWidth,screenHeight;
    public static Handler mHandler;

    private String userName = "Test";

    private int score;
    BaseGame baseGameView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Mhandler();
        getScreenHW();
        ActivityManager.getActivityManager().addActivity(this);
        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",2);
            musicOn = getIntent().getBooleanExtra("musicOn",true);
        }

        /*TODO:根据用户选择的难度加载相应的游戏界面*/
        Handler mhandler = new Mhandler();
        if(gameType == 1){
            baseGameView=new EasyGame(this,mhandler);

        }else if(gameType == 2){
            baseGameView= new MediumGame(this,mhandler);
        }else if(gameType == 3){
            baseGameView=new HardGame(this,mhandler);
        }
        setContentView(baseGameView);
        baseGameView.setMusicOn(musicOn);


    }
    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        screenWidth= dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    // 步骤1：（自定义）新创建Handler子类(继承Handler类) & 复写handleMessage（）方法
    class Mhandler extends Handler {

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            // 根据不同线程发送过来的消息，执行不同的UI操作
            // 根据 Message对象的what属性 标识不同的消息
            if(msg.obj=="游戏结束"){

                score = baseGameView.getScore();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = simpleDateFormat.format(curDate);

                ScoreDaoImpl scoreDao = new ScoreDaoImpl(gameType);
                Score scoreRecord = new Score(userName,score,str);
                try {
                    scoreDao.doAdd(scoreRecord,GameActivity.this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Intent intent = new Intent(GameActivity.this, RecordActivity.class);
                intent.putExtra("gameType",gameType);
                startActivity(intent);
//                setContentView(R.layout.activity_record);

            }
        }
    }
}