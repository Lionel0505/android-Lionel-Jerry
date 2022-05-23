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

public class ListCatAdapter extends ArrayAdapter<CatApprentissage> {

    public ListCatAdapter(Context context, ArrayList<CatApprentissage> catApprentissages){
        super(context,R.layout.list_cat,catApprentissages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CatApprentissage cat = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_cat,parent,false);
        }

        ImageView imageview = convertView.findViewById(R.id.cat_image);
        TextView catname = convertView.findViewById(R.id.categorie);
        TextView descri = convertView.findViewById(R.id.descri);

        imageview.setImageResource(cat.imageId);
        catname.setText(cat.label);
        descri.setText(cat.description);

        return convertView;
    }
}
