package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.text.BoringLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;

import java.util.HashMap;

public class MySoundPool  {
    Context context;
    SoundPool mysp;
    HashMap<Integer, Integer> soundPoolMap;
    private Boolean musicOn;

    public MySoundPool(Context context, Boolean musicOn){
        this.context = context;
        this.musicOn = musicOn;
        createSoundPool();

        soundPoolMap = new HashMap<Integer,Integer>();

        soundPoolMap.put(1,mysp.load(context, R.raw.bullet_hit,1));
        soundPoolMap.put(2,mysp.load(context, R.raw.bomb_explosion,1));
        soundPoolMap.put(3,mysp.load(context, R.raw.get_supply,1));
        soundPoolMap.put(4,mysp.load(context, R.raw.game_over,1));

    }
    private void createSoundPool() {
        if (mysp == null) {
            // Android 5.0 及 之后版本
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = null;
                audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                mysp = new SoundPool.Builder()
                        .setMaxStreams(10)
                        .setAudioAttributes(audioAttributes)
                        .build();
            } else { //Android 5.0 以前版本
                mysp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);  // 创建SoundPool
            }
        }
    }
    public void bullet_hit(){
        if(musicOn){
            mysp.play(soundPoolMap.get(1),1,1,0,0,1);
        }
    }
    public void bomb_explosion(){
        if(musicOn){
            mysp.play(soundPoolMap.get(2),1,1,0,0,1);
        }
    }
    public void get_supply(){
        if(musicOn){
            mysp.play(soundPoolMap.get(3),1,1,0,0,1);
        }
    }
    public void game_over(){
        if(musicOn){
            mysp.play(soundPoolMap.get(4),1,1,0,0,1);
        }
    }
}
