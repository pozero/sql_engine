package com.hitsz.constant;

public class RedisConstant {

    private RedisConstant() {
    }

    /**
     * API接口缓存的key
     */
    public static final String KEY_API = "api:";

    /**
     * 用户数据源信息缓存的key
     */
    public static final String KEY_DBINFO = "dbInfo:";

    /**
     * 仅用于标识，表示对应的key在数据库中已经存在
     */
    public static final String KEY_EXISTED_URL = "existedUrl:";
    public static final String EXISTED_FLAG  = "existed";

    /**
     * 缓存过期时间，统一使用毫秒
     */
    public static final long EXP_TIME = 3600L * 1000;

}
