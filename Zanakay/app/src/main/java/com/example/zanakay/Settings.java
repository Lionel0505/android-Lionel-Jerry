package com.example.zanakay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private RadioGroup radioGroup;
    private TextView themeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroup = findViewById(R.id.idRGgroup);
        themeTV = findViewById(R.id.idtvTheme);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // on radio button check change
                switch (checkedId) {
                    case R.id.idRBLight:
                        // on below line we are checking the radio button with id.
                        // on below line we are setting the text to text view as light mode.
                        themeTV.setText("Light Theme");
                        // on below line we are changing the theme to light mode.
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case R.id.idRBDark:
                        // this method is called when dark radio button is selected
                        // on below line we are setting dark theme text to our text view.
                        themeTV.setText("Dark Theme");
                        // on below line we are changing the theme to dark mode.
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                }
            }
        });
    }
}