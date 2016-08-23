package com.example.user.myfirstappdemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewScreen extends AppCompatActivity {

    private RecyclerView songlist;
    private TextView toptext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_screen);
        songlist=(RecyclerView)findViewById(R.id.songlist);
        songlist.setLayoutManager(new LinearLayoutManager(this));
        songlist.setAdapter(new SongListAdapter());

        toptext = (TextView)findViewById(R.id.musicx);

        Typeface font = Typeface.createFromAsset(ListViewScreen.this.getAssets(), "fonts/Lobster-Regular.ttf");

        toptext.setTypeface(font);


        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = this.managedQuery(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

        List<String> songs = new ArrayList<String>();
        List<String> path  = new ArrayList<String>();
        List<String> artist  = new ArrayList<String>();

        while(cursor.moveToNext()) {
            songs.add(cursor.getString(0));
            String filePath = cursor.getString(1);
            path.add(filePath);
            artist.add(cursor.getString(2));
//            Log.d("sss",cursor.getString(1));
        }
        SongRow.Listinputfinder(songs,path,artist);



    }

    public class SongListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTextView, artistnameview;
        int index;
        private SongRow currentSong;

        public SongListViewHolder(View itemView) {
            super(itemView);
            titleTextView=(TextView)itemView.findViewById(R.id.title);
            artistnameview=(TextView)itemView.findViewById(R.id.artist);

            Typeface font = Typeface.createFromAsset(ListViewScreen.this.getAssets(), "fonts/OpenSans-Regular.ttf");
            Typeface font1 = Typeface.createFromAsset(ListViewScreen.this.getAssets(), "fonts/OpenSans-Light.ttf");

            titleTextView.setTypeface(font);
            artistnameview.setTypeface(font1);

            itemView.setOnClickListener(this);
        }
        public void bindSong(SongRow song,int position){
            titleTextView.setText(song.gettitle());
            artistnameview.setText(song.getArtist());
            this.index =position;
            this.currentSong = song;
        }

        @Override
        public void onClick(View v) {
//            Log.d("sss","intent for home activity called");
//            Log.d("sss", String.valueOf(index));
            Intent i=HomeScreenActivity.intentStarter(getApplicationContext(), index, currentSong);
            startActivity(i);

        }

    }

    public class SongListAdapter extends RecyclerView.Adapter<SongListViewHolder>{
        @Override
        public SongListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.activity_song_row,parent, false);
            return new SongListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SongListViewHolder holder, int position) {
            SongRow song = SongRow.allSongs().get(position);
            holder.bindSong(song,position);
        }

        @Override
        public int getItemCount() {
            return SongRow.allSongs().size();
        }

    }


}
