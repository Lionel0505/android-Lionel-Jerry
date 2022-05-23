package com.example.zanakay.vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zanakay.Article;
import com.example.zanakay.ListeArticleAdapter;
import com.example.zanakay.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListeArticleApprentissage extends AppCompatActivity {

    ListView listarticles;
    BottomNavigationView chipNavigationBar;
    boolean favori = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_article);
        listarticles = findViewById(R.id.listArticle);

        chipNavigationBar = findViewById(R.id.bottom_nav);
        bottomMenu();

        Intent intent = this.getIntent();
        if(intent != null){

            String cat = intent.getStringExtra("categorie");
            String content_type = intent.getStringExtra("content_type");
            boolean favoris = intent.getBooleanExtra("favori",false);
            System.out.println("///////////////////////////"+cat);

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "";
            if(content_type != null) {
                System.out.println("///////////////////////////"+cat);
                url = getString(R.string.url) + "contents?content_category=" + cat + "&content_type=" + content_type;
            }
            else if(favoris) {
                url = getString(R.string.url) + "favorites";
                favori = true;
            }
            else
                url = getString(R.string.url)+"contents/search?filter="+intent.getStringExtra("motcle");
            System.out.println("---------->"+url);

            //token header
            SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            String token = sharedpreferences.getString("tokenKey", null);
            System.out.println("TOKEEEN==="+token);
            ArrayList<Article> articles = new ArrayList();

            if(token == null){
                Toast.makeText(ListeArticleApprentissage.this,"Non connecté", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ListeArticleApprentissage.this, Login.class);
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
                                    if(favori) {
                                        System.out.println("566666666666666666666666" + arrays.get(j).getString("content"));
                                        ObjectMapper mapper = new ObjectMapper();
                                        Map<String,Object> map = mapper.readValue(arrays.get(j).getString("content"), Map.class);
                                        temp = new Article((Integer)map.get("id"), (String)map.get("title"), (String)map.get("description"), (String)map.get("path"));
                                    }
                                    else
                                        temp = new Article(arrays.get(j).getInt("id"), arrays.get(j).getString("title"), arrays.get(j).getString("description"), arrays.get(j).getString("path"));
                                    articles.add(temp);
                                    System.out.println("VALUES===="+arrays.get(j).getString("title"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            ListeArticleAdapter listeArticleAdapter = new ListeArticleAdapter(ListeArticleApprentissage.this,articles);
                            listarticles.setAdapter(listeArticleAdapter);

                            listarticles.setClickable(true);
                            listarticles.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent i = new Intent(ListeArticleApprentissage.this, ArticleFiche.class);
                                    i.putExtra("id",articles.get(position).getId());
                                    i.putExtra("titre",articles.get(position).getTitre());
                                    i.putExtra("description",articles.get(position).getDescription());
                                    i.putExtra("link",articles.get(position).getPath());
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
                        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        c = Login.class;
                }
                Intent intent = new Intent(ListeArticleApprentissage.this,c);
                startActivity(intent);
                return false;
            }
        });

    }
}