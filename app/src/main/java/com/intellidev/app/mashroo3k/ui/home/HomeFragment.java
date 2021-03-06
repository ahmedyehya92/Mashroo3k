package com.intellidev.app.mashroo3k.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.intellidev.app.mashroo3k.uiutilities.CustomBoldTextView;
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
    TabItemPositionCallback tabItemPositionCallback;
    private int[] tabIcons = {R.drawable.ic_studies,R.drawable.ic_opportunities};
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnCompleteListener mListener;
    // TODO: Rename and change types of parameters
    private int mItemPosition = -1;

    OpportunitiesFragment.OrderButtonListener orderButtonListener = this;


    // TODO for Singleton design pattern
    private static HomeFragment fragment;
    @SuppressLint("ValidFragment")
    private HomeFragment(){}
    public static HomeFragment getHomeFragment ()
    {
        if (fragment == null) {
            fragment = new HomeFragment();
            Log.d("Error Dialog", "getFragment: home fragment = null");
        }
        return fragment;
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int itemPosition) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, itemPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemPosition = getArguments().getInt(ARG_PARAM1);
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
            viewPager.setOffscreenPageLimit(4);
            setupViewPager(viewPager);
            tabLayout = rootView.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

            CustomBoldTextView tabOne = (CustomBoldTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            tabOne.setText("دراسات جدوى");
            tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_study, 0, 0);
            tabLayout.getTabAt(0).setCustomView(tabOne);



            CustomBoldTextView tabTow = (CustomBoldTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            tabTow.setText("الفرص الاستثمارية");
            tabTow.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_chance, 0, 0);
            tabLayout.getTabAt(1).setCustomView(tabTow);

            CustomBoldTextView tabTow1 = (CustomBoldTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            tabTow1.setText("الأخبار");
            tabTow1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_action_news, 0, 0);
            tabLayout.getTabAt(2).setCustomView(tabTow1);

            CustomBoldTextView tabThree = (CustomBoldTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            tabThree.setText("طلب دراسة جدوى");
            tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_action_order, 0, 0);
            tabLayout.getTabAt(3).setCustomView(tabThree);


            //tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            //tabLayout.getTabAt(1).setIcon(tabIcons[1]);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    tabItemPositionCallback.setSelectedTabItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });


            for (int i = 0; i < tabLayout.getChildCount(); i++) {
                tabLayout.getChildAt(i).setPadding(2, 2, 2, 2);
            }
        }

        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setCurrentItem(mItemPosition);

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
        viewPager.setAdapter(adapter);
        adapter.addFragment(FeasibilityStudiesFragment.newInstance("data for fragment 1"),"Studies");
        adapter.addFragment(OpportunitiesFragment.newInstance("data for fragment 2",orderButtonListener),"Opportunities");
        adapter.addFragment(NewsFragment.newInstance("data for nes fragment"),"News");
        adapter.addFragment(OrderFragment.newInstance("data for fragment 3"), "Order");
    }
    void switchFragment(int target){
        viewPager.setCurrentItem(target);
    }

    @Override
    public void onOrderClickListener(String title) {
        viewPager.setCurrentItem(3);
    }

    public interface TabItemPositionCallback {
        public void setSelectedTabItem(int selectedTab);
    }

    public void setViewPagerListener(TabItemPositionCallback tabItemPositionCallback)
    {
        this.tabItemPositionCallback = tabItemPositionCallback;
    }

    public void setTabPosition(int position)
    {
        if (viewPager.getCurrentItem() != position)
            viewPager.setCurrentItem(position);
    }


    public static interface OnCompleteListener {
        public abstract void onComplete(int tabInd);
    }



    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCompleteListener)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }
}
