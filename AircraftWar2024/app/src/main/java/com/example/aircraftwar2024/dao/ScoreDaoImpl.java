package com.example.aircraftwar2024.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.example.aircraftwar2024.activity.RecordActivity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ScoreDaoImpl implements ScoreDao {
    private List<Score> scores;
    private String file;

    public ScoreDaoImpl(int difficulty){
        scores =new ArrayList<>();

        if(difficulty==1){
            file = "EasyScores.txt";
        }
        else if(difficulty==2){
            file = "MediumScores.txt";
        }
        else{
            file = "HardScores.txt";
        }

    }

    @Override
    public void doAdd(Score score, Context context) throws IOException {
        scores.add(score);
        FileOutputStream fop= context.openFileOutput(file,Context.MODE_APPEND);
        //FileOutputStream fop=new FileOutputStream(file,true);
        OutputStreamWriter writer=new OutputStreamWriter(fop,"utf-8");
        writer.append(score.getUserName()+"\n"+ score.getMark()+"\n"+ score.getTime()+"\n");
        writer.close();
        fop.close();

    }

    @Override
    public String[][] getAll(Context context) throws IOException {
        scores.clear();
        FileInputStream fip= context.openFileInput(file);
//        FileInputStream fip=new FileInputStream(file);
        InputStreamReader reader=new InputStreamReader(fip,"utf-8");
        BufferedReader br=new BufferedReader(reader);
        String line=br.readLine();
        while(line!=null){
            Score score=new Score();
            score.setUserName(line);
            line=br.readLine();
            String markS = line;
            int markInt=Integer.parseInt(markS);
            score.setMark(markInt);
            line=br.readLine();
            score.setTime(line);
            line=br.readLine();
            scores.add(score);
        }
        br.close();
        reader.close();
        fip.close();

        scores.sort(Comparator.comparing(Score::getMark));
        Collections.reverse(scores);

        System.out.println("***********************************");
        System.out.println("             得分排行榜             ");
        System.out.println("***********************************");

        String[][] scoreTable=new String[scores.size()][4];

        for(int i = 0; i< scores.size(); i++){
            System.out.print("第"+(i+1)+"名：");
            scoreTable[i][0]= String.valueOf(i+1);
            System.out.print(scores.get(i).getUserName()+',');
            scoreTable[i][1]=scores.get(i).getUserName();
            System.out.print(scores.get(i).getMark());
            scoreTable[i][2]= String.valueOf(scores.get(i).getMark());
            System.out.print(','+scores.get(i).getTime()+"\n");
            scoreTable[i][3]=scores.get(i).getTime();
        }
        return scoreTable;
    }

    public void delete(String userName,String mark,String time,Context context) throws IOException {
        scores.clear();
        FileInputStream fip= context.openFileInput(file);
//        FileInputStream fip=new FileInputStream(file);
        InputStreamReader reader=new InputStreamReader(fip,"utf-8");
        BufferedReader br=new BufferedReader(reader);
        String line=br.readLine();
        while(line!=null){
            Score score=new Score();
            score.setUserName(line);
            line=br.readLine();
            String markS = line;
            int markInt=Integer.parseInt(markS);
            score.setMark(markInt);
            line=br.readLine();
            score.setTime(line);
            if(!(score.getUserName().equals(userName) && score.getMark()==Integer.parseInt(mark) && score.getTime().equals(time))){
                scores.add(score);
            }
            line=br.readLine();
        }
        br.close();
        reader.close();
        fip.close();


        FileOutputStream fop2= context.openFileOutput(file,MODE_PRIVATE);
//        FileInputStream fip=new FileInputStream(file);
//        File temp=new File("scorescopy.txt");
//        FileOutputStream fop=new FileOutputStream(temp,true);
        OutputStreamWriter writer=new OutputStreamWriter(fop2,"utf-8");
        for(Score score:scores){
            writer.append(score.getUserName()+"\n"+ score.getMark()+"\n"+ score.getTime()+"\n");
        }
        writer.close();
        fop2.close();


    }
}
