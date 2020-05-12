package com.example.lab.account.domain;

import java.util.EnumMap;

/**
 * login type enum
 *
 * @author Miles.wang
 * @since 2016/6/16 09:42.
 */
public enum LoginType {
    USERID, USERNAME, MOBILE, MOBILE_QUICK, EMAIL, WEIBO, QQ, WECHAT, ALIPAY;

    public static EnumMap<LoginType, String> loginTypeMap =
            new EnumMap<LoginType, String>(LoginType.class);

    static {
        loginTypeMap.put(LoginType.USERID, "userId");
        loginTypeMap.put(LoginType.USERNAME, "username");
        loginTypeMap.put(LoginType.MOBILE, "mobile");
        loginTypeMap.put(LoginType.MOBILE_QUICK, "mobileQuick");
        loginTypeMap.put(LoginType.EMAIL, "email");
        loginTypeMap.put(LoginType.WEIBO, "weibo");
        loginTypeMap.put(LoginType.QQ, "qq");
        loginTypeMap.put(LoginType.WECHAT, "wechat");
        loginTypeMap.put(LoginType.ALIPAY, "alipay");
    }

    public static LoginType defaultLoginType() {
        return USERNAME;
    }

    public String value() {
        return loginTypeMap.get(this);
    }

    /**
     * 判断登录类型
     * <p>
     *
     * @param loginTypeStr 登录类型字符串
     * @return 登录类型
     */
    public static LoginType determinLoginType(String loginTypeStr) {
        try {
            return LoginType.valueOf(loginTypeStr);
        } catch (IllegalArgumentException e) {
        }

        for (LoginType loginType : LoginType.values()) {
            if (loginType.value().equalsIgnoreCase(loginTypeStr)) {
                return loginType;
            }
        }

        return LoginType.USERNAME;
    }

    /**
     * 判断是否第三方登录
     *
     * @param loginTypeStr 登录类型字符串
     * @return 如果第三方登录，返回true
     */
    public static boolean thirdLoginType(String loginTypeStr) {
        LoginType type = determinLoginType(loginTypeStr);
        return thirdLoginType(type);
    }

    /**
     * 判断是否第三方登录
     *
     * @param loginType 登录类型
     * @return 如果第三方登录，返回true
     */
    public static boolean thirdLoginType(LoginType loginType) {
        return loginType == WEIBO || loginType == QQ || loginType == WECHAT|| loginType == ALIPAY;
    }

    /**
     * 判断是否合法的登录类型
     *
     * @param loginTypeStr 登录类型
     * @return 如果合法，返回true
     */
    public static boolean isAllowLoginType(String loginTypeStr) {
        boolean allow = false;
        for (LoginType loginType : LoginType.values()) {
            if (loginType.value().equals(loginTypeStr)) {
                allow = true;
                break;
            }
        }
        return allow;
    }

    /**
     * Determine whether this {@code LoginType} matches the given
     * type value.
     *
     * @param type the type value as a String
     * @return {@code true} if it matches, {@code false} otherwise
     */
    public boolean matches(String type) {
        return value().equals(type);
    }

    /**
     * 判断是否为不需要密码登录
     *
     * @param loginType 登录类型
     * @return 如果不需要密码登录，返回true
     */
    public static boolean noPasswordLogin(LoginType loginType) {
        return thirdLoginType(loginType) || loginType == MOBILE_QUICK;
    }
}
