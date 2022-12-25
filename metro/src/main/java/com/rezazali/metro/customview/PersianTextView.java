package com.rezazali.metro.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class PersianTextView extends AppCompatTextView {
    public PersianTextView(@NonNull Context context) {
        super(context);
        setFont(context);
    }

    private void setFont(@NonNull Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/BNAZANIN.TTF");
        setTypeface(typeface);
    }

    public PersianTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public PersianTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }
}
