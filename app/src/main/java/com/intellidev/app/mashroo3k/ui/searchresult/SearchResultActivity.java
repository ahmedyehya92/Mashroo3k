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
import com.intellidev.app.mashroo3k.PaginationAdapterCallback;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.adapters.FeasibilityStudyAdapter;
import com.intellidev.app.mashroo3k.data.adapters.SearchResultAdapter;
import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
import com.intellidev.app.mashroo3k.ui.base.BaseActivity;
import com.intellidev.app.mashroo3k.ui.feasibilitystuddiscription.FeasibilityStudDescriptionActivity;
import com.intellidev.app.mashroo3k.uiutilities.CustomEditText;
import com.intellidev.app.mashroo3k.uiutilities.CustomRecyclerView;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.uiutilities.paginationStaggardScrollListener;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

import java.util.ArrayList;

public class SearchResultActivity extends BaseActivity implements SearchResultMvpView, FeasibilityStudyAdapter.customButtonListener, PaginationAdapterCallback {
    private Intent intent;
    private String query;

    private FeasibilityStudyAdapter resultAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    Toolbar toolbar;
    CustomRecyclerView rv;
    ProgressBar progressBar;
    LinearLayout errorLayout;
    Button btnRetry;
    TextView txtError;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 20;
    private Integer currentPage = PAGE_START;
    private boolean loading = true;
    private boolean lastItem;

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
                presenter.getFirstSearchResult(query);
            }
        });

        setupRecyclerView();
        currentPage = PAGE_START;
        presenter.getFirstSearchResult(query);

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

        resultAdapter = new FeasibilityStudyAdapter(this, arrayList);
        resultAdapter.setCustomButtonListner(this);
        resultAdapter.setPagingAdapterCallback(this);
        rv.setAdapter(resultAdapter);
        resultAdapter.notifyDataSetChanged();
        implementScrollListener();
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

    private void implementScrollListener() {
        rv.addOnScrollListener(new paginationStaggardScrollListener(staggeredGridLayoutManager) {
            @Override
            protected void hideCatList() {

            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                presenter.getNextSearchResult(query, currentPage.toString());

            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });



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
    public void setLastPageTrue() {
        isLastPage = true;
    }

    @Override
    public void removeLoadingFooter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                resultAdapter.removeLoadingFooter();
            }
        });
    }

    @Override
    public void addLoadingFooter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                resultAdapter.addLoadingFooter();
            }
        });
    }

    @Override
    public void showRetryAdapter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                resultAdapter.showRetry(true,getString(R.string.conn_error_recyview));
            }
        });
    }

    @Override
    public void setIsLoadingFalse() {
        isLoading = false;
    }

    @Override
    public void refreshStudiesRecyclerView(ArrayList<FeasibilityStudyModel> studyItems) {
        arrayList.addAll(studyItems);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                resultAdapter.notifyDataSetChanged();
            }
        });
        loading = true;
    }

    @Override
    public void switchLastItem() {
        if (lastItem)
            lastItem = false;
        else
            lastItem = true;
    }

    @Override
    public void onItemStudyClickListner(String id, String title, String content, String imgUrl, String services, String money, String price, View buttonView, int position) {
        startActivity(FeasibilityStudDescriptionActivity.getStartIntent(this,id,title,content,services,money,price,imgUrl));
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_down);
    }

    @Override
    public void retryPageLoad() {
        presenter.getNextSearchResult(query, currentPage.toString());
    }
}
