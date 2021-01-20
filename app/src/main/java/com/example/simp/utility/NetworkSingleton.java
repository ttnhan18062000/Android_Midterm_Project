package com.example.simp.utility;

public class NetworkSingleton {
    private static NetworkSingleton obj;
    private String SERVER ;

    private NetworkSingleton(){
        SERVER="http://192.168.1.69:8999";
    }
    public static NetworkSingleton getNetworkInfoHolder(){
        if(obj==null){
            synchronized (NetworkSingleton.class){
                if(obj==null){
                    obj=new NetworkSingleton();
                }
            }
        }
        return obj;
    }
    public String getSERVER(){
        return SERVER;
    }
}