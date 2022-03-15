package com.example.smart_test;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MyAdapter extends ArrayAdapter<Album> {


    Context context;
    int resource;
    List<Album> albumsList;
    MyAdapter(Context context, int resource, List<Album> albumsList)
    {
        super(context,resource,albumsList);
        this.context = context;
        this.resource = resource;
        this.albumsList = albumsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource,null,false);
        TextView aname = view.findViewById(R.id.albumnamelbl);
        ImageView aimage = view.findViewById(R.id.albumcoverimageview);
        Album album = albumsList.get(position);
        aname.setText(album.getAlbumname());
        Picasso.with(context).load(album.getAlbumcoverurl()).into(aimage);
        return view;
    }
}