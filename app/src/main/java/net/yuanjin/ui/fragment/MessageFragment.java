package net.yuanjin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.yuanjin.R;
import net.yuanjin.widget.navigation.NavigationText;

/**
 *  Created by WuZhanQiang on 2016/11/14.
 */

public class MessageFragment extends CrmBaseFragment{

    private NavigationText navigation;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,null);
        initFragmentActionBar();
        return view;
    }

    @Override
    public void initFragmentActionBar() {
        if (navigation==null){
            navigation = new NavigationText(getContext());
            // TODO: 2016/11/25  
            navigation.getLeftButton();
            
        }
    }
}
