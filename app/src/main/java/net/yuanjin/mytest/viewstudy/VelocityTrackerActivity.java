package net.yuanjin.mytest.viewstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

/**
 *  Created by zhan on 2017/12/14.
 */

public class VelocityTrackerActivity extends BasicActivity {

    private LinearLayout touchArea;
    VelocityTracker velocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_velocitytracker);

        getDefaultNavigation().setTitle("VelocityTrackerActivity");

        touchArea = (LinearLayout) findViewById(R.id.testarea);
        touchArea.setOnTouchListener(new View.OnTouchListener() {

            private int mPointerId;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("touch ACTION_DOWN ");
                        mPointerId = event.getPointerId(0);
                        velocityTracker = VelocityTracker.obtain();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        System.out.println("touch move ");
                        velocityTracker.addMovement(event);
                        velocityTracker.computeCurrentVelocity(1000);

                        int xVelocity = (int) velocityTracker.getXVelocity();
                        int yVelocity = (int) velocityTracker.getYVelocity();
                        System.out.println("xVelocity : " + xVelocity + "   ,yVelocity: " + yVelocity);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //回收
                        velocityTracker.clear();
                        velocityTracker.recycle();
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

    }
}
