package com.example.zanakay.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zanakay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

public class Recherche extends AppCompatActivity {

    Button callSearch;
    TextInputLayout motcle;
    String mc;
    BottomNavigationView chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        chipNavigationBar = findViewById(R.id.bottom_nav);
        bottomMenu();

        callSearch = findViewById(R.id.search_button);
        motcle = findViewById(R.id.motcle);

        callSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recherche.this,ListeArticleApprentissage.class);
                mc = String.valueOf(motcle.getEditText().getText());
                intent.putExtra("motcle",mc);
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
                Intent intent = new Intent(Recherche.this,c);
                startActivity(intent);
                return false;
            }
        });

    }
}