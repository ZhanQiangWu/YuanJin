package net.yuanjin.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *  自定义ViewPager
 *  Created by WuZhanQiang on 2016/11/14.
 */

public class CustomViewPager extends ViewPager{

    private boolean disableShuffle;//滑动禁止

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 禁止滑动：true
     * @param disableShuffle
     */
    public void setDisableShuffle(boolean disableShuffle) {
        this.disableShuffle = disableShuffle;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (disableShuffle){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (disableShuffle){
            return false;
        }
        return super.onTouchEvent(ev);
    }
}
