package com.intellidev.app.mashroo3k;

import android.app.Application;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.SharedPrefsHelper;
import com.intellidev.app.mashroo3k.data.dphelper.SqliteHandler;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public class MvpApp extends Application {
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(getApplicationContext());
        SqliteHandler sqliteHandler = new SqliteHandler(getApplicationContext());
        dataManager = new DataManager(sqliteHandler, sharedPrefsHelper);

    }
    public DataManager getDataManager() {
        return dataManager;
    }
}
