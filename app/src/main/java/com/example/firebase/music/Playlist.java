package com.example.firebase.music;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class Playlist extends Fragment {
    String songname[];
    String json,json1;
    Type type,type1;
    boolean checkeditem[];
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1 ;
    List<Integer> songpostionlist = new ArrayList<>();
    public static List<List<String>> playlist=new ArrayList<>();
    List<String> singlelist;
    Gson gson = new Gson();
    Gson gson1 = new Gson();
    SharedPreferences.Editor editor;
    public FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.playlist, null);
        floatingActionButton = view.findViewById(R.id.floatingbutton);
        recyclerView = view.findViewById(R.id.playlistrecyclerview);
        singlelist = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        //gson = new Gson();
        json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<List<String>>>() {
        }.getType();
        playlist = gson.fromJson(json, type);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        final playlistadapter playlistadapter=new playlistadapter(getContext(),playlist);
        recyclerView.setAdapter(playlistadapter);
        songname = new String[Song.staticsonglist.size()];/////first comment
        floatingActionButton = view.findViewById(R.id.floatingbutton);
        checkeditem = new boolean[Song.staticsonglist.size()];
        for (int i = 0; i < Song.staticsonglist.size(); i++) {
            songname[i] = Song.staticsonglist.get(i);
        }
        //loadData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
                mbuilder.setTitle("Select the Song");
                mbuilder.setMultiChoiceItems(songname, checkeditem, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!songpostionlist.contains(position)) {
                                songpostionlist.add(position);

                            }

                        }

                    }
                });
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        for (int i = 0; i < songpostionlist.size(); i++) {
                            singlelist.add(Song.staticsonglist.get(songpostionlist.get(i)));
                        }
                        playlist.add(singlelist);
                        for (int i = 0; i < checkeditem.length; i++) {
                            checkeditem[i] = false;
                        }
                        songpostionlist.clear();
                        sharedPreferences1 = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        json1 = gson1.toJson(playlist);
                        editor.putString("task list", json1);
                        editor.apply();
                       // singlelist.clear();//start problem
                        playlistadapter.notifyDataSetChanged();

                    }
                });

                mbuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //songpostionlist.clear();
                        dialog.dismiss();

                    }
                });
                mbuilder.setNeutralButton("CLEAR ALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkeditem.length; i++) {
                            checkeditem[i] = false;

                        }
                        songpostionlist.clear();
                        singlelist.clear();
                    }
                });
                AlertDialog alertDialog = mbuilder.create();
                alertDialog.show();

            }
        });
        playlistadapter.setOnItemClickListener(new playlistadapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, List<String> adpterplaylist, int position) {
            Fragment fragment=new frontplaylistfragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.playlistcontainer,fragment)
                        .commit();
            }
        });
        return view;
    }

}