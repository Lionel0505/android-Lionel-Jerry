package com.example.zanakay;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zanakay.vue.ArticleFiche;
import com.example.zanakay.vue.ListeArticleApprentissage;
import com.example.zanakay.vue.Login;
import com.example.zanakay.vue.Menu;
import com.example.zanakay.vue.VideoView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListeVideo extends AppCompatActivity {

    ListView listvideos;
    BottomNavigationView chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_video);
        listvideos = findViewById(R.id.listVideo);

        chipNavigationBar = findViewById(R.id.bottom_nav);
        bottomMenu();

       /* for(int j = 0; j < titres.length; j++){
            Video video = new Video(titres[j],titres[j]);
            videoArrayList.add(video);
        }*/

        Intent intent = this.getIntent();
        if(intent != null){

            String cat = intent.getStringExtra("categorie");
            String content_type = intent.getStringExtra("content_type");
            System.out.println("///////////////////////////"+cat);

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = getString(R.string.url) + "contents?content_category=" + cat + "&content_type=" + content_type;
            System.out.println("---------->"+url);

            //token header
            SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            String token = sharedpreferences.getString("tokenKey", null);
            System.out.println("TOKEEEN==="+token);
            ArrayList<Article> articles = new ArrayList();

            if(token == null){
                Toast.makeText(ListeVideo.this,"Non connecté", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ListeVideo.this, Login.class);
                startActivity(i);
            }

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("=========================+++++++"+response);
                            String[] titres = null;
                            Article temp = null;
                            JSONObject myjson = null;
                            ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
                            try {
                                myjson = response;
                                JSONArray the_json_array = myjson.getJSONArray("data");
                                int size = the_json_array.length();
                                for (int i = 0; i < size; i++) {
                                    JSONObject another_json_object = the_json_array.getJSONObject(i);
                                    arrays.add(another_json_object);
                                }
                                JSONObject[] jsons = new JSONObject[arrays.size()];
                                arrays.toArray(jsons);

                                titres = new String[arrays.size()];
                                for(int j = 0; j < arrays.size(); j++){
                                    titres[j] = arrays.get(j).getString("title");
                                    temp = new Article(arrays.get(j).getInt("id"), arrays.get(j).getString("title"), arrays.get(j).getString("description"), arrays.get(j).getString("path"));
                                    articles.add(temp);
                                    System.out.println("VALUES===="+arrays.get(j).getString("title"));
                                }
                                System.out.println("TAAAILLE==="+titres.length);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            ListeVideoAdapter listeVideoAdapter = new ListeVideoAdapter(ListeVideo.this,articles);
                            listvideos.setAdapter(listeVideoAdapter);

                            listvideos.setClickable(true);
                            listvideos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent i = new Intent(ListeVideo.this, VideoView.class);
                                    //WS
                                    i.putExtra("id",articles.get(position).getId());
                                    i.putExtra("titre",articles.get(position).getTitre());
                                    i.putExtra("description",articles.get(position).getDescription());
                                    i.putExtra("link",articles.get(position).getPath());
                                    startActivity(i);
                                    startActivity(i);
                                }
                            });
                            // Display the first 500 characters of the response string.
                            //textView.setText("Response is: " + response.substring(0,500));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //textView.setText("That didn't work!");
                    System.out.println("=========================ERRREEERA = "+error.toString());
                }
            }){

                //This is for Headers If You Needed
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    String authValue = "Bearer " + token;
                    System.out.println("////////////////////////////////// token " + token);
                    params.put("Authorization", authValue);
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    return params;
                }

            };
            queue.add(stringRequest);
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
                        c = Menu.class;
                }
                Intent intent = new Intent(ListeVideo.this,c);
                startActivity(intent);
                return false;
            }
        });

    }
}