package com.crm.oms.common.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.crm.oms.model.UmsAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * <线程上下文工具类>
 *
 * @author fangyang
 * @since 1.0.0
 */
public class TransmittableThreadLocalContext {

    private static final TransmittableThreadLocal<Map<String, Object>> CONTEXT = new TransmittableThreadLocal<>();

    private static final String AUTH_DATA = "umsAdmin";


    private static void put(String key, Object value) {
        if (CONTEXT.get() == null) {
            CONTEXT.set(new HashMap<>(16));
        }
        CONTEXT.get().put(key, value);
    }

    private static Object get(String key) {
        if (CONTEXT.get() == null) {
            System.out.println("#TransmittableThreadLocalContext得到对应的key为空null");
        }
        return CONTEXT.get().get(key);
    }

    public static void remove() {
        CONTEXT.remove();
    }

    public static void setAuthData(UmsAdmin umsAdmin) {
        put(AUTH_DATA, umsAdmin);
    }


    public static UmsAdmin getAuthDataBo() {
        return (UmsAdmin) get(AUTH_DATA);
    }


}
