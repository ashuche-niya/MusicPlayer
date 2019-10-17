package com.example.firebase.music;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.PorterDuff.Mode.MULTIPLY;
import static com.example.firebase.music.R.color.colorPrimary;

public class frontsongview extends AppCompatActivity implements Serializable {


    Button btnplay, btnplaypause, btnnext, btnprevios;
    RelativeLayout relativeLayout;
    String songname;
    TextView frontartistname, frontsongname;//, notificationsongname, notificationartistname;
    SeekBar seekBar;
    String artistname;
    int position;
    public Thread updateseekbar;
    public ArrayList<songmodel> allsonglist = new ArrayList<>();
    static MediaPlayer mediaPlayer;
    public Handler handler = new Handler();
    public NotificationManagerCompat notificationManager;
    public  RemoteViews remoteViews;
    Context notificationcontext;
    static List<songmodel> playedsonglist= new ArrayList<songmodel>();
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontsongview);
        remoteViews = new RemoteViews(getPackageName(), R.layout.activity_notification);
        notificationManager = NotificationManagerCompat.from(this);
        btnnext = findViewById(R.id.frontnextbtn);
        btnplaypause = findViewById(R.id.frontplaypausebtn);
        btnprevios = findViewById(R.id.frontpreviousbtn);
        seekBar = findViewById(R.id.songseekbar);
        frontsongname = findViewById(R.id.frontsongname);
        frontartistname = findViewById(R.id.frontartistname);
//        notificationsongname = findViewById(R.id.notificationsongname);
//        notificationartistname = findViewById(R.id.notificationsartistname);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        allsonglist = (ArrayList) bundle.getParcelableArrayList("allsonglist");
        songname = intent.getStringExtra("songname");
        artistname = intent.getStringExtra("artistname");
        position = intent.getIntExtra("position", 0);
        frontsongname.setText(songname);
        frontsongname.setSelected(true);
        frontartistname.setText(artistname);
        frontartistname.setSelected(true);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(allsonglist.get(position).getUri()));
        mediaPlayer.start();
