package com.example.zanakay.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.zanakay.JeuxFragment;
import com.example.zanakay.R;
import com.example.zanakay.Settings;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class Menu extends AppCompatActivity {

    CircleMenu circleMenu;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        circleMenu = findViewById(R.id.circle_menu);
        constraintLayout = findViewById(R.id.constraint_layout);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.icon_menu, R.mipmap.icon_cancel)
        .addSubMenu(Color.parseColor("#88bef5"), R.mipmap.sante)
        .addSubMenu(Color.parseColor("#ff8a5c"), R.mipmap.apprentissage)
        .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.icon_notify)
        .addSubMenu(Color.parseColor("#83e85a"), R.mipmap.icon_setting)
        .addSubMenu(Color.parseColor("#FFEE48"), R.mipmap.icon_search)
        .addSubMenu(Color.parseColor("#3F0FFF"), R.mipmap.favoris)
        .addSubMenu(Color.parseColor("#ba53de"), R.mipmap.icon_jeux)
        .setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int index) {
                switch (index){
                    case 0:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Menu.this,"Sante", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#A8DFFF"));
                                Intent intent = new Intent(Menu.this,CategorieSante.class);
                                startActivity(intent);
                            }
                        },1000);
                        break;
                    case 1:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Menu.this,"Apprendre", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#FFDBCE"));
                                Intent intent = new Intent(Menu.this, CategorieApprentissage.class);
                                startActivity(intent);
                            }
                        },1000);
                        break;
                    case 2:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Menu.this,"Notification", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#FF9E9D"));
                                Intent intent = new Intent(Menu.this,FragmentActivity.class);
                                startActivity(intent);
                                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new JeuxFragment()).commit();*/
                            }
                        },1000);
                        break;
                    case 3:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Menu.this,"Parametre", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#96f7d2"));
                                Intent intent = new Intent(Menu.this, Settings.class);
                                startActivity(intent);
                            }
                        },1000);
                        break;
                    case 4:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Menu.this,"Recherche", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#fff591"));
                                Intent intent = new Intent(Menu.this,Recherche.class);
                                startActivity(intent);
                            }
                        },1000);
                        break;
                    case 5:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Menu.this,"Favoris", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#BCABFF"));
                                Intent intent = new Intent(Menu.this,ListeArticleApprentissage.class);
                                intent.putExtra("favori",true);
                                startActivity(intent);
                            }
                        },1000);
                        break;
                    case 6:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Menu.this,"Se divertir", Toast.LENGTH_SHORT).show();
                                constraintLayout.setBackgroundColor(Color.parseColor("#d3cde6"));
                                Intent intent = new Intent(Menu.this,FragmentActivity.class);
                                startActivity(intent);
                            }
                        },1000);
                        break;
                }
            }
        })
        ;
    }
}