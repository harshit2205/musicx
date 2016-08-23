package com.example.user.myfirstappdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 7/28/2016.
 */
public class SongRow {
    private static int totalSongs = 0;
    private int id;
    private static List<SongRow> songs;
    private String songname,songpath,artist;

    public static List<String> songlist,completepath,artistname;
    public SongRow(String songname,String path,String artist) {
        totalSongs++;
        this.id = totalSongs;
        this.songname =songname;
        this.songpath = path;
        this.artist = artist;
    }
    public String gettitle(){ return songname;}
    public String getpath(){ return songpath;}
    public String getArtist(){ return artist;}
    public SongRow getnext(int index){
        if(index==songs.size()-1){
            index=0;
            return songs.get(index);
        }
        else {
            return songs.get(index++);
        }

    }
    public SongRow getprev(int index){
        if(index ==0){
            index=songs.size()-1;
            return songs.get(index);
        }
        else{
        songs.get(index-1);
            return songs.get(index--);
        }
    }
    public int getSize(){
        return songs.size();
    }

    public static void Listinputfinder(List<String> list,List<String> path,List<String> artist){
        songlist=list;
        completepath=path;
        artistname=artist;
    }


    public static List<SongRow> allSongs() {

       songs= new ArrayList<>();
        for(int i=0;i<songlist.size();i++) {
            songs.add(new SongRow(songlist.get(i),completepath.get(i),artistname.get(i)));
        }
        return songs;
    }
}
