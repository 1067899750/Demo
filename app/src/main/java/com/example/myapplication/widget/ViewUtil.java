package com.example.myapplication.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.myapplication.MyApplication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description：视图工具类
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-28  17:21
 */
public class ViewUtil {

    private static LayoutInflater inflater;

    public static View buildView(int layout) {
        return getInflater().inflate(layout, null);
    }

    private static LayoutInflater getInflater() {
        if (null == inflater) {
            inflater = LayoutInflater.from(MyApplication.getContext());
        }
        return inflater;
    }

    public static View buildView(int layout, ViewGroup parentView) {
        return getInflater().inflate(layout, parentView);
    }

    /**
     * 隐藏键盘
     *
     * @param v
     */
    public static void hideInputMethodManager(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) MyApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 显示软键盘
     *
     * @param v
     */
    public static void showInputMethodManager(final View v) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) MyApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(v, 0);
            }
        }, 300);
    }
    /**
     * 设置EditText 光标位置
     *
     * @param et
     * @return void
     * @throws
     */
    public static void setEditTextSelection(EditText et) {
        if (ValueUtil.isStrNotEmpty(et.getText().toString())) {
            et.setSelection(et.getText().length());
        }
    }
}
