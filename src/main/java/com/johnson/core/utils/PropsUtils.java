package com.johnson.core.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PropsUtils {

    private static final Logger LOGGER = LogManager.getLogger(PropsUtils.class);

    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;
        try {
            is = ClassUtils.getClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }
        return props;
    }

    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = Integer.parseInt(props.getProperty(key));
        }
        return value;
    }

    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = Boolean.parseBoolean(props.getProperty(key));
        }
        return value;
    }
}
