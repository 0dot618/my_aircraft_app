package com.example.aircraftwar2024.activity;

import static com.example.aircraftwar2024.R.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
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

public class OnlineActivity extends AppCompatActivity{

    public static final String TAG = "OnlineActivity";
    Boolean musicOn = true;
    private Socket socket;
    public Handler handler;
    private PrintWriter writer;
    private BufferedReader reader;
    //把音乐选中状态放在这个musicOn里面
    public static Boolean isOnline = false;
    public static Boolean bothOnline = false;

    public BaseGame game;
    private static int opponentScore = 0;
    private static boolean bothGameOver = false;
//    public static int screenWidth,screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getScreenHW();

        ActivityManager.getActivityManager().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicOn = getIntent().getBooleanExtra("musicOn",true);

        AlertDialog.Builder builder = new AlertDialog.Builder(OnlineActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setMessage("匹配中，请等待……");
        dialog.show();
        isOnline = true;

        //用于发送接收到的服务器端的消息，显示在界面上
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                if (msg.what == 0x123 && msg.obj.equals("start")) {
                    dialog.dismiss();
                    bothOnline = true;

                    game = new MediumGame(OnlineActivity.this, handler);
                    game.setMusicOn(musicOn);
                    setContentView(game);

                    new Thread(() -> {

                        while (!game.isGameOver()) {
                            writer.println(game.getScore());
                            Log.i(TAG,"客户端->服务器：当前成绩"+game.getScore());
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        writer.println("end");
                        Log.i(TAG,"客户端->服务器：end");

                    }).start();

                } else if (msg.what == 0x123 && msg.obj.equals("gameover")) {
                    Log.i(TAG,"game over");
                    bothOnline = false;

                    Intent intent = new Intent(OnlineActivity.this, ResultActivity.class);
                    intent.putExtra("myScore",game.getScore());
                    intent.putExtra("opScore",opponentScore);
                    startActivity(intent);
                    Log.i(TAG,"show result");

                } else {
                    try {
                        if ((String)msg.obj != null){
                            opponentScore = Integer.parseInt((String)msg.obj);
                        }
                    } catch (NumberFormatException e) {

                    }

                }
            }
        };

        new Thread(new MyThread(handler)).start();
//        myHandler = new MHandler();

    }
    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        GameActivity.screenWidth= dm.widthPixels;
        //窗口高度
        GameActivity.screenHeight = dm.heightPixels;

//        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }

    class MyThread implements Runnable{
        private Handler handler;    //向客户端的UI发送消息

        public MyThread(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {

            try{
                //利用套接字连接到服务器端
                socket = new Socket();
                socket.connect(new InetSocketAddress("10.0.2.2", 9999));
                writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8)), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

                //创建子线程接收服务端信息
                new Thread(() -> {
                    String msg = null;
                    try{
                        while ((msg = reader.readLine()) != null){
                            Log.e(TAG, "客户端<-服务器: "+msg);

                            Message msgFromServer = new Message();
                            msgFromServer.what = 0x123;
                            msgFromServer.obj = msg;
                            handler.sendMessage(msgFromServer);
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static int getOpponentScore() {
        return opponentScore;
    }



}