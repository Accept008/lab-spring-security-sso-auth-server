package com.example.lab.add;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author miles.wang
 *
 */
public abstract class OauthContextHolder {
    /*
     * public static ThreadLocal<String> userId = new ThreadLocal<String>(); public static
     * ThreadLocal<String> accessToken = new ThreadLocal<String>(); public static
     * ThreadLocal<Map<String, Object>> additionalInformation = new ThreadLocal<Map<String,
     * Object>>();
     */
    public static final String CLIENT_ID = "KEY_CLIENT_ID";
    public static final String SYSTEM_ID = "KEY_SYSTEM_ID";
    public static final String ACCOUNT_ID = "KEY_ACCOUNT_ID";
    public static final String CUSTOMER_ID = "KEY_CUSTOMER_ID";
    public static final String USER_ID = "KEY_USER_ID";
    public static final String ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    public static final String ADDITIONAL_INFORMATION = "KEY_ADDITIONAL_INFORMATION";
    public static final String CLIENT_IP = "KEY_CLIENT_IP";
    private static final ThreadLocal<Map<Object, Object>> resources =
                    new OauthContextHolder.InheritableThreadLocalMap();


    private OauthContextHolder() {}

    public static Map<Object, Object> getResources() {
        return resources.get() == null ? Collections.emptyMap() : new HashMap(resources.get());
    }

    public static void setResources(Map<Object, Object> newResources) {
        if (!CollectionUtils.isEmpty(newResources)) {
            ensureResourcesInitialized();
            ((Map) resources.get()).clear();
            ((Map) resources.get()).putAll(newResources);
        }
    }

    public static String getClientId() {
        return (String) get(CLIENT_ID);
    }

    public static void setClientId(String clientId) {
        put(CLIENT_ID, clientId);
    }

    public static void setSystemId(String systemId) {
        put(SYSTEM_ID, systemId);
    }

    public static String getSystemId() {
        return (String) get(SYSTEM_ID);
    }

    public static String getUserId() {
        return (String) get(USER_ID);
    }

    public static void setUserId(String userId) {
        put(USER_ID, userId);
    }

    public static void setCustomerId(String customerId) {
        put(CUSTOMER_ID, customerId);
    }

    public static String getCustomerId() {
        return (String) get(CUSTOMER_ID);
    }

    public static void setAccountId(String accountId) {
        put(ACCOUNT_ID, accountId);
    }

    public static String getAccountId() {
        return (String) get(ACCOUNT_ID);
    }

    public static String getAccessToken() {
        return (String) get(ACCESS_TOKEN);
    }

    public static void setAccessToken(String token) {
        put(ACCESS_TOKEN, token);
    }

    public static Map<String, Object> getAdditionalInfo() {
        return (Map<String, Object>) get(ADDITIONAL_INFORMATION);
    }

    public static void setAdditionalInfo(Map<String, Object> info) {
        put(ADDITIONAL_INFORMATION, info);
    }

    public static String getClientIp() {
        return (String) get(CLIENT_IP);
    }

    public static void setClientIp(String clientIp) {
        put(CLIENT_IP, clientIp);
    }

    private static Object getValue(Object key) {
        Map perThreadResources = resources.get();
        return perThreadResources != null ? perThreadResources.get(key) : null;
    }

    private static void ensureResourcesInitialized() {
        if (resources.get() == null) {
            resources.set(new HashMap());
        }

    }

    public static Object get(Object key) {
        Object value1 = getValue(key);
        return value1;
    }

    public static void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        } else if (value == null) {
            remove(key);
        } else {
            ensureResourcesInitialized();
            ((Map) resources.get()).put(key, value);

        }
    }

    public static Object remove(Object key) {
        Map perThreadResources = resources.get();
        Object value = perThreadResources != null ? perThreadResources.remove(key) : null;
        return value;
    }

    public static void remove() {
        resources.remove();
    }

    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>>
                    extends InheritableThreadLocal<Map<Object, Object>> {
        private InheritableThreadLocalMap() {}

        @Override
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            return parentValue != null ? (Map) ((HashMap) parentValue).clone() : null;
        }
    }
}
