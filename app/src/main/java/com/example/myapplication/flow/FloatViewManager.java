package com.example.myapplication.flow;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

/**
 * 
 * @description
 * @author puyantao
 * @date 2020/9/21 9:54
 */
public class FloatViewManager {
    private static volatile FloatViewManager sInstance;

    private ViewHolder mViewHolder = new ViewHolder();
    private FloatView.Builder mBuilder;
    private View.OnClickListener listener;

    private FloatViewManager() {

    }


    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public static FloatViewManager getInstance() {
        if (sInstance == null) {
            synchronized (FloatViewManager.class) {
                if (sInstance == null) {
                    sInstance = new FloatViewManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mViewHolder.mFloatView = View.inflate(context, R.layout.view_float, null);
        mBuilder = new FloatView.Builder()
                .setNeedNearEdge(true)
                .setView(mViewHolder.mFloatView);
    }

    public void attach(final Activity activity) {
        ViewGroup parentViewGroup = (ViewGroup) mViewHolder.mFloatView.getParent();
        if (parentViewGroup != null) {
            parentViewGroup.removeView(mViewHolder.mFloatView);
        }
        FloatView floatView = mBuilder.setActivity(activity).build();
        floatView.setCallback(new FloatView.IFloatViewCallback() {
            @Override
            public void downTouch() {
            }

            @Override
            public void upTouch() {
            }

            @Override
            public void onClick() {
                if (listener != null) {
                    listener.onClick(mViewHolder.mFloatView);
                }
            }
        });
    }

    public void show() {
        mViewHolder.mFloatView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mViewHolder.mFloatView.setVisibility(View.GONE);
    }

    static class ViewHolder {
        View mFloatView;
    }
}
