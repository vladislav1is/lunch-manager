package com.redfox.lunchmanager.web;

import com.redfox.lunchmanager.model.AbstractBaseEntity;

public final class SecurityUtil {

    private static int id = AbstractBaseEntity.START_SEQ + 3;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }
}
