package com.example.myapplication.widget;

import android.content.Context;

/**
 * @Describe  Utils初始化相关
 * @Author puyantao
 * @Email 1067899750@qq.com
 * @create 2019/5/31 10:25
 */
public class Utils {
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
