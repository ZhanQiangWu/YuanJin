package net.yuanjin.mytest.mvp.login.view;

/**
 *  Created by zhan on 2016/12/9.
 */

public interface ILoginView {

    public void onClearText();
    public void onLoginResult(Boolean result);
    public void onSetProgressBarVisibility(int visibility);

}
