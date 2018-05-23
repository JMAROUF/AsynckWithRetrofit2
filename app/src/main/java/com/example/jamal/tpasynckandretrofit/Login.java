package com.example.jamal.tpasynckandretrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    public static final String URL="https://tdi-examples.000webhostapp.com/";
    private LinearLayout container;
    private  Button sendButon;
    private ProgressBar progressBar;
    private  EditText emailEdit,passwordEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        container=(LinearLayout) findViewById(R.id.containerLayout);
        sendButon=(Button) findViewById(R.id.sendButton);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        emailEdit = (EditText) findViewById(R.id.loginEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        progressBar.setVisibility(View.INVISIBLE);
        sendButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = emailEdit.getText().toString();
               String password = passwordEdit.getText().toString();
                container.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
               connexion(email,password);
            }
        });
    }

    // appel asynchrone
    public void connexion(String email,String password){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                //Here we are using the GsonConverterFactory to directly convert json data to object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyService myService = adapter.create(MyService.class);
        retrofit2.Call<Result> call = myService.connectUser(email,password);
        call.enqueue(new retrofit2.Callback<Result>() {
            @Override
            public void onResponse(retrofit2.Call<Result> call, retrofit2.Response<Result> response) {
                if(response.isSuccessful()) {

                    ArrayList<HashMap<String, String>> results = response.body().getData();


                    String values[] = new String[results.size()];
                    for (int i = 0; i < results.size(); i++) {
                        values[i] = results.get(i).get("result");
                    }

                    if (values[0].equals("successful")) {

                        showMessage("CONNECTED");

                        Intent nextActivity = new Intent(Login.this, ShowModules.class);
                        startActivity(nextActivity);
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        showMessage(results.get(0).get("result"));
                        progressBar.setVisibility(View.INVISIBLE);
                        container.setVisibility(View.VISIBLE);

                    }

                }else{

                    showMessage("ERROR..! "+response.code());
                    finish();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<Result> call, Throwable t) {

                showMessage("ERROOR "+ t.getMessage());

            }
        });

    }

    public void showMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}
