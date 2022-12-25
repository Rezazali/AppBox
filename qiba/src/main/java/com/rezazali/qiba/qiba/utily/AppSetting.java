package com.rezazali.qiba.qiba.utily;
import android.content.Context;
import android.content.SharedPreferences;



public class AppSetting {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static String KEY_SETTING = "setting";

    private AppSetting() {
    }

    public AppSetting(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY_SETTING, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setTasbih(int tasbih) {
        editor.putInt("Tasbih", tasbih);
        editor.commit();
    }

    public int getTasbih() {
        return sharedPreferences.getInt("Tasbih", 0);
    }

    public void setBoot(int number) {
        editor.putInt("Boot", number);
        editor.commit();
    }


    public void setLat(double lat) {
        editor.putString("lat", String.valueOf(lat));
        editor.commit();
    }

    public String getLat() {
        return sharedPreferences.getString("lat", "0");
    }


    public void setLang(double lang) {
        editor.putString("lang", String.valueOf(lang));
        editor.commit();
    }

    public String getLan() {
        return sharedPreferences.getString("lang", "0");
    }


    public void hasLocation(boolean location) {
        editor.putBoolean("location", location);
        editor.commit();
    }

    public boolean getlocation() {
        return sharedPreferences.getBoolean("location", false);
    }


}

