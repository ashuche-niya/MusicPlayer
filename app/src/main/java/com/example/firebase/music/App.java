package com.example.firebase.music;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String NOTIFICATIONID = "notification_id";
    public void onCreate() {
        super.onCreate();
        createnotificationid();
    }
    public void createnotificationid()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("notification_id","songnotification", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("this is my miusic player app notification");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
