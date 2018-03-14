package com.intellidev.app.mashroo3k.ui.currency;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.uiutilities.CustomButtonTextFont;
import com.intellidev.app.mashroo3k.uiutilities.CustomEditText;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;
import com.intellidev.app.mashroo3k.utilities.StaticValues;

import static com.intellidev.app.mashroo3k.utilities.StaticValues.Currency_LYBI_DINAR;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String TAG = CurrencyFragment.class.getSimpleName();

    CustomEditText et_cash;
    Spinner spConvertFrom;
    Spinner spConvertTo;
    CustomButtonTextFont btnConvert;

    String cash;
    String convertFrom = null;
    String convertTo = null;
    private ArrayAdapter<String> convertSpinnerAdapterFrom;
    private ArrayAdapter<String> convertSpinnerAdapterTo;

    final String [] unitSamples = {"virtual value",StaticValues.Currency_DOLLAR, StaticValues.Currency_EURO,
            StaticValues.Currency_EGY_POUND, StaticValues.Currency_KWAIT_DINAR,
            StaticValues.Currency_IRAQ_DINAR, Currency_LYBI_DINAR,
            StaticValues.Currency_EMARAT_DERHAM, StaticValues.Currency_SAUDI_RYAL,
            StaticValues.Currency_QATAR_RYAL, StaticValues.Currency_OMANI_RYAL};

    public CurrencyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrencyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrencyFragment newInstance(String param1, String param2) {
        CurrencyFragment fragment = new CurrencyFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_currency, container, false);
        et_cash = rootView.findViewById(R.id.et_cash);
        spConvertFrom = rootView.findViewById(R.id.spin_convert_from);
        spConvertTo = rootView.findViewById(R.id.spin_convert_to);
        btnConvert = rootView.findViewById(R.id.btn_convert);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String [] currenciesArrayFrom = {getString(R.string.convert_from),getString(R.string.dollar), getString(R.string.uoru),
                getString(R.string.egyp_pound), getString(R.string.kwait_dinar),
                getString(R.string.iraq_dinar), getString(R.string.lybi_dinar),
                getString(R.string.emarate_derham), getString(R.string.saudi_ryal),
                getString(R.string.qatar_ryal), getString(R.string.omani_ryal)};

        String [] currenciesArrayTo = {getString(R.string.convert_to),getString(R.string.dollar), getString(R.string.uoru),
                getString(R.string.egyp_pound), getString(R.string.kwait_dinar),
                getString(R.string.iraq_dinar), getString(R.string.lybi_dinar),
                getString(R.string.emarate_derham), getString(R.string.saudi_ryal),
                getString(R.string.qatar_ryal), getString(R.string.omani_ryal)};



        convertSpinnerAdapterFrom = new ArrayAdapter<String>(getActivity(),
                R.layout.view_row_spinner_currency, R.id.tv_currency, currenciesArrayFrom) {
            @Override
            public boolean isEnabled(int position) {
                if (convertTo != null && convertTo.equals(unitSamples[position]))
                    return false;

                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                CustomTextView tv = view.findViewById(R.id.tv_currency);
                if (position == 0)
                {
                    tv.setTextColor(getResources().getColor(R.color.boarder));
                } else
                {
                    tv.setTextColor(getResources().getColor(R.color.gray_blue));
                }
                return view;
            }
        };

        convertSpinnerAdapterTo = new ArrayAdapter<String>(getActivity(),
                R.layout.view_row_spinner_currency, R.id.tv_currency, currenciesArrayTo) {
            @Override
            public boolean isEnabled(int position) {
                if (convertFrom != null && convertFrom.equals(unitSamples[position]))
                    return false;
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                CustomTextView tv = view.findViewById(R.id.tv_currency);
                if (position == 0)
                {
                    tv.setTextColor(getResources().getColor(R.color.boarder));
                } else
                {
                    tv.setTextColor(getResources().getColor(R.color.gray_blue));
                }
                return view;
            }
        };
        convertSpinnerAdapterFrom.setDropDownViewResource(R.layout.view_dropdown_spinner_currency);
        convertSpinnerAdapterTo.setDropDownViewResource(R.layout.view_dropdown_spinner_currency);
        setupSpinners();
    }

    private void setupSpinners() {
        spConvertFrom.setAdapter(convertSpinnerAdapterFrom);
        spConvertTo.setAdapter(convertSpinnerAdapterTo);

        spConvertFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0)
                {
                    convertFrom = unitSamples[i];
                    Log.d(TAG, "convert from: "+ convertFrom);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spConvertTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (i>0)
                {
                    convertTo = unitSamples[i];
                    Log.d(TAG, "convert to: "+ convertTo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
