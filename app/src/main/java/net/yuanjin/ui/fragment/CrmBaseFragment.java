package net.yuanjin.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import net.yuanjin.ui.BasicActivity;

/**
 *  Created by WuZhanQiang on 2016/11/14.
 */

public class CrmBaseFragment extends Fragment{

    protected BasicActivity activity;

    public CrmBaseFragment(){
        super();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (activity==null){
            activity = (BasicActivity) getActivity();
        }
    }

    /**
     * 初始化标题栏
     */
    public void initFragmentActionBar(){

    }

}
