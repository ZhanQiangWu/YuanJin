package net.yuanjin.mytest.tencent_xinge;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.android.tpush.XGPushManager;

import net.yuanjin.R;
import net.yuanjin.ormlib.BaseSqliteDALEx;
import net.yuanjin.ui.BasicActivity;
import net.yuanjin.widget.navigation.NavigationText;
import net.yuanjin.widgetlib.XtionBasicActivity;

/**
 *  腾讯信鸽测试页面
 * Created by wzq on 2017/2/16.
 */

public class TencentXinGeTestActivity extends BasicActivity implements View.OnClickListener{

    private EditText accountEditText,tagEditText;
    private Button accountBindButton,accountUnBindButton,tagBindButton,tagUnBindButton,tokenUnRegisterButton;
    private NavigationText navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tencentxinge);
        initActionBar();
        initView();
    }

    private void initActionBar() {
        if (navigation == null){
            navigation = new NavigationText(this);
            navigation.setTitle("TencentXinGeTestActivity");
        }
        setNavigation(navigation);
    }

    /**
     * view初始化
     */
    private void initView() {

        accountEditText = (EditText) findViewById(R.id.xinge_edittext_account);
        tagEditText = (EditText) findViewById(R.id.xinge_edittext_tag);

        accountBindButton = (Button) findViewById(R.id.xinge_button_account_bind);
        accountUnBindButton = (Button) findViewById(R.id.xinge_button_account_unbind);
        tagBindButton = (Button) findViewById(R.id.xinge_button_tag_bind);
        tagUnBindButton = (Button) findViewById(R.id.xinge_button_tag_unbind);

        accountBindButton.setOnClickListener(this);
        accountUnBindButton.setOnClickListener(this);
        tagBindButton.setOnClickListener(this);
        tagUnBindButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.xinge_button_account_bind://账号绑定
                if (TextUtils.isEmpty(accountEditText.getText().toString())) return;
                XGPushManager.registerPush(getApplicationContext(),accountEditText.getText().toString());
                break;
            case R.id.xinge_button_account_unbind://账号解绑
                if (TextUtils.isEmpty(accountEditText.getText().toString())) return;
                Toast.makeText(TencentXinGeTestActivity.this,"账号解绑",Toast.LENGTH_SHORT).show();
                XGPushManager.registerPush(getApplicationContext(),"*");
                break;
            case R.id.xinge_button_account_unregister://token反注册
                Toast.makeText(TencentXinGeTestActivity.this,"token反注册",Toast.LENGTH_SHORT).show();
                XGPushManager.unregisterPush(getApplicationContext());
                break;
            case R.id.xinge_button_tag_bind://标签绑定
                if (TextUtils.isEmpty(tagEditText.getText().toString())) return;
                Toast.makeText(TencentXinGeTestActivity.this,"标签绑定" + tagEditText.getText().toString(),Toast.LENGTH_SHORT).show();
                XGPushManager.setTag(getApplicationContext(),tagEditText.getText().toString());
                break;
            case R.id.xinge_button_tag_unbind://标签解绑
                if (TextUtils.isEmpty(tagEditText.getText().toString())) return;
                Toast.makeText(TencentXinGeTestActivity.this,"标签解绑",Toast.LENGTH_SHORT).show();
                XGPushManager.deleteTag(getApplicationContext(),tagEditText.getText().toString());
                break;

        }
    }
}
