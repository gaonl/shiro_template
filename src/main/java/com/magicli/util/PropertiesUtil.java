package com.magicli.util;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {
    private static final Map<String, String> propertyMap = new HashMap<>();


    private PropertiesUtil() {
    }

    public static final void loadFromClasspath(String fileName) {
        try {

            Properties properties = new Properties();
            properties.load(PropertiesUtil.class.getResourceAsStream(fileName));

            Set<Object> objectSet = properties.keySet();

            for (Object obj : objectSet) {
                propertyMap.put(String.valueOf(obj), String.valueOf(properties.get(obj)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static {
        loadFromClasspath("/application.properties");
    }

    public static final String getProperty(String key) {
        return propertyMap.get(key);
    }
}
