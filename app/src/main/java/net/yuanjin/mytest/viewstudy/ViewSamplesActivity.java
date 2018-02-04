package net.yuanjin.mytest.viewstudy;

import android.os.Bundle;

import net.yuanjin.mytest.viewstudy.looppager.LoopPagerActivity;
import net.yuanjin.ui.MySamplesBaseActivity;

/**
 *  Created by wzq on 2017/12/15.
 */

public class ViewSamplesActivity extends MySamplesBaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDefaultNavigation().setTitle("view 学习实例");
    }

    @Override
    protected void initClassDatas() {
        classList.add(VelocityTrackerActivity.class);
        classList.add(GestureDectectorActivity.class);
        classList.add(ScrollerActivity.class);
        classList.add(LoopPagerActivity.class);
        classList.add(ViewDragHelperActivity.class);
    }
}
