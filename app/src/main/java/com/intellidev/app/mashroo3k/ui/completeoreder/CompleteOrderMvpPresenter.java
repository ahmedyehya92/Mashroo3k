package com.intellidev.app.mashroo3k.ui.completeoreder;

import com.intellidev.app.mashroo3k.data.models.CartListModel;
import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 10/03/2018.
 */

public interface CompleteOrderMvpPresenter <V extends CompleteOrderMvpView> extends MvpPresenter<V> {
    ArrayList<CartListModel> getCartItems ();
    void sendOrder(String price, ArrayList<String> idOfItems, String fullName, String phoneNumber, String email, String address, String note);
}
