package com.example.firebase.music;

import java.util.List;

public class playlistmodel {
    public List<String> playlist;

    public playlistmodel(List<String> playlist) {
        this.playlist = playlist;
    }

    public List<String> getPlaylist() {
        return playlist;
    }
}
