package com.intellidev.app.mashroo3k;

import android.app.Application;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.SharedPrefsHelper;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public class MvpApp extends Application {
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(getApplicationContext());
        dataManager = new DataManager(sharedPrefsHelper);

    }
    public DataManager getDataManager() {
        return dataManager;
    }
}
