package org.simpledfs.core.config;

import java.util.Properties;

public class Configuration {

    private Properties properties;

    public Configuration(Properties properties) {
        this.properties = properties;
    }

    public int getInt(String key, int defaultValue){
        String tmp = this.properties.getProperty(key);
        return tmp == null ? defaultValue : Integer.valueOf(tmp);
    }

    public double getDouble(String key, double defaultValue){
        String tmp = this.properties.getProperty(key);
        return tmp == null ? defaultValue : Double.valueOf(tmp);
    }

    public String getString(String key, String defaultValue){
        return this.properties.getProperty(key, defaultValue);
    }

    public String getString(String key){
        return getString(key, null);
    }
}
