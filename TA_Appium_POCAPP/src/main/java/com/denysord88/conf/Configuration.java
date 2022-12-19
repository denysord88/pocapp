package com.denysord88.conf;

import io.github.cdimascio.dotenv.Dotenv;

public class Configuration {
    private static final Dotenv dotenv = Dotenv
            .configure()
            .ignoreIfMissing()
            .load();

    public static final String WEBVIEW_URL = get("TA_WEBVIEW_URL");
    public static final String EMAIL = get("TA_USER_EMAIL");
    public static final String PASSWORD = get("TA_USER_PASSWORD");
    public static final String[] IOS_VERSIONS = get("TA_IOS_VERSIONS").split(",");
    public static final String[] IOS_DEVICES = get("TA_IOS_DEVICES").split(",");
    public static final String[] ANDROID_VERSIONS = get("TA_ANDROID_VERSIONS").split(",");
    public static final String[] ANDROID_DEVICES = get("TA_ANDROID_DEVICES").split(",");
    public static final int TESTING_THREADS = Integer.parseInt(get("TA_TESTING_THREADS"));
    public static final String[] EXCLUDED_GROUPS = get("TA_EXCLUDED_GROUPS").split(",");
    public static final String SCREENSHOTS_PATH = get("TA_SCREENSHOTS_PATH");
    public static final String IOS_APP_PATH = get("TA_IOS_APP_PATH");
    public static final String ANDROID_APP_PATH = get("BITRISE_APK_PATH");
    public static final boolean ENABLE_LOGGING = Boolean.parseBoolean(get("TA_ENABLE_LOGGING"));
    public static final boolean USE_PREBUILT_WDA = Boolean.parseBoolean(get("TA_USE_PREBUILT_WDA"));
    public static final Integer EXPLICITLY_WAIT_SECONDS = Integer.parseInt(get("TA_EXPLICITLY_WAIT_SECONDS"));
    public static final Integer IMPLICITLY_WAIT_SECONDS = Integer.parseInt(get("TA_IMPLICITLY_WAIT_SECONDS"));
    public static final String LANGUAGE=get("TA_LANGUAGE");
    public static final String LOCALE=get("TA_LOCALE");

    private static String get(String parameterName) {
        String property = System.getProperty(parameterName);
        if (property == null) {
            property = dotenv.get(parameterName);
        }
        return property;
    }
}
