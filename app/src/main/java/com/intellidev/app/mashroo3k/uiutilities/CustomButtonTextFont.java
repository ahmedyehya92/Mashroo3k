package com.intellidev.app.mashroo3k.uiutilities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Ahmed Yehya on 24/02/2018.
 */

public class CustomButtonTextFont extends Button {
    public CustomButtonTextFont(Context context) {
        super(context);
    }

    public CustomButtonTextFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // only for gingerbread and newer versions
            this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Cairo-SemiBold.ttf"));
        }
    }
}
