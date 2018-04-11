package com.intellidev.app.mashroo3k.ui.feasibilitystudies;


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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intellidev.app.mashroo3k.PaginationAdapterCallback;
import com.intellidev.app.mashroo3k.data.models.OpportunityModel;
import com.intellidev.app.mashroo3k.ui.feasibilitystuddiscription.FeasibilityStudDescriptionActivity;
import com.intellidev.app.mashroo3k.uiutilities.CustomRecyclerView;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.adapters.CategoriesAdapter;
import com.intellidev.app.mashroo3k.data.adapters.FeasibilityStudyAdapter;
import com.intellidev.app.mashroo3k.data.models.CategoriesModel;
import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
import com.intellidev.app.mashroo3k.ui.base.BaseFragment;
import com.intellidev.app.mashroo3k.uiutilities.paginationStaggardScrollListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeasibilityStudiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeasibilityStudiesFragment extends BaseFragment implements FeasibilityStudiesMvpView, CategoriesAdapter.customButtonListener, FeasibilityStudyAdapter.customButtonListener,PaginationAdapterCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout loutShowCat;
    private CustomTextView tvcatTitle;
    private ListView lvCategories;
    private CustomRecyclerView rvStudies;
    private RelativeLayout layoutListView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    ProgressBar progressBar;
    LinearLayout errorLayout;
    Button btnRetry;
    TextView txtError;

    private static final int PAGE_START = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 20;
    private Integer currentPage = PAGE_START;
    private String currentId = "0";



    private boolean loading = true;
    Integer page = 1;
    private boolean lastItem;

    ArrayList<FeasibilityStudyModel> feasibilityStudyModelArrayList;

    FeasibilityStudyAdapter feasibilityStudyAdapter;

    CategoriesAdapter categoriesAdapter;
    View rootView;

    FeasibilitiesStudiesPresenter presenter;
    private Handler mHandler;
    ArrayList<CategoriesModel> catList;

    public FeasibilityStudiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FeasibilityStudiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeasibilityStudiesFragment newInstance(String param1) {
        FeasibilityStudiesFragment fragment = new FeasibilityStudiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
        presenter = new FeasibilitiesStudiesPresenter(dataManager);
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mHandler = new Handler(Looper.getMainLooper());
            rootView = inflater.inflate(R.layout.fragment_feasibility_studies, container, false);
            loutShowCat = rootView.findViewById(R.id.layout_shaw_categories);
            tvcatTitle = rootView.findViewById(R.id.tv_cat_title);
            lvCategories = rootView.findViewById(R.id.list_view_categories);
            lvCategories.setEmptyView(rootView.findViewById(R.id.empty_view));
            rvStudies = rootView.findViewById(R.id.rv_studies);
            progressBar = rootView.findViewById(R.id.main_progress);

            if (progressBar != null) {
                progressBar.setIndeterminate(true);
                progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                progressBar.setVisibility(View.VISIBLE);
            }

            errorLayout = rootView.findViewById(R.id.error_layout);
            btnRetry = rootView.findViewById(R.id.error_btn_retry);
            txtError = rootView.findViewById(R.id.error_txt_cause);
            // implementScrollListener();

            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentId.equals("0")) {
                        //TODO 1
                        // loadShowAllFirstPage();
                        presenter.loadFirstShowAllStudies();
                    } else
                        presenter.loadFirstStudiesByCat(currentId);
                }
            });

            layoutListView = rootView.findViewById(R.id.layout_listview);
            feasibilityStudyModelArrayList = new ArrayList<>();


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        loutShowCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutListView.getVisibility()== View.GONE) {

                    lvCategories.setAdapter(null);
                    layoutListView.setVisibility(View.VISIBLE);
                    lvCategories.setVisibility(View.VISIBLE);
                    if (catList == null)
                        presenter.reqCategoriesList();
                    else
                        setupListView(catList);
                }
                else
                {
                    layoutListView.setVisibility(View.GONE);
                    lvCategories.setVisibility(View.GONE);
                    lvCategories.setAdapter(null);
                }
            }
        });
      /*  routToutch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(layoutListView.getVisibility() == View.VISIBLE){
                        layoutListView.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        }); */

        StaggeredGridLayoutManager mainLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        LinearLayoutManager testLayoutManager = new LinearLayoutManager(getContext());
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvStudies
                .setLayoutManager(staggeredGridLayoutManager);

        rvStudies.setHasFixedSize(true);

        rvStudies.setItemViewCacheSize(20);
        rvStudies.setDrawingCacheEnabled(true);
        rvStudies.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        populatRecyclerView();
        implementScrollListener();

        // TODO 2
        // loadShowAllFirstPage();

        currentPage = PAGE_START;
        presenter.loadFirstShowAllStudies();
        presenter.reqCategoriesList();


        super.onViewCreated(view, savedInstanceState);
    }


    private void populatRecyclerView() {


        feasibilityStudyAdapter = new FeasibilityStudyAdapter(getActivity(), feasibilityStudyModelArrayList);
        feasibilityStudyAdapter.setCustomButtonListner(this);
        feasibilityStudyAdapter.setPagingAdapterCallback(this);
        rvStudies.setAdapter(feasibilityStudyAdapter);// set adapter on recyclerview
        feasibilityStudyAdapter.notifyDataSetChanged();


    }


    @Override
    public void setupListView(ArrayList<CategoriesModel> categoriesList) {
        this.catList =categoriesList;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lvCategories.setAdapter(null);
            }
        });

        categoriesAdapter = new CategoriesAdapter(getActivity(),categoriesList);
        categoriesAdapter.setCustomButtonListner(this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lvCategories.setAdapter(categoriesAdapter);
            }
        });

    }

    @Override
    public void refreshStudiesRecyclerView(ArrayList<FeasibilityStudyModel> studyItems) {
        feasibilityStudyModelArrayList.addAll(studyItems);
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // your stuff to update the UI
                feasibilityStudyAdapter.notifyDataSetChanged();
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
        //if (errorLayout.getVisibility() == View.GONE) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    errorLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    txtError.setText(fetchErrorMessage());
                }
            });
        }
        else
            Log.d("Feas fragment", "showErrorView: getActivity = null");

       // }
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
    public void addMoreToAdapter(final ArrayList<FeasibilityStudyModel> list) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                feasibilityStudyAdapter.addAll(list);
            }
        });
    }

    @Override
    public void addLoadingFooter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                feasibilityStudyAdapter.addLoadingFooter();
            }
        });
    }

    @Override
    public void removeLoadingFooter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                feasibilityStudyAdapter.removeLoadingFooter();
            }
        });
    }

    @Override
    public void showRetryAdapter() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                feasibilityStudyAdapter.showRetry(true,getString(R.string.conn_error_recyview));
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

    @Override
    public Context getActivit() {
        return getActivity();
    }

    @Override
    public void onItemCatClickListner(String id, String catName, View buttonView, int position) {
        tvcatTitle.setText(catName);
        feasibilityStudyModelArrayList.clear();
        feasibilityStudyAdapter.notifyDataSetChanged();
        layoutListView.setVisibility(View.GONE);
        lvCategories.setVisibility(View.GONE);
        lvCategories.setAdapter(null);
        currentId = id;
        isLastPage = false;
        isLoading = false;
        progressBar.setVisibility(View.VISIBLE);
        currentPage = PAGE_START;
        if (currentId.equals("0"))
        {
            // TODO 3
            //loadFirstShowAll();
            presenter.loadFirstShowAllStudies();
        }
        else {
            presenter.loadFirstStudiesByCat(id);
        }

    }

    @Override
    public void onItemStudyClickListner(String id, String title, String content, String imgUrl, String services, String money, String price, View buttonView, int position) {
        getActivity().startActivity(FeasibilityStudDescriptionActivity.getStartIntent(getActivity(),id,title,content,services,money,price,imgUrl));
        getActivity().overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_down);
    }

    private void implementScrollListener() {
        rvStudies.addOnScrollListener(new paginationStaggardScrollListener(staggeredGridLayoutManager) {
            @Override
            protected void hideCatList() {
                if (layoutListView.getVisibility()== View.VISIBLE) {
                    layoutListView.setVisibility(View.GONE);
                    lvCategories.setVisibility(View.GONE);
                    lvCategories.setAdapter(null);
                }
            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                if (currentId.equals("0"))
                {
                    // TODO 4
                    //loadNextShowAll(currentPage);
                    presenter.loadNextShowAllStudies(currentPage);
                }
                else
                presenter.loadNextStudiesByCat(currentId,currentPage);
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void retryPageLoad() {
        if (currentId.equals("0"))
        {
            // TODO 4
            //loadNextShowAll(currentPage);
            presenter.loadNextShowAllStudies(currentPage);
        }
        else
        presenter.loadNextStudiesByCat(currentId,currentPage);
    }
}
