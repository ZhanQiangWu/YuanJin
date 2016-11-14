package net.yuanjin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.yuanjin.R;

/**
 *  Created by WuZhanQiang on 2016/11/14.
 */

public class OfficeFragment extends CrmBaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_office,null);
        return view;
    }
}
