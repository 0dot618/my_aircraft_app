package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.aircraftwar2024.R;

public class MyMediaPlayer {

    Context context;
    MediaPlayer bgMP;
    MediaPlayer bgBossMP;
    int position;
    boolean musicOn;

    public MyMediaPlayer(Context context,Boolean musicOn) {
        this.context = context;
        bgMP = MediaPlayer.create(context, R.raw.bgm);
        bgBossMP = MediaPlayer.create(context, R.raw.bgm_boss);
        this.musicOn = musicOn;
    }
//
//    public void startBgMP(){
//            start(bgMP);
//    }
//
//    public void startBgBossMP(){
//            start(bgBossMP);
//    }
//
//    public void pause() {
//        position = bgMP.getCurrentPosition();
//        bgMP.seekTo(position);
//    }
//
//    public void goOn() {
//            bgMP.start();
//    }
//
//    public void stopBgMP() {
//            stop(bgMP);
//    }
//
//    public void stopBgBossMP() {
//            stop(bgBossMP);
//    }

    public void startBgMP(){
        if(musicOn)
            start(bgMP);
    }

    public void startBgBossMP(){
        if(musicOn)
            start(bgBossMP);
    }

    public void pause() {
        if(musicOn){
            bgMP.pause();
        }
    }

    public void goOn() {
        if(musicOn){
            position = bgMP.getCurrentPosition();
            bgMP.seekTo(position);
            bgMP.start();
        }
    }

    public void stopBgMP() {
        if(musicOn)
            stop(bgMP);
    }

    public void stopBgBossMP() {
        if(musicOn)
            stop(bgBossMP);
    }


    public void start(MediaPlayer bg) {
        bg.start();
        bg.setLooping(true);
    }


    public void stop(MediaPlayer bg) {
        bg.stop();
        bg.release();
        bg = null;
    }

}
