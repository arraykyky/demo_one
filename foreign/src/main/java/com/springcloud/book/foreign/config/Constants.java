package com.springcloud.book.foreign.config;

/**
 *  2018-06-25
 *  grl 引用
 * Created by Jason on 2016/11/7.
 */
public class Constants {
    public static String CACHE_KEY_PREFIX = "reids_";
    private static String MODULE_CACHEKEY = "module_cacheKey_";
    private static String MODULE_CACHEKEY_FOREIGN_USER_ID = "module_cacheKey_foreign_user_id_";
    private static String MODULE_CACHEKEY_ID = "module_cacheKey_id_";
    private static String MODULE_CACHEKEY_HOSPITAL_ID = "module_cacheKey_hospital_id_";
    private static String MODULE_CACHEKEY_HOSPITAL_NAME = "module_cacheKey_hospital_name_";
    private static String MODULE_CACHEKEY_DEPT_ID = "module_cacheKey_dept_id_";
    private static String MODULE_CACHEKEY_DEPT_NAME = "module_cacheKey_dept_name_";
    private static String MODULE_CACHEKEY_USER_TYPE = "module_cacheKey_user_type_";
    private static String INFO_CACHEKEY = "info_cacheKey_";
    private static String APP_TOKEN_CACHEKEY = "app_token_cachekey_";
    private static String SESSION_CACHEKEY = "session_cacheKey_";
    private static String AUTHZ_CACHEKEY = "authz_cachekey_";
    private static String AUTHC_CACHEKEY = "authc_cachekey_";
    private static String PAGE_CACHEKEY = "page_cacheKey_";
    private static String DATA_CACHEKEY = "data_cacheKey_";
    private static String CLIENT_CACHEKEY = "client_cacheKey_";
    private static String ROLE_CACHEKEY = "_Role";
    private static String ROLE_CODE_CACHEKEY = "_Role_Code";

    public static String md5salt = "medbooks@2017";

    public Constants() {
    }
    public static String getUserModuleCacheUserRoleCode(String userName) {
        return userName + ROLE_CODE_CACHEKEY;
    }
    public static String getUserModuleCacheUserRole(String userName) {
        return userName + ROLE_CACHEKEY;
    }
    public static String getUserModuleCacheKey(String userName) {
        return MODULE_CACHEKEY + userName;
    }
    public static String getUserModuleCacheKeyForeignUserId(String userName){
        return MODULE_CACHEKEY_FOREIGN_USER_ID + userName;
    }
    public static String getUserModuleCacheKeyId(String userName) {
        return MODULE_CACHEKEY_ID + userName;
    }

    public static String getUserModuleCacheKeyHospitalId(String userName) {
        return MODULE_CACHEKEY_HOSPITAL_ID + userName;
    }

    public static String getUserModuleCacheKeyHospitalName(String userName) {
        return MODULE_CACHEKEY_HOSPITAL_NAME + userName;
    }

    public static String getUserModuleCacheKeyDeptId(String userName) {
        return MODULE_CACHEKEY_DEPT_ID + userName;
    }

    public static String getUserModuleCacheKeyDeptName(String userName) {
        return MODULE_CACHEKEY_DEPT_NAME + userName;
    }

    public static String getUserModuleCacheKeyUserType(String userName) {
        return MODULE_CACHEKEY_USER_TYPE + userName;
    }

    public static String getUserInfoCacheKey(String userName) {
        return INFO_CACHEKEY + userName;
    }

    public static String getUserSessionCacheKey(String userName) {
        return SESSION_CACHEKEY + userName;
    }

    public static String getAppTokenCacheKey(String userName) {
        return APP_TOKEN_CACHEKEY + userName;
    }

    public static String getPageCacheKey(String userName) {
        return PAGE_CACHEKEY + userName;
    }

    public static String getDataCacheKey(String userName) {
        return DATA_CACHEKEY + userName;
    }

    public static String getUserAuthzKey(String userName) {
        return AUTHZ_CACHEKEY + userName;
    }

    public static String getUserAuthcKey(String userName) {
        return AUTHC_CACHEKEY + userName;
    }

    public static String getClientCacheKey(String userName) {
        return CLIENT_CACHEKEY + userName;
    }


}
