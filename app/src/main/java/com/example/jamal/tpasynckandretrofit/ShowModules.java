package com.example.jamal.tpasynckandretrofit;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowModules extends AppCompatActivity {

    public static final String URL="https://tdi-examples.000webhostapp.com/lister_modules.php/";
    ListView myListe;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_modules);
        myListe=(ListView) findViewById(R.id.myList);
        textView=(TextView) findViewById(R.id.textModules);
        loadModules();

    }

    public void loadModules(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                //ici on utilise  GsonConverterFactory pour convertir les données  json  en objets
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyService myService = adapter.create(MyService.class);
        retrofit2.Call<Result> call = myService.getModules();
        new AscknckLoadModules().execute(call);
    }

    class AscknckLoadModules extends AsyncTask<Call<Result>,Integer,String[]> {


        @Override
        protected void onPreExecute() {
            myListe.setVisibility(View.INVISIBLE);
            textView.setText("Debut ");
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(Call<Result>[] calls) {
            String lisModules[]={""};
            ArrayList<HashMap<String,String>> modules = null;

            try {
                modules = calls[0].execute().body().getListModules();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String key,value;
            int progression;
            if(modules !=null) {
                lisModules= new String[modules.size()];
                progression=100/modules.size();
                for (int i = 0; i < modules.size(); i++) {
                    key= String.valueOf(modules.get(i).keySet()).replace("[","");
                    key= key.replace("]","");
                    value=modules.get(i).get(key).toString();
                    lisModules[i]=value;
                    publishProgress(i*progression);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            return lisModules;

        }



        @Override
        protected void onPostExecute(String[] listModules) {
            textView.setText("liste des modules ");
            ArrayAdapter<String> modulesAdapter =
                    new ArrayAdapter<String>(ShowModules.this,
                            android.R.layout.simple_list_item_single_choice, listModules);
            modulesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            myListe.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            myListe.setAdapter(modulesAdapter);
            myListe.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView.setText("chargement en cours "+values[0]+"%");


        }
    }
}
