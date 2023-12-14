package com.manmeet.moudgill.NewTodoApplication.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookiesConfig {


    /**
     * @param cookieName
     * @param cookieData
     * @param isHttpOnly
     * @param cookiePath
     * @return
     * @Description This is Helper method to configure cookies and set them to response headers
     */
    public HttpHeaders configure(String cookieName, String cookieData, boolean isHttpOnly, String cookiePath) {

        ResponseCookie cookie = ResponseCookie.from(cookieName, cookieData)
                .httpOnly(isHttpOnly)
                .path(cookiePath) // Set the path for which the cookie is valid
                .build();


        // Add the cookie to the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return headers;

    }
}
