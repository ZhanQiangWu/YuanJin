package net.yuanjin.mytest.customwidget.paint_canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;

/**
 *  颜色矩阵变换
 *  Created by wzq on 2017/6/28.
 */

public class ColorMatrixView extends View{

    private Paint paint;

    public ColorMatrixView(Context context) {
        super(context);
        paint = new Paint();
        paint.setARGB(255,200,100,100);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        singleColorOutPut(canvas,paint);

    }

    /**
     * 单个颜色的蓝色通道输出
     * @param canvas
     * @param paint
     */
    private void singleColorOutPut(Canvas canvas,Paint paint){
        //绘制原始位图
        canvas.drawRect(0,0,250,300,paint);

        canvas.translate(300,0);

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0
        });
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawRect(0,0,250,300,paint);
    }
}
