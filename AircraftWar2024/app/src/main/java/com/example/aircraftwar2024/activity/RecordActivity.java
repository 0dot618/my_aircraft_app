package com.example.aircraftwar2024.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.dao.ScoreDao;
import com.example.aircraftwar2024.dao.ScoreDaoImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener{

    private ScoreDao scoreDao = null;
    private int gameType;

    private TextView text;
    SimpleAdapter listItemAdapter = null;
    String[][] scoreTable = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityManager.getActivityManager().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Button returnbtn = (Button) findViewById(R.id.returnTo);
        returnbtn.setOnClickListener(this);
        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
        }
        scoreDao = new ScoreDaoImpl(gameType);

        text=findViewById(R.id.gameType);
        if(gameType==1) text.setText("简单模式");
        else if(gameType==2) text.setText("普通模式");
        else text.setText("困难模式");

        //获得Layout里面的ListView
        ListView list = (ListView) findViewById(R.id.ListView01);

        //生成适配器的Item和动态数组对应的元素

        try {
            listItemAdapter = new SimpleAdapter(
                    this,
                    getData(),
                    R.layout.activity_item,
                    new String[]{"rank","name","score","time"},
                    new int[]{R.id.rank,R.id.name,R.id.score,R.id.time});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //添加并且显示
        list.setAdapter(listItemAdapter);
        //添加单击监听
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
                builder.setTitle("提示")
                        .setMessage("你确定删除第"+arg2+"行数据吗");
                AlertDialog dialog = builder.create();
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        listitem.remove(arg2);
                        listitem.clear();
                        listItemAdapter.notifyDataSetChanged();
                        try {
                            scoreDao.delete(scoreTable[arg2-1][1],scoreTable[arg2-1][2],scoreTable[arg2-1][3],RecordActivity.this);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        SimpleAdapter listItemAdapter1=null;

                        try {
                            listItemAdapter1 = new SimpleAdapter(
                                    RecordActivity.this,
                                    getData(),
                                    R.layout.activity_item,
                                    new String[]{"rank","name","score","time"},
                                    new int[]{R.id.rank,R.id.name,R.id.score,R.id.time});
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        //添加并且显示
                        list.setAdapter(listItemAdapter1);

                    }
                });
                builder.show();
            }
        });
    }

    ArrayList<Map<String, Object>> listitem = null;
    private List<Map<String, Object>> getData() throws IOException {
        listitem = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rank", "排名");
        map.put("name", "用户");
        map.put("score", "得分");
        map.put("time", "时间");
        listitem.add(map);
        scoreTable = null;
        scoreTable=scoreDao.getAll(RecordActivity.this);

        for(int i = 0; i< scoreTable.length; i++){
            map = new HashMap<String, Object>();
            map.put("rank",scoreTable[i][0]);
            map.put("name",scoreTable[i][1]);
            map.put("score",scoreTable[i][2]);
            map.put("time",scoreTable[i][3]);
            listitem.add(map);
        }


        return listitem;
    }
    public void onClick(View v) {
        if(v.getId() == R.id.returnTo){
            while(true) {
                if (ActivityManager.getActivityManager().currentActivity() instanceof MainActivity) {
                    break;
                }
                ActivityManager.getActivityManager().finishActivity();
            }
        }

    }
}