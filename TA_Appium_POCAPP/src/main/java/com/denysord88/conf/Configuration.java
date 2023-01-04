package com.denysord88.conf;

import io.github.cdimascio.dotenv.Dotenv;

public class Configuration {
    private static final Dotenv dotenv = Dotenv
            .configure()
            .ignoreIfMissing()
            .load();

    public static final String IOS_SIMULATOR_UUID = get("TA_IOS_SIMULATOR_UUID").trim();
    public static final String IOS_APP_PATH = get("BITRISE_APP_DIR_PATH");

    private static String get(String parameterName) {
        String property = System.getProperty(parameterName);
        if (property == null) {
            property = dotenv.get(parameterName);
        }
        return property;
    }
}
