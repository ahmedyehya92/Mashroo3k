package com.intellidev.app.mashroo3k.ui.currency;

import com.intellidev.app.mashroo3k.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 13/03/2018.
 */

public interface CurrencyMvpView extends MvpView {
    void startCalculate();
    void finishCalculate(String result);
    void showToast(int msgResourceId);
}
