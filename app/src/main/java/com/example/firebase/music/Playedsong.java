package com.example.firebase.music;

import com.example.firebase.music.Song;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Playedsong extends Fragment {
    //static Song song = new Song();

    MediaPlayer mediaPlayer;
    public Handler handler=new Handler();
    public List<songmodel> songmodelList = new ArrayList<songmodel>();
    private List<songmodel> playedsonglist = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.playedsongs, null);
        final RecyclerView recyclerView = view.findViewById(R.id.playedsongrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        int listsize=frontsongview.playedsonglist.size();
        int i=1;
        while(i<=listsize) {
            playedsonglist.add(frontsongview.playedsonglist.get(frontsongview.playedsonglist.size() - i));
            i++;
        }

        playedsongadapter playedsongadapter=new playedsongadapter(getContext(),playedsonglist);
        recyclerView.setAdapter(playedsongadapter);
        return view;

    }

}
