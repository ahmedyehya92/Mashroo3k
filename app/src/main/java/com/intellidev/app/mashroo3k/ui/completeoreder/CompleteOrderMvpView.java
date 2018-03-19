package com.intellidev.app.mashroo3k.ui.completeoreder;

import com.intellidev.app.mashroo3k.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 10/03/2018.
 */

public interface CompleteOrderMvpView extends MvpView {
    void showToast(int messageResId);
    void showErrorConnectionDialog ();
    void completePurchase(String payAmount);
    void hideProgressBar();
    void showProgressBar();
    void unLoadEditTexts();
}
