package com.intellidev.app.mashroo3k.uiutilities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Ahmed Yehya on 23/02/2018.
 */

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
         if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Cairo-SemiBold.ttf"));
         }

    }
}
