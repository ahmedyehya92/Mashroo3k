package com.intellidev.app.mashroo3k.ui.base;

import android.content.Context;
import android.content.ContextWrapper;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public interface MvpView {
    ContextWrapper changeLang(Context context, String lang_code);
}
