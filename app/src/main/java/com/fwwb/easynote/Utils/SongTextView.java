package com.fwwb.easynote.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.fwwb.easynote.Activitys.MainActivity;
import com.fwwb.easynote.MyApplication;

@SuppressLint("AppCompatCustomView")
public class SongTextView extends TextView{

    public SongTextView(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public void setTypeface(Typeface typeface){
        super.setTypeface(MyApplication.songTypeface);

    }
}
