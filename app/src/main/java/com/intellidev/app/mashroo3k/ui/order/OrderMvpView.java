package com.intellidev.app.mashroo3k.ui.order;

import com.intellidev.app.mashroo3k.ui.base.MvpView;

/**
 * Created by Ahmed Yehya on 27/02/2018.
 */

public interface OrderMvpView extends MvpView {
    void showToast(int messageResId);
    void showTextToast(String message);
    void showAlert(final int title, final String message);
    void changeViewEffectForStartSendOrder ();
    void changeViewEffectForResponceSendOrder ();
    void showAlertConnectionError();

}
