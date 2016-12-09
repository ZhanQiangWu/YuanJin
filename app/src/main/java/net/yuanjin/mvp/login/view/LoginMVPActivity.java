package net.yuanjin.mvp.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.yuanjin.R;
import net.yuanjin.mvp.login.presenter.ILoginPresenter;
import net.yuanjin.mvp.login.presenter.LoginPresenterCompl;
import net.yuanjin.ui.BasicActivity;

/**
 *  Created by WuZhanQiang on 2016/12/9.
 */

public class LoginMVPActivity extends BasicActivity implements ILoginView, View.OnClickListener{

    private EditText editUser;
    private EditText editPass;
    private Button btnLogin;
    private Button   btnClear;
    ILoginPresenter loginPresenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUser = (EditText) this.findViewById(R.id.et_login_username);
        editPass = (EditText) this.findViewById(R.id.et_login_password);
        btnLogin = (Button) this.findViewById(R.id.btn_login_login);
        btnClear = (Button) this.findViewById(R.id.btn_login_clear);
        progressBar = (ProgressBar) this.findViewById(R.id.progress_login);

        btnClear.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        loginPresenter = new LoginPresenterCompl(this);
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);

    }

    @Override
    public void onClearText() {
        editUser.setText("");
        editPass.setText("");
    }

    @Override
    public void onLoginResult(Boolean result) {
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);
        btnLogin.setEnabled(true);
        btnClear.setEnabled(true);
        if (result){
            Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Login Fail" ,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_clear:
                loginPresenter.clear();
                break;
            case R.id.btn_login_login:
                loginPresenter.setProgressBarVisiblity(View.VISIBLE);
                btnClear.setEnabled(false);
                btnLogin.setEnabled(false);
                loginPresenter.doLogin(editUser.getText().toString(),editPass.getText().toString());
                break;
        }
    }
}
