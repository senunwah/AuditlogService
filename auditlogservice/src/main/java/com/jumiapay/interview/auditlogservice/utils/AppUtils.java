package com.jumiapay.interview.auditlogservice.utils;

public class AppUtils {

    public static String APPNAME = "AuditlogService";

    public static boolean isNullOrEmpty(String value) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return false;
    }
}
