package com.example.user.myfirstappdemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cleveroad.audiovisualization.AudioVisualization;

public class TestActivity extends AppCompatActivity {

    private MediaPlayer myplayer;
    private AudioVisualization audioVisualization;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myplayer.stop();
    }
}
