package com.intellidev.app.mashroo3k.ui.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.ui.searchresult.SearchResultActivity;
import com.intellidev.app.mashroo3k.uiutilities.CustomButtonTextFont;
import com.intellidev.app.mashroo3k.uiutilities.CustomEditText;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;

public class SearchActivity extends BaseActivity {

    Toolbar toolbar;
    CustomEditText et_query;
    CustomButtonTextFont btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        setupActionBar();
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_query.getText().toString().isEmpty())
                    Toast.makeText(SearchActivity.this, "قم بإدخال ما تريد البحث عنه أولا", Toast.LENGTH_SHORT).show();
                else {
                    startActivity(SearchResultActivity.getStartIntent(SearchActivity.this, et_query.getText().toString()));
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                }

            }
        });
    }
    public static Intent getStartIntent (Context context)
    {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.description_action_menu,menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews ()
    {
        toolbar = findViewById(R.id.toolbar_main);
        et_query = findViewById(R.id.et_search);
        btn_search = findViewById(R.id.btn_search);
    }

     private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar_title, null);

        ((CustomTextView)v.findViewById(R.id.title)).setText("البحث عن مشروع");

        actionBar.setCustomView(v);

    }
}
