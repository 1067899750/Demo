package com.example.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.widget.ScreenUtils;
import com.example.myapplication.widget.Titlebar;
import com.example.myapplication.widget.ViewUtil;
import io.reactivex.disposables.CompositeDisposable;


/**
 * @Describe
 * @Author puyantao
 * @Email 1067899750@qq.com
 * @create 2019/5/31 11:27
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Titlebar titleBar;
    protected LinearLayout baseLayout;
    protected Activity context;
    protected String token;
    protected MyLinearLayout basetouch;
    protected String className;
    protected CompositeDisposable mCompositeDisposable;
    protected final int mScreenWidth = ScreenUtils.getScreenWidth();
    protected final int mScreenHeight = ScreenUtils.getScreenHeight();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //防止设置手机系统字体，引起app 重装加载报错
        if (null != savedInstanceState)
            savedInstanceState = null;
        super.onCreate(savedInstanceState);
        context = this;
        MyApplication.addActivity(this);
        className = getClass().getName();

        setBaseView();
        initView();
        addTouchView();
        fillData();
    }

    /**
     * 添加移动布局
     */
    private void addTouchView() {
        //在屏幕decorView中添加乬
        View decorView = getWindow().getDecorView();
        FrameLayout contentParent = decorView.findViewById(android.R.id.content);
        final View mView = LayoutInflater.from(context).inflate(R.layout.layout_suspend, null);
        basetouch = mView.findViewById(R.id.basetouch);
         final View base_bg_view = mView.findViewById(R.id.base_bg_view);
        //蒙层点击事件
        base_bg_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basetouch.setClose();
                view.setVisibility(View.GONE);
            }
        });
        //监听事件
        basetouch.addTabClickListener(new MyLinearLayout.OnTabClickListener() {
            @Override
            public void OnTabClick(int position) {
                //球关闭的数量,设置不关闭当前我的、世界杯、主界面
                Log.d("--> 选中小球的位置: ", position + "");
                Toast.makeText(context, "选中小球的位置为:" + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnShow() {
                base_bg_view.setVisibility(View.VISIBLE);
            }

            @Override
            public void OnClose() {
                base_bg_view.setVisibility(View.GONE);
            }
        });

        contentParent.addView(mView);

    }


    protected abstract void initView();

    protected abstract void fillData();

    public void setBaseView() {

    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        ViewUtil.buildView(layoutResID, baseLayout);
    }

    @Override
    public void finish() {
        super.finish();
    }


}











