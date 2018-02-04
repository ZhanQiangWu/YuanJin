package net.yuanjin.mytest.viewstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

/**
 * ViewDragHelper 学习
 * @author wzq
 * @date 2018/2/2
 * 参考资料：
 * Android自定义ViewGroup神器-ViewDragHelper
 *      https://www.jianshu.com/p/111a7bc76a0e
 * ViewDragHelper （一）- 介绍及简单用例（入门篇）
 *      http://blog.csdn.net/qq_22393017/article/details/78045810
 * ViewDragHelper实战：APP内“悬浮球”
 *      https://www.jianshu.com/p/d2c80e7e584e
 */

public class ViewDragHelperActivity extends BasicActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewdraghelper);
        getDefaultNavigation().setTitle("ViewDragHelperActivity");

        TextView dragView = (TextView) findViewById(R.id.dragView);

        dragView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewDragHelperActivity.this,"dragView 被点击了",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
