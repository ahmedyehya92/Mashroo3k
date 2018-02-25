package com.intellidev.app.mashroo3k.ui.feasibilitystudies;

import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public interface FeasibilityStudiesMvpPresenter <V extends FeasibilityStudiesMvpView> extends MvpPresenter<V> {
    void reqCategoriesList ();
    void reqStudiesList(String id);
}
