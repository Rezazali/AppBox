package com.rezazali.metro.config;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {


    SharedPreferences sh;
    SharedPreferences.Editor editor;

    public AppConfig (Context context) {
        sh= context.getSharedPreferences("setting" , Context.MODE_PRIVATE);
        editor = sh.edit();
    }

    public void saveInfo(String title , String titleEnglish) {

        editor.putString("title" , title);
        editor.putString("english" , titleEnglish);
        editor.commit();
    }
    public String getTitle() {
        return sh.getString("title","");
    }
    public String getEnglishTitle() {
        return  sh.getString("english","");
    }

    public void setFont(int size) {
        editor.putInt("size" , size);
        editor.commit();
    }
    public int getFont() {
        return sh.getInt("size",16);
    }






}
