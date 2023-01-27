package utility.helper;

import common.LoadPropertiesFiles;

import java.io.IOException;
import java.util.Properties;

public final class VARS {
    static Properties prop;
    static {
        try {
            prop = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") + "/src/main/resources/vars.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getDefaultTimeoutInSeconds() {
        return Integer.parseInt(prop.getProperty("default_timeout_in_seconds"));
    }

    public static int getDefaultRetryWaitInSeconds() {
        return Integer.parseInt(prop.getProperty("default_retry_wait_in_seconds"));
    }

    public static int getMaxRetries() {
        return Integer.parseInt(prop.getProperty("max_retries"));
    }

    public static int getLessRetries() {
        return Integer.parseInt(prop.getProperty("less_retries"));
    }

}
