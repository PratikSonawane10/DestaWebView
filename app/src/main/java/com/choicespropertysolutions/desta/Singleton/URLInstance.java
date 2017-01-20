package com.choicespropertysolutions.desta.Singleton;

public class URLInstance {
    //private static String url = "http://destatalk.com/DestaAPI/api/destaapi.php";
    private static String url = "http://192.168.0.4/DestaAPI/api/destaapi.php";
    public static String getUrl() {
        return url;
    }
}