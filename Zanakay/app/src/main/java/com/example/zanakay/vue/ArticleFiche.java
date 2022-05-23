package com.example.zanakay.vue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zanakay.Article;
import com.example.zanakay.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zanakay.databinding.ActivityArticleFicheBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ArticleFiche extends AppCompatActivity {

    private ActivityArticleFicheBinding binding;
    Button callFavori;
    int id;
    SharedPreferences sharedpreferences;
    ImageView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityArticleFicheBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setContentView(R.layout.activity_article_fiche);

        callFavori = findViewById(R.id.favoris);
        TextView titre = findViewById(R.id.titre_fiche);
        banner = findViewById(R.id.banner);
        Intent intent = this.getIntent();

        if(intent != null){
            String title = intent.getStringExtra("titre");
            id = intent.getIntExtra("id",1);
            titre.setText(title);
            TextView para = findViewById(R.id.paragraphe);
            para.setText(intent.getStringExtra("description"));
            int id = getResources().getIdentifier(intent.getStringExtra("link"), "drawable", getPackageName());
            banner.setImageResource(id);
        }

        callFavori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //miantso WS

                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String URL = getString(R.string.url)+"favorites";
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("favorite_id", id);
                    System.out.println("JSONBODYYY===="+URL);
                    final String requestBody = jsonBody.toString();
                    System.out.println("JSONBODYYY===="+requestBody);


                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    System.out.println("MOTDEPASSE===="+response);
                                    sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);

                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    ObjectMapper mapper = new ObjectMapper();
                                    Map<String,Object> map = null;
                                    try {
                                        map = mapper.readValue(response.toString(), Map.class);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Log.e("ERREEEER",error.toString());
                                }
                            }){
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                            String token = sharedpreferences.getString("tokenKey", null);
                            String authValue = "Bearer " + token;
                            System.out.println("////////////////////////////////// token " + token);
                            params.put("Authorization", authValue);
                            params.put("Content-Type", "application/json; charset=UTF-8");
                            return params;
                        }
                    };

                    requestQueue.add(jsonObjectRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}