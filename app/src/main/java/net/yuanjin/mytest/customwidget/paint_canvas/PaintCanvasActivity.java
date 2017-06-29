package net.yuanjin.mytest.customwidget.paint_canvas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

/**
 *  自定义绘图学习
 *  Created by wzq on 2017/6/27.
 */

public class PaintCanvasActivity extends BasicActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintcanvas);

        getDefaultNavigation().setTitle(this.getClass().getSimpleName());

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.paintcanvas_root);
        //frameLayout.addView(new MyView(this));
        //frameLayout.addView(new PathAndTextView(this));
        //frameLayout.addView(new BezierView(this));
        //frameLayout.addView(new WordPadView(this));


//        WordPadWaveView myview = new WordPadWaveView(this);
//        frameLayout.addView(myview);
//        myview.startAnim();

        frameLayout.addView(new ColorMatrixView(this));
    }

}
