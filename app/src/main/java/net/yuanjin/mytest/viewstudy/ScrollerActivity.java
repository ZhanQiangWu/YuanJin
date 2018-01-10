package net.yuanjin.mytest.viewstudy;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

/**
 *  手势检测，用以辅助检测用户的单击、滑动、长按、双击等行为
 *  Created by zhan on 2018/01/04.
 */

public class ScrollerActivity extends BasicActivity {

    private MScrollView touchArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scroller);

        getDefaultNavigation().setTitle("ScrollerActivity");

        touchArea = (MScrollView) findViewById(R.id.testarea);

        init();

    }

    private void init() {

        touchArea.setClickable(true);
        touchArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchArea.smoothScrollTo(100,-100);
            }
        });
    }


}
