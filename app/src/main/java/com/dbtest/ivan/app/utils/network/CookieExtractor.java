package com.dbtest.ivan.app.utils.network;

import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

/**
 * Created by ivan on 27.03.16.
 */
public class CookieExtractor {
    public static String getCookie(String cookies, String param){
        String[] params = cookies.split(";");
        String result = null;
        for(String s : params){
            if(s.startsWith(param)){
                String[] cookie = s.split("=");
                if(cookie.length == 2) {
                    result = cookie[1];
                }
            }
        }
        return result;
    }

//    public static void main(String[] args) {//todo delete
//        String f = "{Server=[Apache-Coyote/1.1], X-Content-Type-Options=[nosniff], X-XSS-Protection=[1; mode=block], Cache-Control=[no-cache, no-store, max-age=0, must-revalidate], Pragma=[no-cache], Expires=[0], X-Frame-Options=[DENY], Set-Cookie=[JSESSIONID=34937A4878B3EE9B84A1D7C048544551; Path=/; HttpOnly], Content-Length=[37], Date=[Sat, 26 Mar 2016 21:59:34 GMT], OkHttp-Sent-Millis=[1459029348781], OkHttp-Received-Millis=[1459029357151]}";
//        String cookies = "JSESSIONID=34937A4878B3EE9B84A1D7C048544551; Path=/; HttpOnly], Content-Length=[37], Date=[Sat, 26 Mar 2016 21:59:34 GMT";
//        String result = getCookie(cookies,"JSESSIONID");
//        System.out.println(result);
//    }
}
