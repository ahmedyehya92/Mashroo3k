package com.intellidev.app.mashroo3k.ui.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.intellidev.app.mashroo3k.R;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements MvpView {

    public void setLocale() {
        Locale myLocale = new Locale("ar");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLayoutDirection(myLocale);
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

}
