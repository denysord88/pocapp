package com.denysord88.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import static com.denysord88.conf.Configuration.ENABLE_LOGGING;

public class AppiumController {
    public static AppiumDriverLocalService runAppiumService(String wdaPort) {
        AppiumDriverLocalService service = AppiumDriverLocalService.
                buildService(new AppiumServiceBuilder()
                        .usingPort(Integer.parseInt(wdaPort))
                        .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/")
                        .withArgument(() -> "--allow-insecure", "get_server_logs")
                        .withArgument(() -> "--log-level", ENABLE_LOGGING ? "debug" : "error"));
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started");
        }
        return service;
    }
}
