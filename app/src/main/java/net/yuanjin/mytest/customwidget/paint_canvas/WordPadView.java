package net.yuanjin.mytest.customwidget.paint_canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/**
 *  写字板 - 使用 lineto 来画，存在着曲线不平滑的问题
 *  参考资料：http://blog.csdn.net/harvic880925/article/details/50995587
 *  Created by wzq on 2017/6/28.
 */

public class WordPadView extends View{

    private Path mPath = new Path();
    private Paint paint;

    public WordPadView(Context context) {
        super(context);
        paint = new Paint();
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
                return true;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(),event.getY());
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
