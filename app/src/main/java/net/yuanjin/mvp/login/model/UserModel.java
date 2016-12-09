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
        return name;
    }

    @Override
    public String getPasswd() {
        return passwd;
    }

    @Override
    public boolean checkUserValidity(String name, String passwd) {
        if (name==null || passwd==null || !name.equals(getName()) || !passwd.equals(getPasswd())){
            return false;
        }else {
            return true;
        }
    }
}
