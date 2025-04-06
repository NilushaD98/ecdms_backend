package com.ecdms.ecdms.util;

public class TokenContext {
    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();

    public static String getToken() {
        return TOKEN.get();
    }

    public static void setToken(String token) {
        TOKEN.set("Bearer "+token);
    }

    public static void clear() {
        TOKEN.remove();
    }
}
