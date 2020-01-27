package com.fwwb.easynote;

import android.app.Application;
import android.graphics.Typeface;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application{
    //单例模式
    private static MyApplication myApplication=null;
    public static Typeface boldSongTypeface;
    public static Typeface songTypeface;


    @Override
    public void onCreate(){
        super.onCreate();
        myApplication=this;

        boldSongTypeface=Typeface.createFromAsset(getApplicationContext().getAssets(),"font/boldsongti.otf");
        songTypeface=Typeface.createFromAsset(getApplicationContext().getAssets(),"font/songti.otf");
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public static MyApplication getInstance()
    {
        return myApplication;
    }
}
