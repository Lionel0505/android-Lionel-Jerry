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

public class ListCatSanteAdapter extends ArrayAdapter<CatSante> {

    public ListCatSanteAdapter(Context context, ArrayList<CatSante> catSantes){
        super(context,R.layout.list_cat_sante,catSantes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CatSante cat = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_cat_sante,parent,false);
        }

        ImageView imageview = convertView.findViewById(R.id.cat_image_sante);
        TextView catname = convertView.findViewById(R.id.categorie_sante);
        TextView descri = convertView.findViewById(R.id.descri_sante);

        imageview.setImageResource(cat.imageId);
        catname.setText(cat.label);
        descri.setText(cat.description);

        return convertView;
    }
}
