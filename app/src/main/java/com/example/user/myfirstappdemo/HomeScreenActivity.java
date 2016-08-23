package com.example.user.myfirstappdemo;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.audiovisualization.AudioVisualization;

import java.io.File;
import java.io.IOException;

public class HomeScreenActivity extends AppCompatActivity {
    private ImageView playicon;
    private ImageView previousicon, nexticon;
    public MediaPlayer myplayer;
    private static int position=0;
    private static String songname,artistname,pathname;
    private NotificationCompat.Builder builder;
    private NotificationManager notifier;
    TextView outputsongname, outputartist,homescreentop;
    public boolean playpressed = true;
    private static SongRow row;
    private AudioVisualization audioVisualization;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        builder.setOngoing(false);
        notifier.cancel(001);
        myplayer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        outputartist = (TextView) findViewById(R.id.outputartistname);
        outputsongname =(TextView)findViewById(R.id.outputsongname);
        homescreentop = (TextView)findViewById(R.id.home_Screen_top);

        Typeface font = Typeface.createFromAsset(HomeScreenActivity.this.getAssets(), "fonts/Lobster-Regular.ttf");
        Typeface font1 = Typeface.createFromAsset(HomeScreenActivity.this.getAssets(), "fonts/OpenSans-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(HomeScreenActivity.this.getAssets(), "fonts/OpenSans-Light.ttf");

        homescreentop.setTypeface(font);
        outputsongname.setTypeface(font1);
        outputartist.setTypeface(font2);

        songname = row.gettitle();
        artistname =row.getArtist();
        pathname =row.getpath();


        previousicon= (ImageView)findViewById(R.id.previousicon);
        nexticon =(ImageView)findViewById(R.id.nexticon);
        Log.d("xxx",Integer.toString(position));

        playsongcreater();

        playListener();

        previousListener();

        nextListener();

//        audioVisualization = (AudioVisualization) findViewById(R.id.visualizer_view);
//        VisualizerDbmHandler vizualizerHandler = DbmHandler.Factory.newVisualizerHandler(this,myplayer);
//        audioVisualization.linkTo(vizualizerHandler);

        notificationBuilder();

    }

    public static Intent intentStarter(Context context , int index, SongRow rowOne) {

        Intent i = new Intent(context, HomeScreenActivity.class);

        position = index;

        row = rowOne;

        return i;

    }

    public void playsongcreater() {

        myplayer = new MediaPlayer();
        myplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String completePath = pathname;
        File file = new File(completePath);
        Uri myUri1 = Uri.fromFile(file);
        try {

            myplayer.setDataSource(getApplicationContext(), myUri1);

        } catch (IllegalArgumentException e)
        {
            Toast.makeText(getApplicationContext(), "error 1", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "error 2", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "error 3", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myplayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "error 4", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "error 5", Toast.LENGTH_LONG).show();
        }
        myplayer.start();
        outputartist.setText(artistname);
        outputsongname.setText(songname);
    }

    private void playListener() {
        playicon = (ImageView) findViewById(R.id.playbutton);
        playicon.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                                if (playpressed) {
                                                    myplayer.pause();
                                                    playpressed = false;
                                                    playicon.setImageResource(R.drawable.icon_play_unpressed);

                                                } else {
                                                    myplayer.start();
                                                    playpressed = true;
                                                    playicon.setImageResource(R.drawable.icon_paused_unpressed);

                                                }
                                            }
                                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                            }
                                            return false;
                                        }
                                    }
        );
    }

    private void previousListener(){

        previousicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

              myplayer.stop();
                row = row.getprev(position);
                Log.d("xxx",Integer.toString(position));
                songname = row.gettitle();
                artistname =row.getArtist();
                pathname =row.getpath();
                notificationBuilder();
                playsongcreater();
                position--;
                if(position==0){
                    position = row.getSize()-1;
                }

            }
        });
       }

    private void nextListener(){
        nexticon.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                notifier.cancel(001);
                myplayer.stop();
                row = row.getnext(position);
                Log.d("xxx",Integer.toString(position));
                songname = row.gettitle();
                artistname =row.getArtist();
                pathname =row.getpath();
                notificationBuilder();
                playsongcreater();
                position++;
                if(position==row.getSize()-1){
                    position=0;
                }
            }
        });
    }

    private void notificationBuilder(){
        builder= (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(songname)
                .setContentText(artistname);


        builder.setOngoing(true);
        int notificationid = 001;
        notifier=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notifier.notify(notificationid,builder.build());

    }

}

