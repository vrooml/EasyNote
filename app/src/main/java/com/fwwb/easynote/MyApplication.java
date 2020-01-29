package com.fwwb.easynote;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import org.litepal.LitePal;

public class MyApplication extends Application{
    //单例模式
    private static MyApplication myApplication=null;
    public static Typeface boldSongTypeface;
    public static Typeface songTypeface;


    @Override
    public void onCreate(){
        super.onCreate();
        myApplication=this;

        //自定义字体初始化
        boldSongTypeface=Typeface.createFromAsset(getApplicationContext().getAssets(),"font/boldsongti.otf");
        songTypeface=Typeface.createFromAsset(getApplicationContext().getAssets(),"font/songti.otf");
        //百度地图初始化
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        //LitePal初始化
        LitePal.initialize(this);
        SQLiteDatabase database=LitePal.getDatabase();
    }

    public static MyApplication getInstance()
    {
        return myApplication;
    }
}
