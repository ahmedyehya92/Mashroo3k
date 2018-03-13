package com.intellidev.app.mashroo3k.ui.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.intellidev.app.mashroo3k.data.models.OpportunityModel;
import com.intellidev.app.mashroo3k.ui.news.NewsFragment;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.uiutilities.ViewPagerAdapter;
import com.intellidev.app.mashroo3k.ui.feasibilitystudies.FeasibilityStudiesFragment;
import com.intellidev.app.mashroo3k.ui.opportunities.OpportunitiesFragment;
import com.intellidev.app.mashroo3k.ui.order.OrderFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OpportunitiesFragment.OrderButtonListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ViewPagerAdapter adapter;
    private int[] tabIcons = {R.drawable.ic_studies,R.drawable.ic_opportunities};
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    OpportunitiesFragment.OrderButtonListener orderButtonListener = this;



    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        Log.i("home fragment","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("home fragment","onCreateView");
        View rootView;
        if (getView() != null)
            rootView = getView();
        else {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            viewPager = rootView.findViewById(R.id.viewpager);
            setupViewPager(viewPager);
            tabLayout = rootView.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

            CustomTextView tabOne = (CustomTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            tabOne.setText("دراسات جدوى");
            tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_study, 0, 0);
            tabLayout.getTabAt(0).setCustomView(tabOne);

            CustomTextView tabTow = (CustomTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            tabTow.setText("الفرص الاستثمارية");
            tabTow.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_chance, 0, 0);
            tabLayout.getTabAt(1).setCustomView(tabTow);

            CustomTextView tabTow1 = (CustomTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            tabTow1.setText("الأخبار");
            tabTow1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_news, 0, 0);
            tabLayout.getTabAt(2).setCustomView(tabTow1);

            CustomTextView tabThree = (CustomTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            tabThree.setText("طلب دراسة جدوى");
            tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_order, 0, 0);
            tabLayout.getTabAt(3).setCustomView(tabThree);


            //tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            //tabLayout.getTabAt(1).setIcon(tabIcons[1]);

            for (int i = 0; i < tabLayout.getChildCount(); i++) {
                tabLayout.getChildAt(i).setPadding(2, 2, 2, 2);
            }
        }
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter (getChildFragmentManager());

        adapter.addFragment(FeasibilityStudiesFragment.newInstance("data for fragment 1"),"Studies");
        adapter.addFragment(OpportunitiesFragment.newInstance("data for fragment 2",orderButtonListener),"Opportunities");
        adapter.addFragment(NewsFragment.newInstance("data for nes fragment"),"News");
        adapter.addFragment(OrderFragment.newInstance("data for fragment 3"), "Order");
        viewPager.setAdapter(adapter);




    }
    void switchFragment(int target){
        viewPager.setCurrentItem(target);
    }

    @Override
    public void onOrderClickListener(String title) {
        viewPager.setCurrentItem(3);

    }
}
