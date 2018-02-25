package com.intellidev.app.mashroo3k.ui.opportunities;

import com.intellidev.app.mashroo3k.data.models.OpportunityModel;
import com.intellidev.app.mashroo3k.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 24/02/2018.
 */

public interface OpportunitiesMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    void setLastPageTrue();
    String fetchErrorMessage();
    void addMoreToAdapter(ArrayList<OpportunityModel> list);
    void addLoadingFooter();
    void removeLoadingFooter();
    void showRetryAdapter();
    void setIsLoadingFalse();
    void hideProgressBar();
}
