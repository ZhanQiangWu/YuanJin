package net.yuanjin.mytest.viewstudy.scrolltest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import net.yuanjin.R;

/**
 * @author zhan
 * @date 2018/2/4
 */

public class ViewDragHelperLayout extends LinearLayout{

    ViewDragHelper viewDragHelper;

    /**
     * 可以随意被拖动位置
     */
    View dragView;
    /**
     * 只能从ViewGroup左侧拖动
     */
    View edgeDragView;

    /**
     * 拖动释放之后会回到原始位置
     */
    View autoBackView;

    int autoBackViewOriginLeft;
    int autoBackViewOriginTop;

    public ViewDragHelperLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /**
             * 返回true则表示捕获相关view
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == dragView || child == autoBackView;
            }

            /**
             * 计算view垂直方向的位置
             * @param child
             * @param top y轴坐标（相对于ViewGroup）
             * @param dy
             * @return
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (top > (getHeight() - child.getMeasuredHeight())){
                    top = getHeight() - child.getMeasuredHeight();
                }else if (top < 0 ){
                    top = 0;
                }
                return top;
            }

            /**
             *  计算view水平方向的位置
             * @param child
             * @param left view左边界坐标
             * @param dx
             * @return
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left > (getWidth() - child.getMeasuredWidth())){
                    //右侧边界
                    left = getWidth() - child.getMeasuredWidth();
                }else if (left < 0){
                    //左侧边界
                    left = 0;
                }
                //System.out.println("--------->clampViewPositionHorizontal  left : " + left + " , child.getMeasuredWidth() : " + child.getMeasuredWidth() );
                return left;
            }

            /**
             * 当前被捕获的view释放之后回调
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == autoBackView){
                    viewDragHelper.settleCapturedViewAt(autoBackViewOriginLeft,autoBackViewOriginTop);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                //主动去捕获第二个View：edgeDragView
                viewDragHelper.captureChildView(edgeDragView,pointerId);
            }

            /**
             * 重写getViewVerticalDragRange 和 getViewHorizontalDragRange 使得view可以被点击
             * @param child
             * @return
             */
            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }
        });

        //设置左边缘可以被Drag
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    /**
     * 重写使子view可以点击
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = findViewById(R.id.dragView);
        edgeDragView = findViewById(R.id.edgeDragView);
        autoBackView = findViewById(R.id.autoBackView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        autoBackViewOriginLeft = autoBackView.getLeft();
        autoBackViewOriginTop = autoBackView.getTop();
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    /**
     * Factory method to create a new ViewDragHelper.
     *
     * @param forParent Parent view to monitor
     * @param sensitivity Multiplier for how sensitive the helper should be about detecting
     *                    the start of a drag. Larger values are more sensitive. 1.0f is normal.
     *                    设置touchSlop，传入越大，touchSlop越小
     * @param cb Callback to provide information and receive events
     * @return a new ViewDragHelper instance
     */
//    public static ViewDragHelper create(ViewGroup forParent, float sensitivity, ViewDragHelper.Callback cb) {
//    }
}
