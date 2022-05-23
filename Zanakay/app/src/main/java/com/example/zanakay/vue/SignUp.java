package com.example.zanakay.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zanakay.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    Button callLogIn;
    Button callSignUp;
    TextInputLayout password,age,login;
    TextView erreur;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        callLogIn = findViewById(R.id.login_screen);
        callSignUp = findViewById(R.id.signup);
        password = findViewById(R.id.password);
        age = findViewById(R.id.age);
        login = findViewById(R.id.login);

        callLogIn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });

        callSignUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                boolean signedup = true; //tokony false
                erreur = findViewById(R.id.erreur_signup);
                if(TextUtils.isEmpty(password.getEditText().getText()) || TextUtils.isEmpty(login.getEditText().getText()) || TextUtils.isEmpty(age.getEditText().getText())){
                    erreur.setText("Champs obligatoires.");
                }
                else {
                    String pwd = String.valueOf(password.getEditText().getText());
                    int ages = Integer.parseInt(String.valueOf((age.getEditText().getText())));
                    String log = String.valueOf(login.getEditText().getText());
                    //WS

                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        String URL = getString(R.string.url)+"users/sign_up";
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("username", log);
                        jsonBody.put("password", pwd);
                        jsonBody.put("child_category", ages);
                        final String requestBody = jsonBody.toString();


                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {

                                        sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);

                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        ObjectMapper mapper = new ObjectMapper();
                                        Map<String,Object> map = null;
                                        try {
                                            map = mapper.readValue(response.toString(), Map.class);
                                        } catch (JsonProcessingException e) {
                                            e.printStackTrace();
                                        }
                                        editor.putString(Login.Token, (String) map.get("data"));
                                        editor.commit();

                                        Toast.makeText(SignUp.this,"Inscription réussie", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUp.this, Menu.class);
                                        startActivity(intent);

                                        finish();
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        erreur.setText("Une erreur est survenue. Veuillez reessayer.");
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
            }
        });
    }
}