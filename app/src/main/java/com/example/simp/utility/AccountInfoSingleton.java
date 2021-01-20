package com.example.simp.utility;

public class AccountInfoSingleton {
    private static AccountInfoSingleton obj;
    private String _userID;

    private AccountInfoSingleton(){
        _userID="";
    }
    public static AccountInfoSingleton getAccountInfoHolder(){
        if(obj==null){
            synchronized (AccountInfoSingleton.class){
                if(obj==null){
                    obj=new AccountInfoSingleton();
                }
            }
        }
        return obj;
    }
    public void setupInfo(String userID){
        _userID = userID;
    }
    public String getUserID(){
        return _userID;
    }
}
