package net.yuanjin.mytest.viewstudy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 *
 *  Created by wzq on 2018/1/9.
 */

public class MScrollView extends LinearLayout{

    private Scroller mscroller;

    public MScrollView(Context context) {
        super(context);
        init();
    }

    public MScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mscroller = new Scroller(getContext());
    }

    public void smoothScrollTo(int destX,int destY){
//        int sCrollX = getScrollX();
//        int delta = destX - sCrollX;

        mscroller.startScroll(0,destX,0,destY,1000);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (mscroller.computeScrollOffset()){
            scrollTo(mscroller.getCurrX(),mscroller.getCurrY());
            postInvalidate();
        }
    }

    private float downy;

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                downy = event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mscroller.startScroll(getScrollY(),0, (int) (event.getRawY() - downy),0,1000);
//                break;
//            case MotionEvent.ACTION_UP:
//                default:
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
}
