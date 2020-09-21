package com.example.myapplication;

import android.view.View;
import android.widget.Toast;

import com.example.myapplication.flow.FloatViewManager;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/9/21 9:55
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }



    @Override
    protected void fillData() {
        FloatViewManager.getInstance().init(this);
        FloatViewManager.getInstance().attach(this);
        FloatViewManager.getInstance().show();
        FloatViewManager.getInstance().setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"点击", Toast.LENGTH_LONG).show();
            }
        });
    }
}