//        seekBar.setMax(mediaPlayer.getDuration());
//        updateseekbar = new Thread() {
//            @Override
//            public void run() {
//                if(mediaPlayer!=null) {
//                    int totalduration = mediaPlayer.getDuration();
//                    int currentduration = 0;
//                    while (currentduration < totalduration) {
//                        try {
//                            sleep(50);
//                            currentduration = mediaPlayer.getCurrentPosition();
//                            seekBar.setProgress(currentduration);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }
//        };
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                mediaPlayer.seekTo(seekBar.getProgress());
//            }
//        });
//        updateseekbar.start();
        playedsonglist.add(new songmodel(allsonglist.get(position).getSongname(),allsonglist.get(position).getArtistname()));
        btnplaypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    btnplaypause.setBackgroundResource(R.drawable.ic_play_circle_outline_black_24dp);
                    mediaPlayer.pause();
                } else {
                    btnplaypause.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24dp);
                    mediaPlayer.start();
                }
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notifiacationlayout);
                mediaPlayer.pause();
                position = (position + 1) % (allsonglist.size());
                frontsongname.setText(allsonglist.get(position).getSongname());
                //frontsongname.setSelected(true);
                frontartistname.setText(allsonglist.get(position).getArtistname());
                //frontartistname.setSelected(true);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(allsonglist.get(position).getUri()));
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                playedsonglist.add(new songmodel(allsonglist.get(position).getSongname(),allsonglist.get(position).getArtistname()));
                remoteViews.setTextViewText(R.id.notificationsartistname,allsonglist.get(position).getArtistname());
                remoteViews.setTextViewText(R.id.notificationsongname,allsonglist.get(position).getSongname());
                Notification notification = new NotificationCompat.Builder(getApplicationContext(), App.NOTIFICATIONID)
                        .setSmallIcon(R.drawable.ic_one)
                        .setCustomContentView(remoteViews)
                        .setPriority(NotificationManager.IMPORTANCE_LOW)
                        .setAutoCancel(true)
                        .build();
                notificationManager.notify(1, notification);
            }
        });
        btnprevios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                position = (position - 1);
                if (position == -1) {
                    position = allsonglist.size() - 1;
                }
                frontsongname.setText(allsonglist.get(position).getSongname());
                //frontsongname.setSelected(true);
                frontartistname.setText(allsonglist.get(position).getArtistname());
                //frontartistname.setSelected(true);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(allsonglist.get(position).getUri()));
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                playedsonglist.add(new songmodel(allsonglist.get(position).getSongname(),allsonglist.get(position).getArtistname()));
                remoteViews.setTextViewText(R.id.notificationsartistname,allsonglist.get(position).getArtistname());
                remoteViews.setTextViewText(R.id.notificationsongname,allsonglist.get(position).getSongname());
                Notification notification = new NotificationCompat.Builder(getApplicationContext(), App.NOTIFICATIONID)
                        .setSmallIcon(R.drawable.ic_one)
                        .setCustomContentView(remoteViews)
                        .setPriority(NotificationManager.IMPORTANCE_LOW)
                        .setAutoCancel(true)
                        .build();
                notificationManager.notify(1, notification);
            }
        });
        remoteViews.setTextViewText(R.id.notificationsartistname,allsonglist.get(position).getArtistname());
        remoteViews.setTextViewText(R.id.notificationsongname,allsonglist.get(position).getSongname());
        Notification notification = new NotificationCompat.Builder(this, App.NOTIFICATIONID)
                .setSmallIcon(R.drawable.ic_one)
                .setCustomContentView(remoteViews)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setPriority(NotificationManager.IMPORTANCE_LOW)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
        seekBar.setMax(mediaPlayer.getDuration());
        updateseekbar = new Thread() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int totalduration = mediaPlayer.getDuration();
                    int currentduration = 0;
                    while (currentduration < totalduration) {
                        try {
                            sleep(300);
                            if (mediaPlayer != null && seekBar != null) {
                                currentduration = mediaPlayer.getCurrentPosition();

                                seekBar.setProgress(currentduration);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        };
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        updateseekbar.start();
//        Runnable runnable =new Runnable() {//first ways to implement
//            @Override
//            public void run() {
//                try {
//                    if(mediaPlayer!=null)
//                    {
//                        mediaPlayer.stop();
//                        mediaPlayer.reset();
//                        mediaPlayer.release();
//                        mediaPlayer=null;
//                    }
//                    mediaPlayer=new MediaPlayer();
//                    mediaPlayer.setDataSource(allsonglist.get(position).getUri());
//                    mediaPlayer.prepareAsync();
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(final MediaPlayer mp) {
//                            mp.start();
//                            seekBar.setProgress(0);
////                    updateseekbar = new Thread() {
////                        @Override
////                        public void run() {
////                            int totalduration = mediaPlayer.getDuration();
////                            int currentduration = 0;
////                            while (currentduration < totalduration) {
////                                try {
////                                    sleep(50);
////                                    currentduration = mediaPlayer.getCurrentPosition();
////                                    seekBar.setProgress(currentduration);
////                                } catch (InterruptedException e) {
////                                    e.printStackTrace();
////                                }
////                            }
////
////                        }
////                    };
//                            seekBar.setMax(mp.getDuration());
//                            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                                @Override
//                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                                }
//
//                                @Override
//                                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                                }
//
//                                @Override
//                                public void onStopTrackingTouch(SeekBar seekBar) {
//                                    mp.seekTo(seekBar.getProgress());
//                                }
//                            });
//
//                        }
//                    });
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        };
//        handler.postDelayed(runnable,25);
//        Thread t=new Muthread();
//        t.start();//up to here firtst ways
//        try {
//            if(mediaPlayer!=null)
//            {
//                mediaPlayer.stop();
//                mediaPlayer.reset();
//                mediaPlayer.release();
//                mediaPlayer=null;
//            }
//            mediaPlayer=new MediaPlayer();
//            mediaPlayer.setDataSource(allsonglist.get(position).getUri());
//            mediaPlayer.prepareAsync();
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(final MediaPlayer mp) {
//                    mp.start();
//
////                    updateseekbar = new Thread() {
////                        @Override
////                        public void run() {
////                            int totalduration = mediaPlayer.getDuration();
////                            int currentduration = 0;
////                            while (currentduration < totalduration) {
////                                try {
////                                    sleep(50);
////                                    currentduration = mediaPlayer.getCurrentPosition();
////                                    seekBar.setProgress(currentduration);
////                                } catch (InterruptedException e) {
////                                    e.printStackTrace();
////                                }
////                            }
////
////                        }
////                    };
//                    seekBar.setMax(mediaPlayer.getDuration());
//                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                        @Override
//                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                        }
//
//                        @Override
//                        public void onStartTrackingTouch(SeekBar seekBar) {
//
//                        }
//
//                        @Override
//                        public void onStopTrackingTouch(SeekBar seekBar) {
//                            mediaPlayer.seekTo(seekBar.getProgress());
//                        }
//                    });
//
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        updateseekbar = new Thread() {//last commetn
//            @Override
//            public void run() {
//                int totalduration = mediaPlayer.getDuration();
//                int currentduration = 0;
//                while (currentduration < totalduration) {
//                    try {
//                        sleep(1000);
//                        currentduration = mediaPlayer.getCurrentPosition();
//                        seekBar.setProgress(currentduration);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        };
//        updateseekbar.start();//upto hare
    }
//    public class Muthread extends Thread{//first ways thread
//        @Override
//            public void run() {
//            if (mediaPlayer != null) {
//                int totalduration = mediaPlayer.getDuration();
//                int currentduration = 0;
//                while (currentduration < totalduration) {
//                    try {
//                        sleep(100);
//                        currentduration = mediaPlayer.getCurrentPosition();
//                        seekBar.setProgress(currentduration);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }
//
//        }//upto here thread firsg waysbtn

    public ArrayList<songmodel> getAllsonglist() {
        return allsonglist;
    }

    public int getPosition() {
        return position;
    }
}
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        allsonglist = (ArrayList) bundle.getParcelableArrayList("allsonglist");
//        songname = intent.getStringExtra("songname");
//        artistname = intent.getStringExtra("artistname");
//        position = intent.getIntExtra("position", 0);
//        frontsongname.setText(songname);
//        frontsongname.setSelected(true);
//        frontartistname.setText(artistname);
//        frontartistname.setSelected(true);
//        updateseekbar = new Thread() {
//            @Override
//            public void run() {
//                int totalduration = mediaPlayer.getDuration();
//                int currentduration = 0;
//                while (currentduration < totalduration) {
//                    try {
//                        sleep(50);
//                        currentduration = mediaPlayer.getCurrentPosition();
//                        seekBar.setProgress(currentduration);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        };
        /*if (mediaPlayer != null) ;
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        allsonglist = (ArrayList) bundle.getParcelableArrayList("allsonglist");
        songname = intent.getStringExtra("songname");
        artistname = intent.getStringExtra("artistname");
        position = intent.getIntExtra("position", 0);
        frontsongname.setText(songname);
        frontsongname.setSelected(true);
        frontartistname.setText(artistname);
        frontartistname.setSelected(true);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(allsonglist.get(position).getUri()));
        mediaPlayer.start();
        updateseekbar = new Thread() {
            @Override
            public void run() {
                int totalduration = mediaPlayer.getDuration();
                int currentduration = 0;
                while (currentduration < totalduration) {
                    try {
                        sleep(50);
                        currentduration = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentduration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });*///here is the last commenting
//        try{
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setDataSource(allsonglist.get(position).getUri());
//            mediaPlayer.prepareAsync();
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(final MediaPlayer mp) {
//                    mp.start();
//                    seekBar.setMax(mp.getDuration());
//                    updateseekbar = new Thread() {
//                        @Override
//                        public void run() {
//                            int totalduration = mp.getDuration();
//                            int currentduration = 0;
//                            while (currentduration < totalduration) {
//                                try {
//                                    sleep(50);
//                                    currentduration = mp.getCurrentPosition();
//                                    seekBar.setProgress(currentduration);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }
//                    };
//                    updateseekbar.start();
////                seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
////                seekBar.getThumb().setColorFilter(getResources().getColor(colorPrimary), PorterDuff.Mode.SRC_IN);
//
//                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                        @Override
//                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                        }
//
//                        @Override
//                        public void onStartTrackingTouch(SeekBar seekBar) {
//
//                        }
//
//                        @Override
//                        public void onStopTrackingTouch(SeekBar seekBar) {
//                            mediaPlayer.seekTo(seekBar.getProgress());
//                        }
//                    });
//
//                    btnplaypause.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (mediaPlayer.isPlaying()) {
//                                btnplaypause.setBackgroundResource(R.drawable.ic_play_circle_outline_black_24dp);
//                                mediaPlayer.pause();
//                            } else {
//                                btnplaypause.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24dp);
//                                mediaPlayer.start();
//                            }
//                        }
//                    });
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//mediaPlayer=MediaPlayer.create(getApplicationContext(), Uri.parse(allsonglist.get(position).getUri()));