package com.example.zanakay.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zanakay.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class VideoView extends AppCompatActivity {

    WebView webView;
    BottomNavigationView chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        chipNavigationBar = findViewById(R.id.bottom_nav);
        bottomMenu();

        /*webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.youtube.com/watch?v=nGYUGj8UkUA");*/
        Intent intent = this.getIntent();
        if(intent != null){
            String url = intent.getStringExtra("link");

            String frameVideo = "<html>\n" +
                    "\t<body>\n" +
                    "\t\t<iframe width=\\\"100%\\\" height=\\\"100%\\\" src=\\\""+url+"\\\" frameborder=\\\"0\\\" allowfullscreen>\n" +
                    "\t\t</iframe>\n" +
                    "\t</body>\n" +
                    "</html>\n" +
                    "\n" +
                    "<!DOCTYPE html>\n" +
                    "<html lang='en'>\n" +
                    "<head>\n" +
                    "    <meta charset='UTF-8'>\n" +
                    "    <title>Title</title>\n" +
                    "    <style>\n" +
                    "\t\t.videoWrapper {\n" +
                    "\t\t  position: relative;\n" +
                    "\t\t  padding-bottom: 56.25%;\n" +
                    "\t\t  /* 16:9 */\n" +
                    "\t\t  padding-top: 25px;\n" +
                    "\t\t  height: 0;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.videoWrapper iframe {\n" +
                    "\t\t  position: absolute;\n" +
                    "\t\t  top: 0;\n" +
                    "\t\t  left: 0;\n" +
                    "\t\t  width: 100%;\n" +
                    "\t\t  height: 100%;\n" +
                    "\t\t}\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class=\"videoWrapper\">\n" +
                    "\t<iframe width='100%' height='100%' src='"+url+"'\n" +
                    "\t\t\tallowfullscreen>\n" +
                    "\t</iframe>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";

            WebView displayYoutubeVideo = (WebView) findViewById(R.id.webview);
            displayYoutubeVideo.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            WebSettings webSettings = displayYoutubeVideo.getSettings();
            webSettings.setJavaScriptEnabled(true);
            displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
        }
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
                Intent intent = new Intent(VideoView.this,c);
                startActivity(intent);
                return false;
            }
        });

    }
}