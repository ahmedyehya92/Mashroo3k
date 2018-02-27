package com.intellidev.app.mashroo3k.ui.order;

import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 27/02/2018.
 */

public interface OrderMvpPresenter <V extends OrderMvpView> extends MvpPresenter<V> {
    void sendOrder(String projectName, String orderType, String mobNum, String fullName, String money, String email, String subject, String productsAndServices, String details);
}
