package com.intellidev.app.mashroo3k.ui.aboutus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intellidev.app.mashroo3k.R;
import com.intellidev.app.mashroo3k.uiutilities.CustomTextView;

import org.jsoup.Jsoup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AboutUsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutUsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CustomTextView tvAboutUs;



    public AboutUsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutUsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutUsFragment newInstance(String param1, String param2) {
        AboutUsFragment fragment = new AboutUsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        tvAboutUs = rootView.findViewById(R.id.tv_about_us);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String aboutUs = "<p>شركة مشروعك متخصصة فى مجال دراسات الجدوى وخطط الأعمال بتقديم الكثير من دراسات الجدوى والإستشارات الإقتصادية فى مختلف القطاعات (الصناعية / التجارية / الخدمية / الصحية / البلاستيكية / الإنشاء والتشييد / التدوير / المواد الغذائية / التعليم..وغيرها) وتستهدف مشروعك رجال الأعمال وأصحاب المشاريع والمستثمرين وطالبى التمويل والإستثمار ويتم إعداد دراسات الجدوى الإقتصادية من خلال مستشارين فهى تسعى بصورة مستمرة لتخصيص إستثمارات كبيرة بغرض تعزيز وتوسيع نطاق معرفتها بالتعاون مع جهات وأفراد متخصصين فى المجالات المالية والمحاسبية بحيث تكون من أكبر شركات الإستشارات الإقتصادية والمالية فى الشرق الأوسط .</p>\n<p>الرؤية:<br />\nأن نكون الكيان الإستشارى المتصدر بالشرق الأوسط فى مجال دراسات الجدوى وخطط الأعمال بحلول عام 2020.</p>\n<p>الرسالة:<br />\nتسعى شركة مشروعك لدراسات الجدوى وخطط الأعمال بالشرق الأوسط إلى العمل على خلق مجتمع قائم على تقديم منتج لخدمة المجتمع من خلال القطاعات الخدمية والصناعية وتقديم خدمة إستشارية ومهنية متطورة لرجال الأعمال المقبلين على البدء بمسيرة النجاح عن طريق مشروع جديد يضيف سهما جديدا فى عالم الأعمال بمنطقة الشرق الأوسط وتطوير  المشروعات القائمة فى المجالات المختلفة لنكون شركاء نجاح لجميع عملاؤنا.</p>";
        String finalAboutUs = Jsoup.parse(aboutUs).text();
        tvAboutUs.setText(finalAboutUs);
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
}
