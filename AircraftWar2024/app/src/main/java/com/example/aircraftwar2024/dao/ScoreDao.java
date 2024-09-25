package com.example.aircraftwar2024.dao;

import android.content.Context;

import java.io.IOException;

public interface ScoreDao {

    void doAdd(Score score, Context context) throws IOException;

    String[][] getAll(Context context) throws IOException;

    void delete(String userName,String mark,String time,Context context) throws IOException;
}
