package com.ga.java.project01;

import java.util.Collections;
import java.util.List;

public final class Session {
    private static Session instance = null;

    private final String userId;
    private final List<String> authGroups;

    private Session(String userId, List<String> authGroups) {
        this.userId = userId;
        this.authGroups = authGroups;
    }

    public static void create(String userId, List<String> authGroups) {
        if (instance == null) {
            instance = new Session(userId, authGroups);
        }
    }

    public static Session get() {
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getAuthGroups() {
        return Collections.unmodifiableList(authGroups);
    }
}
