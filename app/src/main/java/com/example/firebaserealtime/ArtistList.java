package com.example.firebaserealtime;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ArtistList extends ArrayAdapter<Artist> {

    private Activity context;
    private List<Artist> artistList;

    public ArtistList(Activity context, List<Artist> artistList){

        super(context, R.layout.list_layout, artistList);
        this.context = context;
        this.artistList = artistList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView txtVName = (TextView) listViewItem.findViewById(R.id.txtVName);
        TextView txtVGenre = (TextView) listViewItem.findViewById(R.id.txtVGenre);

        Artist artist = artistList.get(position);

        txtVName.setText(artist.getArtistName());
        txtVGenre.setText(artist.getArtistGenre());

        return listViewItem;
    }
}
