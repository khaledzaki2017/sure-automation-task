package com.sure.orangehrm.utils;

import java.util.Properties;
import java.io.InputStream;

public class ConfigReader {
    private static final Properties props = new Properties();

    public static void load(String fileName) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("Property file not found: " + fileName);
            }
            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load "+fileName, e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    //get integer configs with defaults
    public static int getInt(String key, int defaultVal) {
        try {
            return Integer.parseInt(props.getProperty(key));
        } catch (Exception e) {
            return defaultVal;
        }
    }

}
