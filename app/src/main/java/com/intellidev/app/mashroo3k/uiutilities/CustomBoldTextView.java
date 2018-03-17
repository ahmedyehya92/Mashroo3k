package com.intellidev.app.mashroo3k.uiutilities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ahmed Yehya on 16/03/2018.
 */

public class CustomBoldTextView extends TextView {
    public CustomBoldTextView(Context context) {
        super(context);
    }

    public CustomBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Cairo-Bold.ttf"));
        }
    }
}
