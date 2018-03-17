package com.intellidev.app.mashroo3k.ui.calculator;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.ui.borrowings.BorrowingsFragment;
import com.intellidev.app.mashroo3k.ui.currency.CurrencyFragment;
import com.intellidev.app.mashroo3k.ui.ratereturn.RateReturnFragment;
import com.intellidev.app.mashroo3k.ui.recoveryperiod.RecoveryPeriodFragment;
import com.intellidev.app.mashroo3k.uiutilities.CustomBoldTextView;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.uiutilities.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    ViewPagerAdapter adapter;



    public CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calculator, container, false);
        viewPager = rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        CustomBoldTextView tabOne = (CustomBoldTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText("معدل العائد");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        CustomBoldTextView tabTow = (CustomBoldTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTow.setText("فترة الاسترداد");
        tabLayout.getTabAt(1).setCustomView(tabTow);

        CustomBoldTextView tabTow1 = (CustomBoldTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTow1.setText("فوائد القروض");
        tabLayout.getTabAt(2).setCustomView(tabTow1);

        CustomBoldTextView tabThree = (CustomBoldTextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText("تحويل العملات");
        tabLayout.getTabAt(3).setCustomView(tabThree);


        return rootView;
    }

    private void setupViewPager (ViewPager viewPager)
    {
        adapter = new ViewPagerAdapter (getChildFragmentManager());
        adapter.addFragment(RateReturnFragment.newInstance("data for fragment rate","1"),"RateReturn");
        adapter.addFragment(RecoveryPeriodFragment.newInstance("data","2"),"recovery");
        adapter.addFragment(BorrowingsFragment.newInstance("data","3"),"borrowings");
        adapter.addFragment(CurrencyFragment.newInstance("data","4"),"currency");
        viewPager.setAdapter(adapter);
    }

}
