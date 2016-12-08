package net.yuanjin.mvp.login.model;

/**
 *  Created by zhan on 2016/12/9.
 */

public class UserModel implements IUser{
    String name;
    String passwd;

    public UserModel(String name,String passwd){
        this.name = name ;
        this.passwd = passwd;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPasswd() {
        return null;
    }

    @Override
    public int checkUserValidity(String name, String passwd) {
        return 0;
    }
}
