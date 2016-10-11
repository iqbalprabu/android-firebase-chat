package com.iqbalprabu.chatsapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by iqbalprabu on 18/09/16.
 */
public class AppPreference {

    private SharedPreferences sharedPreferences;
    private String PREF_NAME = "ChatsApp";
    private String KEY_EMAIL = "email";
    private String Email;
    private SharedPreferences.Editor editor;

    public AppPreference(Context mContext)
    {
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public void setEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public void clear()
    {
        editor.clear();
        editor.commit();
    }
}
