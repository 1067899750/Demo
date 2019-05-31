package com.example.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.example.myapplication.widget.Utils;
import com.example.myapplication.widget.ValueUtil;

import java.util.Stack;

/**
 * @Describe
 * @Author puyantao
 * @Email 1067899750@qq.com
 * @create 2019/5/31 10:27
 */
public class MyApplication extends Application {
    private static Context context;
    public static Stack<BaseActivity> activityList = new Stack<>();


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // 加载系统默认设置，字体不随用户设置变化
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());

        Utils.init(this);


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    public static Context getContext() {
        return context;
    }


    public static void addActivity(BaseActivity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }


    /**
     * 结束页面返回
     */
    public static void toMainActivity() {
        try {
            for (Activity each : activityList) {
                if (each instanceof MainActivity) {
                } else {
                    each.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeAllActivity() {
        try {
            for (Activity each : activityList) {
                each.finish();
            }
            removeAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 结束指定类名的Activity
     */
    public static void finishSingActivity(Class<?> cls) {
        for (BaseActivity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                finishSingActivity(activity);
                return;
            }
        }
    }
    /**
     * 是否包含指定类名的Activity
     */
    public static boolean hasActivity(Class<?> cls) {
        boolean has=false;
        if(ValueUtil.isListEmpty(activityList)){
            return false;
        }else {
            for (BaseActivity activity : activityList) {
                if (activity.getClass().equals(cls)) {
                    has=true;
                    break;
                }
            }
        }
        return has;
    }
    /**
     * 结束指定的Activity
     */
    public static void finishSingActivity(Activity activity) {
        if (activity != null) {
            activityList.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    public static void removeAllActivity() {
        try {
            activityList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}











