package com.demes.util;

import javax.servlet.http.HttpServletRequest;

public class UrlApplicationHelper {

    public static String getAppUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath();
    }
}
