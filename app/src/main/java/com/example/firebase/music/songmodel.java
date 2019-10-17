package com.example.firebase.music;

import java.io.Serializable;

public class songmodel implements Serializable {
    String songname;
    String artistname;
    int imageid;

    public songmodel(String songname, String artistname, String uri) {
        this.songname = songname;
        this.artistname = artistname;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    String uri;

    public songmodel(String songname, String artistname, int imageid) {
        this.songname = songname;
        this.artistname = artistname;
        this.imageid = imageid;
    }

    public songmodel(String songname, String artistname) {
        this.songname = songname;
        this.artistname = artistname;
    }

    public String getSongname() {
        return songname;
    }

    public String getArtistname() {
        return artistname;
    }

    public int getImageid() {
        return imageid;
    }
}
