package net.yuanjin.mytest.viewstudy;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

/**
 *  手势检测，用以辅助检测用户的单击、滑动、长按、双击等行为
 *  Created by zhan on 2018/01/04.
 */

public class GestureDectectorActivity extends BasicActivity {

    private LinearLayout touchArea;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_velocitytracker);

        getDefaultNavigation().setTitle("GestureDectectorActivity");

        touchArea = (LinearLayout) findViewById(R.id.testarea);

        init();

    }

    private void init() {
        gestureDetector = new GestureDetector(GestureDectectorActivity.this,onGestureListener);
        gestureDetector.setIsLongpressEnabled(true);//解决长按屏幕后无法拖动的现象


        touchArea.setClickable(true);
        touchArea.setOnTouchListener(new View.OnTouchListener() {

            private int mPointerId;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        System.out.println("touch ACTION_DOWN ");
//
//
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        System.out.println("touch move ");
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        break;
//
//                    case MotionEvent.ACTION_CANCEL:
//                        break;
//                    default:
//                        break;
//                }
//
//                return true;

                boolean consume = gestureDetector.onTouchEvent(event);
                return consume;
            }
        });
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            //手指轻轻触摸屏幕的一瞬间
            System.out.println("--------------> onDown ");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            //手指轻轻触摸屏幕，尚未松开或拖动
            System.out.println("--------------> onShowPress ");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //手指（轻轻触摸屏幕后）松开，单击行为
            System.out.println("--------------> onSingleTapUp ");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            System.out.println("--------------> onScroll , distanceX = " + distanceX + " , distanceY = " + distanceY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            System.out.println("--------------> onLongPress ");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            System.out.println("--------------> onFling , velocityX = " + velocityX + " , velocityY = " + velocityY);
            return false;
        }
    };
}
