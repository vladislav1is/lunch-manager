package com.redfox.lunchmanager.web;

public final class SecurityUtil {

    private static int id = 1;

    public static final int USER_ID_1 = 100_001;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }
}
