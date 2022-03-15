package com.example.smart_test;

public class Album {
    public String albumname;
    public String albumcoverurl;


    public  Album(String albumname, String albumcoverurl)
    {
        this.albumname = albumname;
        this.albumcoverurl = albumcoverurl;
    }

    public String getAlbumcoverurl() {
        return albumcoverurl;
    }

    public String getAlbumname() {
        return albumname;
    }
}

