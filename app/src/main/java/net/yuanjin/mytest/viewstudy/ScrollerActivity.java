package net.yuanjin.mytest.viewstudy;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

/**
 *   Android Scroller完全解析，关于Scroller你所需知道的一切
 *   http://blog.csdn.net/guolin_blog/article/details/48719871
 *  Created by zhan on 2018/01/04.
 */

public class ScrollerActivity extends BasicActivity {

    private LinearLayout layout;

    private Button scrollToBtn;

    private Button scrollByBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scroller);

        getDefaultNavigation().setTitle("ScrollerActivity");


        layout = (LinearLayout) findViewById(R.id.layout);
        scrollToBtn = (Button) findViewById(R.id.scroll_to_btn);
        scrollByBtn = (Button) findViewById(R.id.scroll_by_btn);
        scrollToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scrollTo 滚动的是该View 内部的内容
                layout.scrollTo(-60, -100);
            }
        });
        scrollByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scrollBy 滚动的是该View 内部的内容
                layout.scrollBy(-60, -100);
            }
        });

    }

    /**
     * scrollTo(int x, int y)
     * 让View相对于初始的位置滚动某段距离（View的初始位置是不变的）
     * @param x 表示相对于当前位置横向移动的距离，正值向左移动，负值向右移动，单位是像素
     * @param y 表示相对于当前位置纵向移动的距离，正值向上移动，负值向下移动，单位是像素
     */
    private void scrollTo(int x, int y){

    }

    /**
     * scrollBy(int x, int y)
     * 让View相对于当前的位置滚动某段距离
     * @param x 表示相对于当前位置横向移动的距离，正值向左移动，负值向右移动，单位是像素
     * @param y 表示相对于当前位置纵向移动的距离，正值向上移动，负值向下移动，单位是像素
     */
    private void scrollBy(int x, int y){

    }


}
