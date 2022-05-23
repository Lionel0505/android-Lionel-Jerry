package com.example.zanakay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListeArticleAdapter extends ArrayAdapter<Article> {

    public ListeArticleAdapter(Context context, ArrayList<Article> articles){

        super(context,R.layout.list_articles,articles);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Article article = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_articles,parent,false);
        }

        TextView titre = convertView.findViewById(R.id.titre);


        titre.setText(article.titre);



        return convertView;
    }
}
