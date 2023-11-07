package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        EditText inputTextNombrePersona = findViewById(R.id.TextNombrePersona);
        String nombrePersona = inputTextNombrePersona.getText().toString();

        llamarAPIGenero("https://api.genderize.io/?name=" + nombrePersona, Request.Method.GET);
        llamarAPIEdad("https://api.agify.io/?name=" + nombrePersona, Request.Method.GET);
    }

    private void llamarAPIGenero(String url, int httpVerb) {
        TextView campotextGenero = findViewById(R.id.textGenero);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(httpVerb, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject reader = new JSONObject(response);
                            String genero = reader.getString("gender");

                            if (genero.equals("male")) {
                                genero = "hombre";
                            } else {
                                genero = "mujer";
                            }
                            campotextGenero.setText(genero);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        campotextGenero.setText("Ocurrió un error");
                    }
                });

        queue.add(stringRequest);
    }

    private void llamarAPIEdad(String url, int httpVerb) {
        TextView campotextEdad = findViewById(R.id.textEdad);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(httpVerb, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject reader = new JSONObject(response);
                            String edad = reader.getString("age");

                            campotextEdad.setText(edad);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        campotextEdad.setText("Ocurrió un error: " +error.toString());
                    }
                });


        queue.add(stringRequest);
    }
}
