package com.example.firebase.music;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView =findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadfragment(new Song());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            case R.id.songs:
                loadfragment(new Song());
                break;
            case R.id.playlist:
                loadfragment(new Playlist());
                break;
            case R.id.playedsongs:
                loadfragment(new Playedsong());
                break;
        }
        return false;
    }
    public  boolean loadfragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentcontainer,fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
