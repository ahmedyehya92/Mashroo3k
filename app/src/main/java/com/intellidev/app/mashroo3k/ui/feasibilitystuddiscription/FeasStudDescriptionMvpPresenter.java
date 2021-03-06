package com.intellidev.app.mashroo3k.ui.feasibilitystuddiscription;

import com.intellidev.app.mashroo3k.ui.base.MvpPresenter;

/**
 * Created by Ahmed Yehya on 08/03/2018.
 */

public interface FeasStudDescriptionMvpPresenter <V extends FeasStudDescriptionMvpView> extends MvpPresenter <V> {
    Integer getNumberOfItemsInCart();
    void addItemToCart (String id, String title, String price, String imgUrl );
    boolean isItemExistingInCart (String itemId);
}
