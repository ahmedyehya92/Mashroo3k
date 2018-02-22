package com.intellidev.app.mashroo3k.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public class SharedPrefsHelper {
    public static final String MY_PREFS = "MY_PREFS";
    SharedPreferences mSharedPreferences;

    public SharedPrefsHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
    }
}
