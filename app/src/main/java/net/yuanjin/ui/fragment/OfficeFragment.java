package net.yuanjin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.yuanjin.R;
import net.yuanjin.widget.navigation.NavigationContainer;
import net.yuanjin.widget.navigation.NavigationText;

/**
 *  Created by WuZhanQiang on 2016/11/14.
 */

public class OfficeFragment extends CrmBaseFragment{

    private NavigationText navigation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_office,null);
        return view;
    }

    /**
     * 标题栏初始化
     */
    @Override
    public void initFragmentActionBar() {
        super.initFragmentActionBar();
        if(navigation==null){
            navigation = new NavigationText(activity).setTitle("办公");
            navigation.getLeftButton().hide();
        }
        activity.setNavigation(navigation);
    }
}
