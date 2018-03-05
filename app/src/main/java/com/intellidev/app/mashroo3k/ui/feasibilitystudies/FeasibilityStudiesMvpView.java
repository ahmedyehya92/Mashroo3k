package com.intellidev.app.mashroo3k.ui.feasibilitystudies;

import android.content.Context;

import com.intellidev.app.mashroo3k.data.models.CategoriesModel;
import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
import com.intellidev.app.mashroo3k.data.models.OpportunityModel;
import com.intellidev.app.mashroo3k.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public interface FeasibilityStudiesMvpView extends MvpView {
    void setupListView(ArrayList<CategoriesModel> categoriesList);
    void refreshStudiesRecyclerView(ArrayList<FeasibilityStudyModel> studyItems);
    void switchLastItem();
    void hideErrorView();
    void showErrorView();
    void setLastPageTrue();
    String fetchErrorMessage();
    void addMoreToAdapter(final ArrayList<FeasibilityStudyModel> list);
    void addLoadingFooter();
    void removeLoadingFooter();
    void showRetryAdapter();
    void setIsLoadingFalse();
    void hideProgressBar();
    Context getActivit();

}
