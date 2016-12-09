package net.yuanjin.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.yuanjin.R;
import net.yuanjin.mvp.login.view.LoginMVPActivity;
import net.yuanjin.ui.MyTestMainActivity;

/**
 *  Created by WuZhanQiang on 2016/11/14.
 */

public class MySelfFragment extends CrmBaseFragment{

    private Button mytestBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myself,null);
        mytestBtn = (Button) view.findViewById(R.id.mytestbtn);
        mytestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyTestMainActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
