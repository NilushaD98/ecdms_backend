package com.ecdms.ecdms.util;

public class AuthorizedUserContext {

    private static final ThreadLocal<String> USER = new ThreadLocal<>();

    public static String getUser() {
        return USER.get();
    }

    public static void setUser(String user) {
        USER.set(user);
    }

    public static void clear() {
        USER.remove();
    }
}
