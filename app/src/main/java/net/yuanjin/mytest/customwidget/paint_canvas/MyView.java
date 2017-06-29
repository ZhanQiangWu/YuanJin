package net.yuanjin.mytest.customwidget.paint_canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;

/**
 *  视图类
 *  Created by wzq on 2017/6/27.
 */

public class MyView  extends View{

    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置画笔的基本属性
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.RED);//设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度
        paint.setShadowLayer(10,15,15,Color.GREEN);//设置阴影

        //设置画布背景颜色
        canvas.drawRGB(255,255,255);

        //画圆
        //canvas.drawCircle(390,200,150,paint);

        //画直线
        //canvas.drawLine(100,100,200,200,paint);

        //画多条直线
        //float[] pts = new float[]{10,10,100,100,200,200,400,400,500,500,600,600};
        //canvas.drawLines(pts,paint);

        //画点
        //canvas.drawPoint(100,100,paint);

        //画多个点
        //float [] fts = new float[]{10,10,100,100,200,200,400,400};
        //canvas.drawPoints(fts,paint);
        //canvas.drawPoints(fts,2,4,paint);

        //画矩形
        //canvas.drawRect(100,100,200,200,paint);
        //Rect rect = new Rect(300,100,400,200);
        //canvas.drawRect(rect,paint);
        //RectF rectF = new RectF(500,100,600,200);
        //canvas.drawRect(rectF,paint);

        //画圆角矩形
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    canvas.drawRoundRect(100,200,400,400,20,20,paint);
        //}

        //画弧
        RectF rect1 = new RectF(100, 10, 300, 100);
        canvas.drawArc(rect1, 0, 90, true, paint);
    }
}
