package com.intellidev.app.mashroo3k.ui.base;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.Fragment;

/**
 * Created by Ahmed Yehya on 22/02/2018.
 */

public class BaseFragment extends Fragment implements MvpView {
    @Override
    public ContextWrapper changeLang(Context context, String lang_code) {
        return null;
    }
}
