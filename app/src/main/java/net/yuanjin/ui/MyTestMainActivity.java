package net.yuanjin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import net.yuanjin.R;
import net.yuanjin.mvp.login.view.LoginMVPActivity;

/**
 *  Created by WuZhanQiang on 2016/12/9.
 */

public class MyTestMainActivity extends BasicActivity{

    private Button mvploginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytestmain);
        initView();
    }

    private void initView() {
        mvploginBtn = (Button) findViewById(R.id.btn_mvp_login);
        mvploginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyTestMainActivity.this, LoginMVPActivity.class);
                startActivity(intent);
            }
        });
    }
}
