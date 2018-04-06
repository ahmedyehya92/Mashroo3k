package com.intellidev.app.mashroo3k.ui.searchresult;

import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
import com.intellidev.app.mashroo3k.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by ahmedyehya on 4/6/18.
 */

public interface SearchResultMvpView extends MvpView {
    void hideErrorView();
    void showErrorView();
    String fetchErrorMessage();
    void addMoreToAdapter(ArrayList<FeasibilityStudyModel> list);
    void hideProgressBar();
}
