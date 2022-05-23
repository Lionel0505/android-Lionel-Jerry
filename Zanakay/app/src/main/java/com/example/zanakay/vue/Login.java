package com.example.zanakay.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zanakay.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button callSignUp;
    Button callLogin;
    ImageView image;
    TextInputLayout username,password;
    TextView erreur;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Token = "tokenKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.signup_screen);
        callLogin = findViewById(R.id.login_button);
        image = findViewById(R.id.logo_image);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        callSignUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(Login.this,SignUp.class);

                //Pair[] pairs = new Pair[1];
                //pairs[0] = new Pair<View,String>(image, "logo_image");

                //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);

                startActivity(intent);
                finish();
            }
        });

        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erreur = findViewById(R.id.erreur_login);
                if(TextUtils.isEmpty(username.getEditText().getText()) || TextUtils.isEmpty(password.getEditText().getText())){
                    erreur.setText("Champs obligatoires.");
                }
                String name = String.valueOf(username.getEditText().getText());
                String pwd = String.valueOf(password.getEditText().getText());

                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String URL = getString(R.string.url)+"auth/sign_in";
                    JSONObject jsonBody = new JSONObject();
                    System.out.println("LOGIIIN===="+name);
                    System.out.println("MOTDEPASSE===="+pwd);
                    jsonBody.put("username", name);
                    jsonBody.put("password", pwd);
                    System.out.println("JSONBODYYY===="+URL);
                    final String requestBody = jsonBody.toString();
                    System.out.println("JSONBODYYY===="+requestBody);


                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    System.out.println("MOTDEPASSE===="+response);
                                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    ObjectMapper mapper = new ObjectMapper();
                                    Map<String,Object> map = null;
                                    try {
                                        map = mapper.readValue(response.toString(), Map.class);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    editor.putString(Token, (String) map.get("data"));
                                    editor.commit();
                                    Intent intent = new Intent(Login.this, Menu.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    erreur.setText("Login ou mot de passe incorrecte.");
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
                 };

                    requestQueue.add(jsonObjectRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}