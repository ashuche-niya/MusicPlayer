package com.example.firebase.music;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class playedsongadapter extends RecyclerView.Adapter<playedsongadapter.playedsongviewholder> {
    Context context;
    //List<songmodel> songmodelList;
    List<songmodel> playedsonglist;
    LinearLayout linearLayout;
//    OnItemClickListener onItemClickListener;
//    public interface  OnItemClickListener {
//        void onItemClick(LinearLayout linearLayout, View v, songmodel smod, int position);
//    }
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
//    {
//        this.onItemClickListener=onItemClickListener;
//    }
    public playedsongadapter(Context context, List<songmodel> playesonglist) {
        this.context = context;
        this.playedsonglist = playesonglist;
    }

    public class playedsongviewholder extends RecyclerView.ViewHolder {
        TextView songname;
        TextView artistname;


        public playedsongviewholder(@NonNull View itemView) {
            super(itemView);
            songname=itemView.findViewById(R.id.songname);
            artistname=itemView.findViewById(R.id.artistname);
            //linearLayout=itemView.findViewById(R.id.songlayout);
        }
    }

    @NonNull
    @Override
    public playedsongadapter.playedsongviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.songrow,viewGroup,false);
        return new playedsongviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull playedsongadapter.playedsongviewholder playedsongviewholder, final int i) {
        String playedsongname=playedsonglist.get(i).getSongname();

        String playedartistname=playedsonglist.get(i).getArtistname();
        playedsongviewholder.songname.setText(playedsongname);
        playedsongviewholder.artistname.setText(playedartistname);
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onItemClickListener!=null)
//                {
//                    onItemClickListener.onItemClick(linearLayout,v,playedsonglist.get(i),i);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return playedsonglist.size();
    }
}
