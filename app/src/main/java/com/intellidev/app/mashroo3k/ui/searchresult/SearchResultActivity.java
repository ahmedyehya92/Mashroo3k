package com.intellidev.app.mashroo3k.ui.searchresult;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.uiutilities.CustomEditText;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

public class SearchResultActivity extends AppCompatActivity {
    CustomEditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
    }




    public static Intent getStartIntent (Context context, String query)
    {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(StaticValues.KEY_SEARCH_QUERY, query);
        return intent;
    }
}
