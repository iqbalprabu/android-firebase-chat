package com.iqbalprabu.chatsapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by iqbalprabu on 18/09/16.
 */
public class MyChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        instance.setPersistenceEnabled(true);
        instance.getReference().keepSynced(true);
    }
}
