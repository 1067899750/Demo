package com.example.myapplication.widget;

import java.io.Serializable;

/**
 * touchview
 * Created by huangb on 2018/4/12.
 */

public class TouchPositionBean implements Serializable {
    private float x;
    private float y;
    private String className;

    public TouchPositionBean(String name, float x, float y) {
        this.x = x;
        this.y = y;
        this.className = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
