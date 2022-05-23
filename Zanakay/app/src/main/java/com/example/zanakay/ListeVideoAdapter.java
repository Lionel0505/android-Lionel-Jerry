package com.example.zanakay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListeVideoAdapter extends ArrayAdapter<Article> {

    public ListeVideoAdapter(Context context, ArrayList<Article> videos){

        super(context,R.layout.list_videos,videos);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Article video = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_videos,parent,false);
        }

        TextView titre = convertView.findViewById(R.id.titre_video);

        titre.setText(video.titre);

        return convertView;
    }
}
