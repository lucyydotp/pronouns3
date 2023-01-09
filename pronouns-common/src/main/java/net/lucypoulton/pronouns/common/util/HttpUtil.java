package net.lucypoulton.pronouns.common.util;

import net.lucypoulton.pronouns.common.platform.Platform;

import java.net.http.HttpClient;

public class HttpUtil {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static HttpClient client() {
        return client;
    }

    public static String userAgent(Platform platform) {
        return String.format("ProNouns/%s (%s) (github/lucypoulton)", platform.currentVersion(), platform.name());
    }

    private HttpUtil() {
    }
}
