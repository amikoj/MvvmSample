package cn.enjoytoday.baselibray.common;

import android.app.Application;
import android.content.Context;

/**
 * 作者： hfcai
 * 时间： 19-3-20
 * 描述：application基类
 */
public class BaseApplication extends Application {

    public static Context context = null;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public static Context getContext(){
        return context;
    }
}
