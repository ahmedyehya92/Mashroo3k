package com.intellidev.app.mashroo3k.ui.searchresult;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.adapters.SearchResultAdapter;
import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.ui.feasibilitystuddiscription.FeasibilityStudDescriptionActivity;
import com.intellidev.app.mashroo3k.uiutilities.CustomEditText;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

import java.util.ArrayList;

public class SearchResultActivity extends BaseActivity implements SearchResultMvpView, SearchResultAdapter.CustomButtonListener {
    private Intent intent;
    private String query;

    private SearchResultAdapter resultAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    Toolbar toolbar;
    RecyclerView rv;
    ProgressBar progressBar;
    LinearLayout errorLayout;
    Button btnRetry;
    TextView txtError;

    SearchResultPresenter presenter;

    private ArrayList<FeasibilityStudyModel> arrayList;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initViews();
        setupActionBar();
        mHandler = new Handler(Looper.getMainLooper());
        intent = getIntent();
        query = intent.getStringExtra(StaticValues.KEY_SEARCH_QUERY);

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new SearchResultPresenter(dataManager);
        presenter.onAttach(this);
        arrayList = new ArrayList<>();
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getSearchResult(query);
            }
        });

        setupRecyclerView();
        presenter.getSearchResult(query);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar_main);
        rv = findViewById(R.id.rv_results);

        progressBar = findViewById(R.id.main_progress);
        if (progressBar != null) {
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
            progressBar.setVisibility(View.VISIBLE);
        }

        errorLayout = findViewById(R.id.error_layout);
        btnRetry = findViewById(R.id.error_btn_retry);
        txtError = findViewById(R.id.error_txt_cause);
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

        ((CustomTextView)v.findViewById(R.id.title)).setText("نتائج البحث");

        actionBar.setCustomView(v);

    }

    private void setupRecyclerView()
    {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(staggeredGridLayoutManager);
        rv.setHasFixedSize(true);

        rv.setItemViewCacheSize(20);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        resultAdapter = new SearchResultAdapter(this, arrayList);
        resultAdapter.setCustomButtonListner(this);
        rv.setAdapter(resultAdapter);
        resultAdapter.notifyDataSetChanged();
    }


    public static Intent getStartIntent (Context context, String query)
    {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(StaticValues.KEY_SEARCH_QUERY, query);
        return intent;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void hideErrorView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (errorLayout.getVisibility() == View.VISIBLE) {
                    errorLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void showErrorView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                errorLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                txtError.setText(fetchErrorMessage());
            }
        });
    }

    @Override
    public String fetchErrorMessage() {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        }

        return errorMsg;
    }

    @Override
    public void addMoreToAdapter(final ArrayList<FeasibilityStudyModel> list) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                resultAdapter.addAll(list);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClickListner(String id, String title, String content, String imgUrl, String services, String money, String price, View buttonView, int position) {
        startActivity(FeasibilityStudDescriptionActivity.getStartIntent(this,id,title,content,services,money,price,imgUrl));
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_down);
    }
}
