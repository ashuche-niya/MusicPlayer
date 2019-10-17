package com.example.firebase.music;
//import com.example.firebase.music.Playedsong;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;
import com.example.firebase.music.frontsongview;

public class songadapter extends RecyclerView.Adapter<songadapter.songviewholder> {

    @NonNull

    Context ctx;
    List<songmodel> songmodels;
    LinearLayout linearLayout;
    MediaPlayer mediaPlayer;
    public ImageView songimage;
    public TextView textView1;
    public TextView textView2;
    public Intent intent;
    OnItemClickListener onItemClickListener;
    public interface  OnItemClickListener {
        void onItemClick(LinearLayout linearLayout,View v,songmodel smod,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }
    public songadapter(@NonNull Context ctx, List<songmodel> songmodels) {
        this.ctx = ctx;
        this.songmodels = songmodels;
    }


    public class songviewholder extends RecyclerView.ViewHolder
    {
        public TextView songname;
        public TextView artistname;
        public songviewholder(@NonNull View itemView) {

            super(itemView);
            songimage =itemView.findViewById(R.id.songimage);
            songname =itemView.findViewById(R.id.songname);
            artistname =itemView.findViewById(R.id.artistname);
            linearLayout=itemView.findViewById(R.id.songlayout);
            textView1 = itemView.findViewById(R.id.frontsongname);
            textView2 = itemView.findViewById(R.id.frontartistname);


        }
    }
    public songadapter.songviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(viewGroup.getContext());
        View view =inflater.inflate(R.layout.songrow,viewGroup,false);
        return new songviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final songadapter.songviewholder songviewholder, final int i) {
        final String songname=songmodels.get(i).getSongname();
        final String artistname=songmodels.get(i).getArtistname();
        int imageid = songmodels.get(i).getImageid();
        final String uri=songmodels.get(i).getUri();
        songviewholder.songname.setText(songname);


        //songviewholder.songimage.setImageResource(imageid);
        songviewholder.artistname.setText(artistname);
//        songimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textView1.setText(songname);
//                textView2.setText(artistname);
//                intent=new Intent(v.getContext(), frontsongview.class);
//                v.getContext().startActivity(intent);
//
//            }
//        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)
                {
                    onItemClickListener.onItemClick(linearLayout,v,songmodels.get(i),i);
                }
            }
        });
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    if(mediaPlayer!=null)
//                    {
//                        mediaPlayer.stop();
//                        mediaPlayer.reset();
//                        mediaPlayer.release();
//                        mediaPlayer=null;
//                    }
//                    mediaPlayer=new MediaPlayer();
//                    mediaPlayer.setDataSource(uri);
//                    mediaPlayer.prepareAsync();
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mp) {
//                            mp.start();
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return songmodels.size();
    }
}
