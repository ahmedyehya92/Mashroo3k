package com.intellidev.app.mashroo3k;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.SharedPrefsHelper;
import com.intellidev.app.mashroo3k.data.dphelper.SqliteHandler;

import java.util.Locale;

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
        setLocale("ar");

    }
    public DataManager getDataManager() {
        return dataManager;
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLayoutDirection(myLocale);
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }
}
