package com.intellidev.app.mashroo3k.uiutilities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Ahmed Yehya on 27/02/2018.
 */

public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // only for gingerbread and newer versions
            this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Cairo-SemiBold.ttf"));
        }
    }
}
