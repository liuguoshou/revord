package com.recycling.common.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer implements ResourceLoaderAware, InitializingBean {

    private static Logger       logger             = Logger.getLogger(PropertyPlaceholderConfigurer.class);

    private static final String PLACEHOLDER_PREFIX = "${";
    private static final String PLACEHOLDER_SUFFIX = "}";
    private ResourceLoader      loader;
    private String              locationNames;

    public PropertyPlaceholderConfigurer(){
        setIgnoreUnresolvablePlaceholders(true);
    }

    public void setResourceLoader(ResourceLoader loader) {
        this.loader = loader;
    }

    public void setLocationNames(String locations) {
        this.locationNames = locations;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(loader, "no resourceLoader");

        if (locationNames != null) {
            locationNames = resolveSystemPropertyPlaceholders(locationNames);
        }

        if (StringUtils.hasLength(locationNames)) {
            String[] locations = StringUtils.commaDelimitedListToStringArray(locationNames);
            List<Resource> resources = new ArrayList<Resource>(locations.length);

            for (String location : locations) {

                if (location != null) {
                    resources.add(loader.getResource(location.trim()));
                }
            }

            super.setLocations(resources.toArray(new Resource[resources.size()]));
        }
    }

    private String resolveSystemPropertyPlaceholders(String text) {
        StringBuilder buf = new StringBuilder(text);

        for (int startIndex = buf.indexOf(PLACEHOLDER_PREFIX); startIndex >= 0;) {
            int endIndex = buf.indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());

            if (endIndex != -1) {
                String placeholder = buf.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                int nextIndex = endIndex + PLACEHOLDER_SUFFIX.length();

                try {
                    String value = resolveSystemPropertyPlaceholder(placeholder);

                    if (value != null) {
                        buf.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), value);
                        nextIndex = startIndex + value.length();
                    } else {
                        logger.error("Could not resolve placeholder '" + placeholder + "' in [" + text
                                     + "] as system property: neither system property nor environment variable found");
                    }
                } catch (Throwable ex) {
                    logger.error("Could not resolve placeholder '" + placeholder + "' in [" + text
                                 + "] as system property: " + ex);
                }

                startIndex = buf.indexOf(PLACEHOLDER_PREFIX, nextIndex);
            } else {
                startIndex = -1;
            }
        }

        return buf.toString();
    }

    private String resolveSystemPropertyPlaceholder(String placeholder) {
        DefaultablePlaceholder dp = new DefaultablePlaceholder(placeholder);
        String value = System.getProperty(dp.placeholder);

        if (value == null) {
            value = System.getenv(dp.placeholder);
        }

        if (value == null) {
            value = dp.defaultValue;
        }

        return value;
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
        DefaultablePlaceholder dp = new DefaultablePlaceholder(placeholder);
        String value = super.resolvePlaceholder(dp.placeholder, props, systemPropertiesMode);

        if (value == null) {
            value = dp.defaultValue;
        }

        return value;
    }

    private static class DefaultablePlaceholder {

        private final String defaultValue;
        private final String placeholder;

        public DefaultablePlaceholder(String placeholder){
            int commaIndex = placeholder.indexOf(":");
            String defaultValue = null;

            if (commaIndex >= 0) {
                defaultValue = placeholder.substring(commaIndex + 1);
                placeholder = placeholder.substring(0, commaIndex);
            }

            this.placeholder = placeholder == null ? "" : placeholder;
            this.defaultValue = defaultValue == null ? "" : defaultValue;
        }
    }
}
