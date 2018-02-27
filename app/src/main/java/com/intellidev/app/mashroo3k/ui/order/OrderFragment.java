package com.intellidev.app.mashroo3k.ui.order;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.intellidev.app.mashroo3k.MvpApp;
import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.data.DataManager;
import com.intellidev.app.mashroo3k.ui.base.BaseFragment;
import com.intellidev.app.mashroo3k.uiutilities.CustomButtonTextFont;
import com.intellidev.app.mashroo3k.uiutilities.CustomEditText;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseFragment implements OrderMvpView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String projectName, mobileNum, fullName, money, email,
            projectSubject, productsAndServices, projectDetails, orderType ;

    CustomEditText etProjectName, etMobileNum, etFullName, etMoney,
            etEmail, etProjectSubject, etProductAndServices, etProjectDetails;

    Spinner spinOrderType;
    private ArrayAdapter<String> orderTypeSpinnerAdapter;
    CustomButtonTextFont btnSend;


    OrderPresenter presenter;

    private Handler mHandler;


    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1) {
        OrderFragment fragment = new OrderFragment();
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
        orderType = null;
        DataManager dataManager = ((MvpApp) getActivity().getApplication()).getDataManager();
        presenter = new OrderPresenter(dataManager);
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mHandler = new Handler(Looper.getMainLooper());
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        etProjectName = rootView.findViewById(R.id.et_project_name);
        etFullName = rootView.findViewById(R.id.et_full_name);
        etEmail = rootView.findViewById(R.id.et_email);
        etMobileNum = rootView.findViewById(R.id.et_mob_num);
        etProjectSubject = rootView.findViewById(R.id.et_project_subject);
        etMoney = rootView.findViewById(R.id.et_money);
        etProjectDetails = rootView.findViewById(R.id.et_project_details);
        etProductAndServices = rootView.findViewById(R.id.et_products_services);
        spinOrderType = rootView.findViewById(R.id.spin_order_type);

        String [] typesArray = {getString(R.string.order_type),getString(R.string.spin_feasib_study),getString(R.string.spin_product_line), getString(R.string.spin_work_plan)};
        orderTypeSpinnerAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_row_spinner_order_type, R.id.tv_order_type,typesArray) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                CustomTextView tv = (CustomTextView) view.findViewById(R.id.tv_order_type);
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
        orderTypeSpinnerAdapter.setDropDownViewResource(R.layout.view_dropdown_spinner_order_type);
        setupSpinner();
        btnSend = rootView.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectName = etProjectName.getText().toString();
                projectSubject = etProjectSubject.getText().toString();
                projectDetails = etProjectDetails.getText().toString();
                productsAndServices = etProductAndServices.getText().toString();
                mobileNum = etMobileNum.getText().toString();
                fullName = etFullName.getText().toString();
                money = etMoney.getText().toString();
                email = etEmail.getText().toString();

                presenter.sendOrder(projectName,orderType,mobileNum,fullName,money,email,
                        projectSubject,productsAndServices,projectDetails);
            }
        });
        return rootView;
    }

    private void setupSpinner ()
    {
        spinOrderType.setAdapter(orderTypeSpinnerAdapter);

        spinOrderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (i>0)
                {
                    if (selection.equals(getString(R.string.spin_feasib_study)))
                        orderType = getString(R.string.spin_feasib_study);
                    else if (selection.equals(getString(R.string.spin_product_line)))
                        orderType = getString(R.string.spin_product_line);
                    else if (selection.equals(getString(R.string.spin_work_plan)))
                        orderType = getString(R.string.spin_work_plan);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void showToast(int messageResId) {
        Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTextToast(final String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
