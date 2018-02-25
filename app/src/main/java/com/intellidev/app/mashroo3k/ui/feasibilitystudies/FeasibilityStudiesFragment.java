package com.intellidev.app.mashroo3k.ui.feasibilitystudies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intellidev.app.mashroo3k.CustomRecyclerView;
import com.intellidev.app.mashroo3k.CustomTextView;
import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.data.adapters.CategoriesAdapter;
import com.intellidev.app.mashroo3k.data.adapters.FeasibilityStudyAdapter;
import com.intellidev.app.mashroo3k.data.models.CategoriesModel;
import com.intellidev.app.mashroo3k.data.models.FeasibilityStudyModel;
import com.intellidev.app.mashroo3k.ui.base.BaseFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeasibilityStudiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeasibilityStudiesFragment extends BaseFragment implements FeasibilityStudiesMvpView, CategoriesAdapter.customButtonListener, FeasibilityStudyAdapter.customButtonListener {
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



    private boolean loading = true;
    Integer page = 1;
    private boolean lastItem;

    ArrayList<FeasibilityStudyModel> feasibilityStudyModelArrayList;

    FeasibilityStudyAdapter feasibilityStudyAdapter;

    CategoriesAdapter categoriesAdapter;

    FeasibilitiesStudiesPresenter presenter;

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
        View rootView = inflater.inflate(R.layout.fragment_feasibility_studies, container, false);
        loutShowCat = rootView.findViewById(R.id.layout_shaw_categories);
        tvcatTitle = rootView.findViewById(R.id.tv_cat_title);
        lvCategories = rootView.findViewById(R.id.list_view_categories);
        lvCategories.setEmptyView(rootView.findViewById(R.id.empty_view));
        rvStudies = rootView.findViewById(R.id.rv_studies);
        rvStudies.setEmptyView(rootView.findViewById(R.id.empty_view_rec));
        // implementScrollListener();
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
                    presenter.reqCategoriesList();
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
        rvStudies
                .setLayoutManager(mainLayoutManager);
        populatRecyclerView();

        presenter.reqStudiesList("5191");

        super.onViewCreated(view, savedInstanceState);
    }


    private void populatRecyclerView() {


        feasibilityStudyAdapter = new FeasibilityStudyAdapter(getActivity(), feasibilityStudyModelArrayList);
        feasibilityStudyAdapter.setCustomButtonListner(this);
        rvStudies.setAdapter(feasibilityStudyAdapter);// set adapter on recyclerview
        feasibilityStudyAdapter.notifyDataSetChanged();


    }


    @Override
    public void setupListView(ArrayList<CategoriesModel> categoriesList) {
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
    public void onItemCatClickListner(String id, String catName, View buttonView, int position) {
        tvcatTitle.setText(catName);
        feasibilityStudyModelArrayList.clear();
        feasibilityStudyAdapter.notifyDataSetChanged();
        layoutListView.setVisibility(View.GONE);
        lvCategories.setVisibility(View.GONE);
        lvCategories.setAdapter(null);
        presenter.reqStudiesList(id);
    }

    @Override
    public void onItemStudyClickListner(String title, String content, String imgUrl, String services, String money, String price, View buttonView, int position) {

    }

    private void implementScrollListener() {

        rvStudies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                if (loading) {


                    if (dy > 0) //check for scroll down
                    {
                        int visibleItemCount = rvStudies.getLayoutManager().getChildCount();
                        int  totalItemCount = rvStudies.getLayoutManager().getItemCount();
                        int  pastVisiblesItems = ((LinearLayoutManager) rvStudies.getLayoutManager()).findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;

                            Log.v("...", " Reached Last Item");

                            loadMoreVideos();
                        }

                    }
                }
            }
        });


    }

    private void loadMoreVideos() {
        // Show Progress Layout

        // Handler to show refresh for a period of time you can use async task
        // while commnunicating serve
        if (lastItem!= true) {
            page++;
            // getInfographics(page,user_id,apiKey);
            lastItem = false;


        }

        else {

        }

        // notify adapter

        // Toast for task completion


        // After adding new data hide the view.

    /*    new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                // Loop for 3 items

                loadBar.setVisibility(View.GONE);

                // asynctask



            }
        }, 5000); */
    }

}
