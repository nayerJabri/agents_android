package com.forme.agents.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.forme.agents.DTO.LoginResponse;

public class PreferenceHelper {

    private static final String _NAME = "mypref";
    private SharedPreferences preferences;

    public PreferenceHelper(Context context) {
        preferences = context.getSharedPreferences(_NAME, Context.MODE_PRIVATE);
    }



    private static String _LOGINRESPONSE = "loginResponse";

    public void saveLoginData(LoginResponse response)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(_LOGINRESPONSE,new Gson().toJson(response));
        editor.commit();
    }

    public void saveLoginData(String data)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(_LOGINRESPONSE,data);
        editor.commit();
    }

    public String getLoginData()
    {
        return preferences.getString(_LOGINRESPONSE,"");
    }
}
