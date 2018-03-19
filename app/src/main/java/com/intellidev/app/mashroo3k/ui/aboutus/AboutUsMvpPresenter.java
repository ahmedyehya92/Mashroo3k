package com.intellidev.app.mashroo3k.ui.aboutus;

import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 19/03/2018.
 */

public interface AboutUsMvpPresenter <V extends AboutUsMvpView> extends MvpPresenter<V> {
    void reqAboutUsDetails();
}
