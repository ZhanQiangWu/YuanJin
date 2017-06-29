package net.yuanjin.mytest.customwidget.paint_canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/**
 *  写字板 - 使用 quadto 来画，利用贝赛尔曲线实现平滑过渡
 *  参考资料：http://blog.csdn.net/harvic880925/article/details/50995587
 *  Created by wzq on 2017/6/28.
 */

public class WordPadView2 extends View{

    private Path mPath = new Path();
    Paint paint ;
    private float preX,preY;

    public WordPadView2(Context context) {
        super(context);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(),event.getY());
                preX = event.getX();
                preY = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                float endX = (preX + event.getX())/2;
                float endY = (preY + event.getY())/2;
                mPath.quadTo(preX,preY,endX,endY);

                preX = event.getX();
                preY = event.getY();

                postInvalidate();
                break;


        }

        return super.onTouchEvent(event);
    }

    public void reset(){
        mPath.reset();
        invalidate();
    }
}
