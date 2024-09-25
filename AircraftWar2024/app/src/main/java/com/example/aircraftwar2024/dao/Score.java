package com.example.aircraftwar2024.dao;

public class Score {
    private int mark;
    private String userName;
    private String time;

    public Score() {

    }
    public Score(String userName, int mark, String time) {
        this.mark = mark;
        this.userName = userName;
        this.time = time;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
