package net.yuanjin.mytest.viewstudy.scrolltest;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 *  Created by guolin on 16/1/12.
 */
public class ScrollerLayout extends ViewGroup {

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove;

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;


    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //第一步，创建Scroller 的实例
        mScroller = new Scroller(getContext());
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        //获取TouchSlop 的值
        mTouchSlop = configuration.getScaledPagingTouchSlop();//ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


}

/**
 *  Scroller 的基本用法
 *  1. 创建Scroller的实例
 *  2. 调用startScroll()方法来初始化滚动数据并刷新界面
 *  3. 重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
 */
