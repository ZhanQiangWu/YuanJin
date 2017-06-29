package net.yuanjin.mytest.customwidget.paint_canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

/**
 *  路径与文字
 *  参考资料： http://blog.csdn.net/harvic880925/article/details/38926877
 *  Created by wzq on 2017/6/27.
 */

public class PathAndTextView extends View{

    public PathAndTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Paint paint = initPathPaint();

        //drawLinePath(paint,canvas);
        //drawRectPath(paint,canvas);
        //drawRectPathWithText(paint,canvas);
        //drawRoundRect(paint,canvas);
        //drawCircle(paint,canvas);
        //drawOvalPath(paint,canvas);
        //drawArcPath(paint,canvas);

        Paint paint = initTextPaint();


    }

    private Paint initTextPaint() {
        Paint paint = new Paint();
        //普通设置
        paint.setStrokeWidth (5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setStyle(Paint.Style.FILL);//绘图样式，对于设文字和几何图形都有效
        paint.setTextAlign(Paint.Align.CENTER);//设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
        paint.setTextSize(12);//设置文字大小

        //样式设置
        paint.setFakeBoldText(true);//设置是否为粗体文字
        paint.setUnderlineText(true);//设置下划线
        paint.setTextSkewX((float) -0.25);//设置字体水平倾斜度，普通斜体字是-0.25
        paint.setStrikeThruText(true);//设置带有删除线效果

        //其它设置
        paint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变

        return paint;
    }

    private Paint initPathPaint(){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        return paint;
    }

    /**
     * 弧形
     * @param paint
     * @param canvas
     */
    private void drawArcPath(Paint paint,Canvas canvas){
        Path path = new Path();
        RectF rectF = new RectF(100,100,300,200);
        path.addArc(rectF,0,90);
        canvas.drawPath(path, paint);
    }

    /**
     * 椭圆
     * @param paint
     * @param canvas
     */
    private void drawOvalPath(Paint paint,Canvas canvas){
        Path path = new Path();
        RectF rectF = new RectF(100,100,300,200);
        path.addOval(rectF,Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    /**
     * 圆
     * @param paint
     * @param canvas
     */
    private void drawCircle(Paint paint,Canvas canvas){
        Path path = new Path();
        path.addCircle(200, 200, 100, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    /**
     * 圆角矩形 - 可自定义每个圆角的弧度
     * @param paint
     * @param canvas
     */
    private void drawRoundRect(Paint paint, Canvas canvas) {
        Path path = new Path();
        RectF rect1 =  new RectF(50, 50, 240, 200);
        path.addRoundRect(rect1, 10, 15 , Path.Direction.CCW);

        RectF rect2 =  new RectF(290, 50, 480, 200);
        float radii[] ={10,15,20,25,30,35,40,45};
        path.addRoundRect(rect2, radii, Path.Direction.CCW);

        canvas.drawPath(path, paint);
    }

    /**
     * 矩形路径 + 文字
     * @param paint
     * @param canvas
     */
    private void drawRectPathWithText(Paint paint, Canvas canvas) {
        //逆时钟生长
        Path ccwRectPath = new Path();
        RectF rectF = new RectF(50, 50, 240, 200);
        ccwRectPath.addRect(rectF, Path.Direction.CCW);

        //第二个顺向生成
        Path CWRectpath = new Path();
        RectF rect2 =  new RectF(290, 50, 480, 200);
        CWRectpath.addRect(rect2, Path.Direction.CW);

        canvas.drawPath(ccwRectPath,paint);
        canvas.drawPath(CWRectpath,paint);

        //依据路径写出文字
        String text = "风萧萧兮易水寒，壮士一去兮不复返";
        paint.setColor(Color.GRAY);
        paint.setTextSize(35);

        canvas.drawTextOnPath(text,ccwRectPath,0,18,paint);
        canvas.drawTextOnPath(text,CWRectpath,0,18,paint);
    }

    /**
     * 矩形路径
     * @param paint
     * @param canvas
     */
    private void drawRectPath(Paint paint, Canvas canvas) {
        //逆时钟生长
        Path ccwRectPath = new Path();
        RectF rectF = new RectF(50, 50, 240, 200);
        ccwRectPath.addRect(rectF, Path.Direction.CCW);

        //第二个顺向生成
        Path CWRectpath = new Path();
        RectF rect2 =  new RectF(290, 50, 480, 200);
        CWRectpath.addRect(rect2, Path.Direction.CW);

        canvas.drawPath(ccwRectPath,paint);
        canvas.drawPath(CWRectpath,paint);
    }

    /**
     * 直线路径
     * @param paint
     * @param canvas
     */
    private void drawLinePath(Paint paint, Canvas canvas) {
        Path path = new Path();
        path.moveTo(100,100);//设置起点
        path.lineTo(100,200);
        path.lineTo(300,200);
        path.close();//首位是否闭合

        canvas.drawPath(path,paint);
    }
}
