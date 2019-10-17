package com.example.firebase.music;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class playlistadapter extends RecyclerView.Adapter<playlistadapter.playlistviewholder> {
    OnItemClickListener onItemClickListener;
    Context context;
    List<List<String>> playlist;
    RelativeLayout relativeLayout;
    public interface  OnItemClickListener {
        void onItemClick( View v, List<String> adpterplaylist, int position);
    }
    public void setOnItemClickListener(playlistadapter.OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }
    public playlistadapter(Context context, List<List<String>> playlist) {
        this.context = context;
        this.playlist = playlist;
    }


    @NonNull
    @Override
    public playlistviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.playlistlayout,viewGroup,false);
        return new playlistviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull playlistviewholder playlistviewholder, final int i) {

            String name = "Myplaylist " + (i+1);
            playlistviewholder.playlistname.setText(name);

    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }


    public class playlistviewholder extends RecyclerView.ViewHolder {
        TextView playlistname;

        public playlistviewholder(@NonNull View itemView) {
            super(itemView);
            playlistname =itemView.findViewById(R.id.playlistname);
            relativeLayout=itemView.findViewById(R.id.playlistrelativelayout);
        }

    }

}
