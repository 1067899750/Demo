package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.myapplication.widget.Constant;
import com.example.myapplication.widget.DensityUtil;
import com.example.myapplication.widget.ScreenUtils;
import com.example.myapplication.widget.TimeUtils;
import com.example.myapplication.widget.TouchPositionBean;
import com.example.myapplication.widget.ValueUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangb on 2018/4/6.
 */

public class MyLinearLayout extends FrameLayout {

    /**
     * 图片数组
     */
    private List<View> mImageList = new ArrayList<>();

    public MyTouchView myTouchView;

    private boolean isShowCir = false;
    private int floatNum = 5;//FloatManager.getInstance().getState()
    private int mScreenWidth = ScreenUtils.getScreenWidth();
    private int mScreenHeight = ScreenUtils.getScreenHeight();
    /**
     * 半圆半径
     */
    private int mRadius = floatNum == Constant.FloatNum.FIVE.getValue() ? DensityUtil.dp2px(80) : DensityUtil.dp2px(90);
    /**
     * 距离底部间距
     */
    private int bottomPosition = floatNum == Constant.FloatNum.FIVE.getValue() ? DensityUtil.dp2px(130) : DensityUtil.dp2px(140);

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        myTouchView = new MyTouchView(getContext());
        myTouchView.setLayoutParams(new LinearLayout.LayoutParams(DensityUtil.dp2px(58), DensityUtil.dp2px(58)));
        setBallBackground(floatNum);
        myTouchView.setBottionPosition(bottomPosition);
        myTouchView.setNomalAddress();
        addView(myTouchView);
        setImageIntList();
        myTouchView.addOnTouchLogoClicklistener(new MyTouchView.OnTouchLogoClickListener() {
            @Override
            public void onTouchClick(float v, float v1) {
                if (!TimeUtils.isCanClick()) {
                    return;
                }
                myTouchView.bringToFront();
                if (isShowCir) {
                    closeCircleMenu(null);
                    return;
                }
                showCircleMenu();
            }

            @Override
            public void onMove() {
                if (isShowCir) {
                    closeCircleMenu(null);
                }
            }

            @Override
            public void OnViewMove(MotionEvent event) {
                if (isShowCir) {
                    closeCircleMenu(null);

                }
            }

            @Override
            public void OnOutMove() {
                showCircleMenu();
            }
        });


    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearLayout(Context context) {
        this(context, null);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 显示圆形菜单
     */
    private void showCircleMenu() {
        final boolean[] isAnimation = {false};
        myTouchView.closeMove();
        boolean isLeft = myTouchView.getX() < mScreenWidth / 2;
        int index = mImageList.size() - 1;
        for (int i = 0; i < mImageList.size(); i++) {
            PointF point = new PointF();
            //根据菜单个数计算每个菜单之间的间隔角度*
            int avgAngle = (180 / (mImageList.size() - 1));
            //根据间隔角度计算出每个菜单相对于水平线起始位置的真实角度
            //小于一半 -90 或者 90
            int angle = avgAngle * i + (isLeft ? -90 : 90);
            point.x = myTouchView.getX() + (float) Math.cos(angle * (Math.PI / 180)) * mRadius;
            point.y = myTouchView.getY() + (float) Math.sin(angle * (Math.PI / 180)) * mRadius;
            /**第五步，根据坐标执行位移动画**/
            /**
             * 第一个参数代表要操作的对象
             * 第二个参数代表要操作的对象的属性
             * 第三个参数代表要操作的对象的属性的起始值
             * 第四个参数代表要操作的对象的属性的终止值
             */
            ObjectAnimator objectAnimatorA = ObjectAnimator.ofFloat(mImageList.get(isLeft ? i : index - i), "alpha", 0f, 1f);
            ObjectAnimator animatorOpen = ObjectAnimator.ofFloat(mImageList.get(isLeft ? i : index - i), "rotation", 0, 180, 0);
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mImageList.get(isLeft ? i : index - i), "translationX", myTouchView.getX(), point.x);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mImageList.get(isLeft ? i : index - i), "translationY", myTouchView.getY(), point.y);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(230);
            animatorSet.play(objectAnimatorX).with(animatorOpen).with(objectAnimatorY).with(objectAnimatorA);
            animatorSet.start();
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!isAnimation[0]) {
                        isAnimation[0] = true;
                        myTouchView.setBackgroundResource(R.drawable.main_suspend_un);
                        for (int j = 0; j < mImageList.size(); j++) {
                            mImageList.get(j).setEnabled(true);
                        }
                        myTouchView.setCanTouch(false);
                        myTouchView.bringToFront();
                        if (mOnTabClickListener != null) {
                            mOnTabClickListener.OnShow();
                        }
                        isShowCir = true;
                    }
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    myTouchView.setCanTouch(false);
                }
            });
        }

    }

    /**
     * 关闭圆形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void closeCircleMenu(View v) {
        final boolean[] isAnimation = {false};
        myTouchView.startTime();
        for (int j = 0; j < mImageList.size(); j++) {
            mImageList.get(j).setEnabled(false);
        }
        int index = mImageList.size() - 1;
        for (int i = 0; i < mImageList.size(); i++) {
            PointF point = new PointF();
            int avgAngle = (180 / (mImageList.size() - 1));
            boolean isLeft = myTouchView.getX() < mScreenWidth / 2;
            int angle = avgAngle * i + (isLeft ? -90 : 90);
            point.x = myTouchView.getX() + (float) Math.cos(angle * (Math.PI / 180)) * mRadius;
            point.y = myTouchView.getY() + (float) Math.sin(angle * (Math.PI / 180)) * mRadius;

            ObjectAnimator objectAnimatorA = ObjectAnimator.ofFloat(mImageList.get(isLeft ? i : index - i), "alpha", 1f, 0f);
            ObjectAnimator animatorClose = ObjectAnimator.ofFloat(mImageList.get(isLeft ? i : index - i), "rotation", 0, 180, 0);
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mImageList.get(isLeft ? i : index - i), "translationX", point.x, myTouchView.getX());
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mImageList.get(isLeft ? i : index - i), "translationY", point.y, myTouchView.getY());
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(230);
            animatorSet.play(objectAnimatorX).with(animatorClose).with(objectAnimatorY).with(objectAnimatorA);
            animatorSet.start();
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!isAnimation[0]) {
                        isAnimation[0] = true;
                        setBallBackground(floatNum);
                        myTouchView.setCanTouch(true);
                        myTouchView.bringToFront();
                        if (mOnTabClickListener != null) {
                            mOnTabClickListener.OnClose();
                        }
                    }
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    myTouchView.setCanTouch(false);
                }
            });
        }
        isShowCir = false;
        if (v != null && mOnTabClickListener != null) {
            mOnTabClickListener.OnTabClick((Integer) v.getTag());
        }
    }


    /**
     * 设置扇形按钮图片
     */
    public void setImageIntList() {
        try {
            List<View> imageViewList = new ArrayList<>();

            for (int i = 0; i < floatNum; i++) {
                View mView = LayoutInflater.from(getContext()).inflate(R.layout.item_suspend, this, false);
                switch (i) {
                    case 0:
                        ((ImageView) mView.findViewById(R.id.image_view)).setImageResource(R.mipmap.home_float_button_quotes);
                        ((TextView) mView.findViewById(R.id.tv_text)).setText(R.string.menu_market);
                        break;
                    case 1:
                        ((ImageView) mView.findViewById(R.id.image_view)).setImageResource(R.mipmap.home_flaot_button_spot);
                        ((TextView) mView.findViewById(R.id.tv_text)).setText(R.string.spot_goods);
                        break;
                    case 2:
                        ((ImageView) mView.findViewById(R.id.image_view)).setImageResource(R.mipmap.home_float_alphametal);
                        ((TextView) mView.findViewById(R.id.tv_text)).setText("Alpha");
                        break;
                    case 3:
                        ((ImageView) mView.findViewById(R.id.image_view)).setImageResource(R.mipmap.home_float_button_news);
                        ((TextView) mView.findViewById(R.id.tv_text)).setText(R.string.menu_information);
                        break;
                    case 4:
                        ((ImageView) mView.findViewById(R.id.image_view)).setImageResource(R.mipmap.home_flaot_button_data);//快报
                        ((TextView) mView.findViewById(R.id.tv_text)).setText(R.string.menu_main_flash);
                        break;
                }
                addView(mView);
                mView.setTag(i);
                mView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TimeUtils.isCanClick()) {
                            return;
                        }
                        if (isShowCir) {
                            closeCircleMenu(view);
                        }

                    }
                });
                imageViewList.add(mView);
                //放置在底部
                mView.setX(mScreenWidth);
                mView.setY(mScreenHeight);
            }
            this.mImageList = imageViewList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBallBackground(int num) {
        this.floatNum = num;
        this.mRadius = floatNum == Constant.FloatNum.FIVE.getValue() ? DensityUtil.dp2px(80) : DensityUtil.dp2px(90);
        this.bottomPosition = floatNum == Constant.FloatNum.FIVE.getValue() ? DensityUtil.dp2px(130) : DensityUtil.dp2px(140);
        if (floatNum == 5) {
            myTouchView.setBackgroundResource(R.drawable.main_suspend);
        }
        setImageIntList();
    }


    //关闭
    public void setClose() {
        if (isShowCir) {
            closeCircleMenu(null);
        }
    }


    /**
     * 悬浮球事件
     */
    public interface OnTabClickListener {

        /**
         * tab点击
         *
         * @param position 下标
         */
        void OnTabClick(int position);

        /**
         * 扇形显示
         */
        void OnShow();

        /**
         * 扇形关闭
         */
        void OnClose();
    }

    private OnTabClickListener mOnTabClickListener;

    public void addTabClickListener(OnTabClickListener onTabClickListener) {
        if (onTabClickListener != null) {
            mOnTabClickListener = onTabClickListener;
        }
    }

    /**
     * 设置touch位置
     */
    public void setTouchXY(TouchPositionBean bean) {
        myTouchView.setX(bean.getX());
        myTouchView.setY(bean.getY());
    }
}
