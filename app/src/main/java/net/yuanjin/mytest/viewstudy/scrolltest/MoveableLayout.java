package net.yuanjin.mytest.viewstudy.scrolltest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * @author wzq
 * @date 2018/2/2
 * 参考资料：使用了 NineOldAndroids  https://github.com/JakeWharton/NineOldAndroids
 */

public class MoveableLayout extends LinearLayout{

    public MoveableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int mLastX;
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.d("mytest","move , deltaX = " + deltaX + " , deltaY = " + deltaY);
                int translationX = (int) (ViewHelper.getTranslationX(this) + deltaX);
                int translationY = (int) (ViewHelper.getTranslationY(this) + deltaY);
                ViewHelper.setTranslationX(this,translationX);
                ViewHelper.setTranslationY(this,translationY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }
}
