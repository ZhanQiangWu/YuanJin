package net.yuanjin.mytest.viewstudy.scrolltest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount ; i++){
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                //为ScrollerLayout中的每一个子控件在水平方向上进行布局
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            //初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                //新发生的在X轴上的位移
                int scrolledX = (int) (mXLastMove - mXMove);
                /**
                 * getScrollX():在X轴上已发生的位移
                 */
                if (getScrollX() + scrolledX < leftBorder) {
                    //发生的位移小于左边界则实际不移动
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
                    //向左发生的位移大于 左边界 - 一个宽度，则认为到达最左位置，此时则不再发生移动
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                /**
                 * 弹性滑动方式一：通过Scroller
                 */
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                //mScroller.startScroll(getScrollX(), 0, dx, 0);
                //invalidate();//刷新界面

                /**
                 * 弹性滑动方式二：通过动画
                 */
                final int startX = getScrollX();
                final int deltaX = dx;
                final ValueAnimator animator = ValueAnimator.ofInt(0,1).setDuration(1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float fraction = animator.getAnimatedFraction();
                        ScrollerLayout.this.scrollTo((int) (startX + deltaX * fraction),ScrollerLayout.this.getScrollY());
                    }
                });
                animator.start();

//                /**
//                 * 弹性滑动方式三：通过动画
//                 * 验证未通过
//                 */
//                ObjectAnimator.ofFloat(this,"translationX",startX,dx).setDuration(100).start();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            Log.d("mytest","---------> computeScroll : getCurrX() = " + mScroller.getCurrX() + " , mScroller.getCurrY() = " + mScroller.getCurrY());
            postInvalidate();
        }
    }

/**
     *
     * @param startX : 滚动开始时X的坐标
     * @param startY : 滚动开始时Y的坐标
     * @param dx : 横向滚动的距离，正值表示向左滚动
     * @param dy : 纵向滚动的距离，正值表示向上滚动
    public void startScroll(int startX, int startY, int dx, int dy) {
**/

}

/**
 *  Scroller 的基本用法
 *  1. 创建Scroller的实例
 *  2. 调用startScroll()方法来初始化滚动数据并刷新界面
 *  3. 重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
 */
