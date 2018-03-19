package com.intellidev.app.mashroo3k.ui.aboutus;

import com.intellidev.app.mashroo3k.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 19/03/2018.
 */

public interface AboutUsMvpView extends MvpView {
    void showLoadingLayout();
    void hideLoadingLyout();
    void showErrorLayout();
    void setTextOfAboutUs(String details);
    void hideErrorLayout();
    String fetchErrorMessage();
}
