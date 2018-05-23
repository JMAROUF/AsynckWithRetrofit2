package com.example.jamal.tpasynckandretrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jamal on 16/05/2018.
 */

public class Result {

    @SerializedName("data")
    @Expose
    ArrayList<HashMap<String,String>> data ;

    @SerializedName("listModules")
    @Expose
    ArrayList<HashMap<String,String>> listModules ;


    public ArrayList<HashMap<String, String>> getData() {
        return data;
    }

    public void setData(ArrayList<HashMap<String, String>> data) {
        this.data = data;
    }

    // get Modules
    public ArrayList<HashMap<String, String>> getListModules() {
        return listModules;
    }

    public void setListModules(ArrayList<HashMap<String, String>> listModules) {
        this.listModules = listModules;
    }
}

























    /*
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("result2")
    @Expose
    private String result2;

    public String getResult() {
        return result;
    }

    public Result(String result, String result2) {
        this.result = result;
        this.result2 = result2;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult2() {
        return result2;
    }

    public void setResult2(String result2) {
        this.result2 = result2;
    }*/


