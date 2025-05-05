package com.yallaIg.yallaIg_backend.util;

import jakarta.servlet.http.HttpServletRequest;

public final class ApiRequestUtils {

    private ApiRequestUtils(){

    }

    public static String getAppUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" ;
    }

    public static String getFullAppUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
