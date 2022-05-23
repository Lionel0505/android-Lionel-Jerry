package com.example.zanakay.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zanakay.CatApprentissage;
import com.example.zanakay.CatSante;
import com.example.zanakay.ListCatAdapter;
import com.example.zanakay.ListCatSanteAdapter;
import com.example.zanakay.ListeVideo;
import com.example.zanakay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class CategorieSante extends AppCompatActivity {

    ListView listcats;
    BottomNavigationView chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie_sante);

        listcats = findViewById(R.id.listCatSante);
        chipNavigationBar = findViewById(R.id.bottom_nav);
        bottomMenu();

        int[] imageId = {R.drawable.texte,R.drawable.video};
        String[] cat = {"Articles sante","Videos"};
        String[] descri = {"Lisez des articles pertinents illustres.","Video pour mieux comprendre."};
        //mivadika liste WS articles

        ArrayList<CatSante> catSantes = new ArrayList<>();

        for(int j = 0; j < imageId.length; j++){
            CatSante catSante = new CatSante(cat[j], descri[j], imageId[j]);
            catSantes.add(catSante);
        }

        ListCatSanteAdapter listCatSanteAdapter = new ListCatSanteAdapter(CategorieSante.this,catSantes);
        listcats.setAdapter(listCatSanteAdapter);

        Class[] classes = {ListeArticleApprentissage.class, ListeVideo.class};
        String[] content_type = {"TXT","VID"};
        listcats.setClickable(true);
        listcats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CategorieSante.this,classes[position]);
                /*parametre cat*/
                intent.putExtra("categorie","1");
                intent.putExtra("content_type",content_type[position]);
                startActivity(intent);
            }
        });
    }

    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Class c = null;
                switch (item.getItemId()) {
                    case R.id.action_menu:
                        c = Menu.class;
                    case R.id.action_deco:
                        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                }
                Intent intent = new Intent(CategorieSante.this,c);
                startActivity(intent);
                return false;
            }
        });

    }
}