package com.example.myapplication.widget;

import java.io.Serializable;

/**
 * Description：静态常量类
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-28  17:23
 */
public class Constant {

    /**
     * 球菜单数量
     */
    public enum FloatNum {
        FIVE(5),
        SIX(6);
        private final int value;

        public int getValue() {
            return value;
        }

        private FloatNum(int value) {
            this.value = value;
        }

    }

}
