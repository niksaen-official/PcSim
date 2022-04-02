package com.niksaen.pcsim.save;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
    public String Theme = "Dark";
    public String Language = "RU";

    private SharedPreferences sharedPreferences;
    public Settings(Context context){
         sharedPreferences = context.getSharedPreferences("Settings",Context.MODE_PRIVATE);
         getSettings();
    }

    private void getSettings(){
         Language = getLanguage();
    }

    //настройки языка
    private String getLanguage(){
        return sharedPreferences.getString("lang", com.niksaen.pcsim.save.Language.RU);
    }
    public void setLanguage(String langCode){
        sharedPreferences.edit().putString("lang",langCode).apply();
    }
}
