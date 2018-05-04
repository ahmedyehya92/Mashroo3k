package com.intellidev.app.mashroo3k.ui.opportunities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.PaginationAdapterCallback;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.adapters.OppertunitiesAdapter;
import com.intellidev.app.mashroo3k.data.models.OpportunityModel;
import com.intellidev.app.mashroo3k.ui.home.HomeFragment;
import com.intellidev.app.mashroo3k.ui.newsdetails.NewsDetailsActivity;
import com.intellidev.app.mashroo3k.uiutilities.paginationStaggardScrollListener;
import com.intellidev.app.mashroo3k.ui.base.BaseFragment;
import com.intellidev.app.mashroo3k.utilities.OrderTitleChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpportunitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpportunitiesFragment extends BaseFragment implements OpportunitiesMvpView, OppertunitiesAdapter.customButtonListener, PaginationAdapterCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OppertunitiesAdapter oppertunitiesAdapter;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    OrderButtonListener orderButtonListener;

    RecyclerView rv;
    ProgressBar progressBar;
    LinearLayout errorLayout;
    Button btnRetry;
    TextView txtError;

    OpportunityPresenter presenter;

    private static final int PAGE_START = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 20;
    private Integer currentPage = PAGE_START;

    private ArrayList<OpportunityModel> arrayList;
    private Handler mHandler;


    public OpportunitiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment OpportunitiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpportunitiesFragment newInstance(String param1, OrderButtonListener o) {
        OpportunitiesFragment fragment = new OpportunitiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setOrderClickListener(o);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        DataManager dataManager = ((MvpApp) getActivity().getApplication()).getDataManager();
        presenter = new OpportunityPresenter(dataManager);
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mHandler = new Handler(Looper.getMainLooper());
        View rootview = inflater.inflate(R.layout.fragment_opportunities, container, false);

        rv = rootview.findViewById(R.id.rv_opportunities);
        progressBar = rootview.findViewById(R.id.main_progress);

        if (progressBar != null) {
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
            progressBar.setVisibility(View.VISIBLE);
        }

        errorLayout = rootview.findViewById(R.id.error_layout);
        btnRetry = rootview.findViewById(R.id.error_btn_retry);
        txtError = rootview.findViewById(R.id.error_txt_cause);
        arrayList = new ArrayList<>();

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadFirstPage();
            }
        });

        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        rv.setLayoutManager(staggeredGridLayoutManager);
        rv.setHasFixedSize(true);

        rv.setItemViewCacheSize(20);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        populatRecyclerView();
        implementScrolListener();

        currentPage = PAGE_START;
        presenter.loadFirstPage();

        super.onViewCreated(view, savedInstanceState);
    }

    private void populatRecyclerView() {

        oppertunitiesAdapter = new OppertunitiesAdapter(getActivity(), arrayList);
        oppertunitiesAdapter.setCustomButtonListner(this);
        oppertunitiesAdapter.setPagingAdapterCallback(this);
        rv.setAdapter(oppertunitiesAdapter);
        oppertunitiesAdapter.notifyDataSetChanged();

    }
    private void implementScrolListener()
    {
        rv.addOnScrollListener(new paginationStaggardScrollListener(staggeredGridLayoutManager) {
            @Override
            protected void hideCatList() {

            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                presenter.loadNextPage(currentPage);
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
    public void onButtonOrderClickListner(String title, String id, View buttonView, int position) {
        EventBus bus = EventBus.getDefault();
        bus.post(new OrderTitleChangeEvent(title));
        orderButtonListener.onOrderClickListener(title);
    }

    @Override
    public void onContainerClickListener(String title, String id, String imUrl, String content, int FLAG_INTENT) {
        startActivity(NewsDetailsActivity.getStartIntent(getActivity(),title,imUrl,content, FLAG_INTENT));

    }

    @Override
    public void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showErrorView() {
        if (getActivity() != null) {
            if (errorLayout.getVisibility() == View.GONE) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        errorLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        txtError.setText(fetchErrorMessage());
                    }
                });

            }
        }
        else
            Log.d("Feas fragment", "showErrorView: getActivity = null");
    }

    @Override
    public void setLastPageTrue() {
        isLastPage = true;
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
    public void addMoreToAdapter(final ArrayList<OpportunityModel> list) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                oppertunitiesAdapter.addAll(list);
            }
        });



    }

    @Override
    public void addLoadingFooter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                oppertunitiesAdapter.addLoadingFooter();
            }
        });

    }

    @Override
    public void removeLoadingFooter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                oppertunitiesAdapter.removeLoadingFooter();
            }
        });

    }

    @Override
    public void showRetryAdapter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                oppertunitiesAdapter.showRetry(true,getString(R.string.conn_error_recyview));
            }
        });

    }

    @Override
    public void setIsLoadingFalse() {
        isLoading = false;
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void retryPageLoad() {
        presenter.loadNextPage(currentPage);
    }

    public interface OrderButtonListener {
        public void onOrderClickListener (String title);
    }
    public void setOrderClickListener (OrderButtonListener orderButtonListener)
    {
        this.orderButtonListener = orderButtonListener;
    }

}
