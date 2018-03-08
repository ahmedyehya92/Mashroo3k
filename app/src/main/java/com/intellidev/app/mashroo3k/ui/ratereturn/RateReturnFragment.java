package com.intellidev.app.mashroo3k.ui.ratereturn;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.intellidev.app.mashroo3k.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateReturnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateReturnFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText etAnnualNetCash, etTotalInvestment;

    Button btnCalculate;

    public RateReturnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RateReturnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RateReturnFragment newInstance(String param1, String param2) {
        RateReturnFragment fragment = new RateReturnFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_rate_return, container, false);
        etAnnualNetCash = rootView.findViewById(R.id.et_annual_net_cash);
        etTotalInvestment = rootView.findViewById(R.id.et_total_invistment);
        btnCalculate = rootView.findViewById(R.id.btn_calculate);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(etAnnualNetCash.getText().toString().isEmpty() || etTotalInvestment.getText().toString().isEmpty())) {
                    float annualNetCash = Float.parseFloat(etAnnualNetCash.getText().toString());
                    float totalInvestment = Float.parseFloat(etTotalInvestment.getText().toString());
                    float result = annualNetCash / totalInvestment;
                    btnCalculate.setText(String.format("%s", result));
                    btnCalculate.setTextSize(20);
                }
                else
                    Toast.makeText(getActivity(), "برجاء ملأ الحقول الفارغة", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
