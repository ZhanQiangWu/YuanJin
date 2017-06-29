package net.yuanjin.mytest.customwidget.paint_canvas;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 写字板 - Path.rQuadTo()，利用贝赛尔曲线实现平滑过渡
 * 参考资料：http://blog.csdn.net/harvic880925/article/details/50995587
 * Created by wzq on 2017/6/28.
 */

public class WordPadWaveView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength = 1000;
    private int dx = 0;
    ValueAnimator animator;

    public WordPadWaveView(Context context) {
        super(context);

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        int originY = 300;
        int halfWaveLen = mItemWaveLength / 2;
        mPath.moveTo(-mItemWaveLength + dx, originY);
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -50, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, 50, halfWaveLen, 0);
        }

        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void startAnim() {
        animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                System.out.println("------------>dx = " + dx);
                invalidate();
            }
        });
        animator.start();//在项目中动画开启后不再使用时需要手动关闭
    }

    public void reset() {
        mPath.reset();
        invalidate();
        if (animator.isStarted()) animator.cancel();
    }



}
