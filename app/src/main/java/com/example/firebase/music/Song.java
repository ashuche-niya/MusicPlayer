package com.example.firebase.music;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Song extends Fragment implements Serializable {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    final String MEDIA_PATH = Environment.getExternalStorageDirectory() + "";

    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    public  ArrayList<songmodel> songmodelList = new ArrayList<songmodel>();
    public static ArrayList<String> staticsonglist = new ArrayList<String>();
    TextView textView1;
    TextView textView2;
    public NotificationManagerCompat notificationManager;
    //static List<songmodel> playedsonglist= new ArrayList<songmodel>();
    public MediaPlayer mediaPlayer;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.songs, null);
       // notificationManager = NotificationManagerCompat.from(getContext());
//        playedsonglist.add(new songmodel("last hurrah","bebe rexa"));
//        playedsonglist.add(new songmodel("hello ","ashish"));
        View view1 = inflater.inflate(R.layout.playedsongs, null);
       // notificationosngname =view.findViewById(R.id.notificationsongname);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        loadsongs();
        int i=0;
        if(staticsonglist.size()!=0)
        {
            staticsonglist.clear();
        }
        while(i<songmodelList.size())
        {
            staticsonglist.add(songmodelList.get(i).getSongname());
            i++;
        }
        songadapter songadapter=new songadapter(getContext(), songmodelList);
        recyclerView.setAdapter(songadapter);
        songadapter.setOnItemClickListener(new songadapter.OnItemClickListener() {
            @Override
            public void onItemClick(LinearLayout linearLayout, View v, songmodel smod, int position) {

                        startActivity(new Intent(getContext(),frontsongview.class)
                                .putExtra("songname",smod.getSongname())
                                .putExtra("artistname",smod.getArtistname())
                                .putExtra("position",position)
                        .putExtra("allsonglist",songmodelList));//putExtra("allsonglist", String.valueOf(songmodelList)))

                    }
                });

        return view;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void loadsongs() {
        String[] STAR = {"mp3"};
        Cursor cursor;
        Uri allsonguri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        String selection2 = MediaStore.Audio.Media._ID ;
        cursor = getActivity().getContentResolver().query(allsonguri, null, selection,null,null);
        //cursor= Objects.requireNonNull(getContext()).getContentResolver().query(allsonguri,STAR, selection,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String songname = cursor
                            .getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));


                   int songid = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String artistname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String songuri=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    songmodelList.add(new songmodel(songname, artistname,songuri));

                } while (cursor.moveToNext());
            }
            cursor.close();

        }
    }

}
